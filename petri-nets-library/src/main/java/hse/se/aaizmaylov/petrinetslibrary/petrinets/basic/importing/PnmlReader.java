package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.importing;

import fr.lip6.move.pnml.framework.general.PnmlImport;
import fr.lip6.move.pnml.framework.hlapi.HLAPIClass;
import fr.lip6.move.pnml.framework.utils.exception.*;
import fr.lip6.move.pnml.ptnet.PlaceNode;
import fr.lip6.move.pnml.ptnet.TransitionNode;
import fr.lip6.move.pnml.ptnet.hlapi.*;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.Edge;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.PetriNet;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.PetriNetReader;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.*;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PnmlReader implements PetriNetReader<Place, Transition> {

    private final PnmlFilePreprocessor preprocessor = new PnmlFilePreprocessor("graphics", "toolspecific");

    @NotNull
    @Override
    public PetriNet<Place, Transition> read(@NonNull String path) throws PetriNetReadingException {
        String newPath;
        try {
            newPath = preprocessor.preprocess(path);
        } catch (PreprocessException e) {
            throw new PetriNetReadingException(e);
        }

        PnmlImport pnmlImport = new PnmlImport();
        HLAPIClass hlapiClass;
        try {
            hlapiClass = pnmlImport.importFile(newPath);
        } catch (IOException | BadFileFormatException | UnhandledNetType | ValidationFailedException |
                InnerBuildException | OCLValidationFailed | OtherException | AssociatedPluginNotFound |
                InvalidIDException | VoidRepositoryException e) {
            throw new PetriNetReadingException(e);
        }

        if (!(hlapiClass instanceof PetriNetDocHLAPI)) {
            throw new PetriNetReadingException("Unsupported type of Petri Net " + hlapiClass.getClass());
        }

        PetriNetDocHLAPI petriNetDocHLAPI = (PetriNetDocHLAPI) hlapiClass;

        if (petriNetDocHLAPI.getNetsHLAPI().size() != 1) {
            throw new PetriNetReadingException("Unsupported number of nets " + petriNetDocHLAPI.getNetsHLAPI().size());
        }

        try {
            return convertFromPnml(petriNetDocHLAPI);
        } catch (CyclicReferencesException | WrongEdgeTypeException e) {
            throw new PetriNetReadingException(e);
        }
    }

    @NotNull
    private static PetriNet<Place, Transition> convertFromPnml(@NotNull PetriNetDocHLAPI petriNetDocHLAPI)
            throws CyclicReferencesException, WrongEdgeTypeException {
        PetriNet<Place, Transition> petriNet = new PetriNet<>();

        final PetriNetHLAPI petriNetHLAPI = petriNetDocHLAPI.getNetsHLAPI().get(0);

        Map<String, String> references = petriNetHLAPI.getPagesHLAPI()
                .stream()
                .map(PnmlReader::getReferencesFromPage)
                .collect(HashMap::new, Map::putAll, Map::putAll);

        petriNetHLAPI.getPagesHLAPI().forEach(page -> getVerticesFromPage(page, petriNet));
        for (PageHLAPI page : petriNetHLAPI.getPagesHLAPI()) {
            getArcsFromPage(page, petriNet, references);
        }

        return petriNet;
    }

    private static void getVerticesFromPage(@NotNull PageHLAPI page, @NotNull PetriNet<Place, Transition> petriNet) {
        for (PlaceHLAPI placeHLAPI : page.getObjects_PlaceHLAPI()) {
            Place place = Place.withMarks((int) (long)placeHLAPI.getInitialMarkingHLAPI().getText(),
                    placeHLAPI.getId());

            petriNet.addPlace(place);
        }

        for (TransitionHLAPI transitionHLAPI : page.getObjects_TransitionHLAPI()) {
            petriNet.addTransition(new TransitionImpl(transitionHLAPI.getId()));
        }
    }

    private static void getArcsFromPage(@NotNull PageHLAPI page, @NotNull PetriNet<Place, Transition> petriNet,
                                        @NotNull Map<String, String> references)
            throws CyclicReferencesException, WrongEdgeTypeException {

        for (ArcHLAPI arc : page.getObjects_ArcHLAPI()) {
            if (arc.getSourceHLAPI() instanceof PlaceNode && arc.getTargetHLAPI() instanceof TransitionNode) {
                Place from = petriNet.getPlacesMap()
                        .get(getOriginalIdFromRef(references, arc.getSourceHLAPI().getId()));

                Transition to = petriNet.getTransitionsMap()
                        .get(getOriginalIdFromRef(references, arc.getTargetHLAPI().getId()));

                from.addOutput(new FromPlaceToTransitionEdge(from, to));
            } else if (arc.getSourceHLAPI() instanceof TransitionNode && arc.getTargetHLAPI() instanceof PlaceNode) {
                Transition from = petriNet.getTransitionsMap()
                        .get(getOriginalIdFromRef(references, arc.getSourceHLAPI().getId()));

                Place to = petriNet.getPlacesMap()
                        .get(getOriginalIdFromRef(references, arc.getTargetHLAPI().getId()));

                from.addOutput(new FromTransitionToPlaceEdge(from, to));
            } else {
                throw new WrongEdgeTypeException("Wrong endpoint types: source = " + arc.getSourceHLAPI().getClass() +
                        ", target = " + arc.getTargetHLAPI().getClass());
            }
        }
    }

    @NotNull
    private static String getOriginalIdFromRef(@NotNull Map<String, String> references, @NotNull String refId)
            throws CyclicReferencesException {

        Set<String> visited = new HashSet<>();

        String current = refId;
        visited.add(current);

        while (references.containsKey(current)) {
            current = references.get(current);

            if (!visited.contains(current))
                visited.add(current);
            else
                throw new CyclicReferencesException("Cyclic references " + visited);
        }

        return current;
    }

    @NotNull
    private static Map<String, String> getReferencesFromPage(@NotNull PageHLAPI page) {
        Map<String, String> references = page.getObjects_RefPlaceHLAPI().stream()
                .collect(Collectors.toMap(RefPlaceHLAPI::getId, ref -> ref.getRef().getId()));

        references.putAll(page.getObjects_RefTransitionHLAPI()
                .stream()
                .collect(Collectors.toMap(RefTransitionHLAPI::getId, ref -> ref.getRef().getId())));

        return references;
    }

    public static class CyclicReferencesException extends Exception {
        CyclicReferencesException(String message) {
            super(message);
        }
    }

    public static class WrongEdgeTypeException extends Exception {
        WrongEdgeTypeException(String message) {
            super(message);
        }
    }
}
