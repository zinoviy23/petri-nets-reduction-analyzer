package hse.se.aaizmaylov.petrinetscalculationserver.services;

import hse.se.aaizmaylov.petrinetscalculationserver.data.RequestInfo;
import hse.se.aaizmaylov.petrinetscalculationserver.data.RequestStatus;
import hse.se.aaizmaylov.petrinetscalculationserver.repository.RequestInfoRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RequestService {
    private final CalculationService calculationService;
    private final RequestInfoRepository requestInfoRepository;

    @Autowired
    public RequestService(CalculationService calculationService,
                          RequestInfoRepository requestInfoRepository) {

        this.calculationService = calculationService;
        this.requestInfoRepository = requestInfoRepository;
    }

    public RequestInfo addRequest(@NonNull RequestInfo requestInfo) {
        RequestInfo res = requestInfoRepository.save(requestInfo);
        calculationService.addRequest(requestInfo);

        return res;
    }

    public Optional<RequestInfo> findById(long id) {
        return requestInfoRepository.findById(id);
    }

    public Optional<RequestStatus> getStatus(long id) {
        return requestInfoRepository.findById(id).map(RequestInfo::getRequestStatus);
    }
}
