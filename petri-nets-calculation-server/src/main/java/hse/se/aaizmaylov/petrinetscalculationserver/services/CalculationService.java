package hse.se.aaizmaylov.petrinetscalculationserver.services;

import hse.se.aaizmaylov.petrinetscalculationserver.data.RequestInfo;
import hse.se.aaizmaylov.petrinetscalculationserver.data.RequestStatus;
import hse.se.aaizmaylov.petrinetscalculationserver.data.RequestType;
import hse.se.aaizmaylov.petrinetscalculationserver.repository.RequestInfoRepository;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.PetriNet;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.PetriNetReader;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.Reduction;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.ReductionHistory;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Place;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Transition;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.analysis.Reducer;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.analysis.coverability.CoverabilityAnalyser;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.exporting.IntoPnmlExporter;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.exporting.PnmlColoredResult;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.importing.PnmlReader;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class CalculationService {
    private final static Logger LOGGER = LoggerFactory.getLogger(CalculationService.class);

    private final FileStorageService fileStorageService;

    @SuppressWarnings("FieldCanBeLocal")
    private final ExecutorService executorService;

    private final RequestInfoRepository requestInfoRepository;

    private final ReductionsInfoService reductionsInfoService;

    private BlockingQueue<RequestInfo> requestInfos;

    @Autowired
    public CalculationService(FileStorageService fileStorageService,
                              RequestInfoRepository requestInfoRepository,
                              ReductionsInfoService reductionsInfoService) {

        this.fileStorageService = fileStorageService;
        this.requestInfoRepository = requestInfoRepository;
        this.reductionsInfoService = reductionsInfoService;

        executorService = Executors.newFixedThreadPool(4);

        requestInfos = new LinkedBlockingQueue<>();

        executorService.execute(new CalculationRunner());
    }

    public void addRequest(@NonNull RequestInfo requestInfo) {
        requestInfos.offer(requestInfo);
    }

    private class CalculationRunner implements Runnable {

        private RequestInfo currentRequestInfo;

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    currentRequestInfo = requestInfos.take();

                    currentRequestInfo.setRequestStatus(RequestStatus.CALCULATING);
                    currentRequestInfo = requestInfoRepository.save(currentRequestInfo);

                    try {
                        PetriNet<Place, Transition> petriNet = readPetriNet();
                        ReductionHistory history = reduce(petriNet);

                        if (currentRequestInfo.getRequestType() == RequestType.REDUCTION) {
                            toPnml(petriNet);
                        } else if (currentRequestInfo.getRequestType() == RequestType.ANALYSIS) {
                            colorVertices(analyze(petriNet), history);
                        } else {
                            throw new AnalysisException("Unknown request type " + currentRequestInfo.getRequestType());
                        }

                        currentRequestInfo.setRequestStatus(RequestStatus.READY);
                    } catch (AnalysisException ex) {
                        StringWriter stringWriter = new StringWriter();
                        ex.printStackTrace(new PrintWriter(stringWriter));

                        currentRequestInfo.setError(ex.getMessage());
                        currentRequestInfo.setErrorLog(stringWriter.toString());
                        currentRequestInfo.setRequestStatus(RequestStatus.ERROR);
                    }

                    requestInfoRepository.save(currentRequestInfo);
                }
            } catch (InterruptedException e) {
                LOGGER.info(this + " interupted!");
            }
        }

        private PetriNet<Place, Transition> readPetriNet() {

            PnmlReader pnmlReader = new PnmlReader();

            try {
                return pnmlReader.read(
                        fileStorageService.getPathToFile(currentRequestInfo.getOriginalFileName()));
            } catch (PetriNetReader.PetriNetReadingException e) {
                throw new AnalysisException("Incorrect file!", e);
            }
        }

        private ReductionHistory reduce(PetriNet<Place, Transition> net) {
            Pair<List<Reduction<Place, Transition>>, List<Reduction<Transition, Place>>> reductions =
                    reductionsInfoService.getReductions(currentRequestInfo.getReductions());

            Reducer reducer = new Reducer(net);

            return reducer.reduce(reductions.getFirst(), reductions.getSecond());
        }

        private void toPnml(PetriNet<Place, Transition> petriNet) {
            IntoPnmlExporter exporter = new IntoPnmlExporter();

            try {
                exporter.export(petriNet, fileStorageService.getPathToFile(currentRequestInfo.getResultPath()));
            } catch (Exception e) {
                throw new AnalysisException("Cannot convert to pnml", e);
            }
        }

        private Pair<Set<Place>, Set<Transition>> analyze(PetriNet<Place, Transition> net) {
            CoverabilityAnalyser coverabilityAnalyser = new CoverabilityAnalyser(net);

            return Pair.of(coverabilityAnalyser.getUnboundedPlaces(), coverabilityAnalyser.getDeadTransitions());
        }

        private void colorVertices(Pair<Set<Place>, Set<Transition>> vertices, ReductionHistory history) {

            PnmlColoredResult result;
            try {
                result = new PnmlColoredResult(
                        fileStorageService.getPathToFile(currentRequestInfo.getOriginalFileName()));
            } catch (ParserConfigurationException | IOException | SAXException e) {
                throw new AnalysisException("Cannot color", e);
            }

            for (Place p : vertices.getFirst()) {
                for (String associatedP : history.getAssociated(p)) {
                    result.markPlaceUnbounded(associatedP);
                }
            }

            for (Transition t : vertices.getSecond()) {
                for (String associatedT : history.getAssociated(t)) {
                    result.markTransitionDead(associatedT);
                }
            }

            try {
                result.saveToFile(fileStorageService.getPathToFile(currentRequestInfo.getResultPath()));
            } catch (TransformerException | IOException e) {
                throw new AnalysisException("Cannot save colored result", e);
            }
        }
    }



}
