package hse.se.aaizmaylov.petrinetscalculationserver.controllers;

import hse.se.aaizmaylov.petrinetscalculationserver.data.RequestInfo;
import hse.se.aaizmaylov.petrinetscalculationserver.data.RequestStatus;
import hse.se.aaizmaylov.petrinetscalculationserver.data.RequestType;
import hse.se.aaizmaylov.petrinetscalculationserver.payload.AddRequestResponse;
import hse.se.aaizmaylov.petrinetscalculationserver.payload.RequestStatusResponse;
import hse.se.aaizmaylov.petrinetscalculationserver.services.FileStorageService;
import hse.se.aaizmaylov.petrinetscalculationserver.services.ReductionsInfoService;
import hse.se.aaizmaylov.petrinetscalculationserver.services.RequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
public class CalculationRequests {
    private static final Logger LOGGER = LoggerFactory.getLogger(CalculationRequests.class);

    private final FileStorageService fileStorageService;

    private final RequestService requestService;

    private final ReductionsInfoService reductionsInfoService;

    @Autowired
    public CalculationRequests(FileStorageService fileStorageService, RequestService requestService,
                               ReductionsInfoService reductionsInfoService) {
        this.fileStorageService = fileStorageService;
        this.requestService = requestService;
        this.reductionsInfoService = reductionsInfoService;
    }

    @PostMapping("/addRequest")
    public AddRequestResponse addRequest(@RequestParam("file") MultipartFile file,
                                         @RequestParam("type") String type,
                                         @RequestParam("reductions") String reductions) {

        LOGGER.info("upload!");

        RequestType requestType = RequestType.fromString(type);

        if (requestType == null)
            return new AddRequestResponse(-1, "Wrong type " + type);

        String fileName = fileStorageService.storeFile(file);
        String resultFileName = fileStorageService.generateResultPath(fileName);

        RequestInfo requestInfo = new RequestInfo(fileName, requestType);
        requestInfo.setResultPath(resultFileName);
        requestInfo.setReductions(reductionsInfoService.toList(reductions));

        requestInfo = requestService.addRequest(requestInfo);

        return new AddRequestResponse(requestInfo.getId(), "");
    }

    @GetMapping("/getStatus/{id:[0-9]+}")
    public RequestStatusResponse getStatus(@PathVariable String id) {
        long idd;

        try {
            idd = Long.parseLong(id);
        } catch (NumberFormatException e) {
            return new RequestStatusResponse("unknown", "", "");
        }

        Optional<RequestInfo> requestInfo = requestService.findById(idd);

        return requestInfo.map(info -> new RequestStatusResponse(
                info.getRequestStatus().toString(),
                info.getError(),
                info.getErrorLog()))
                .orElseGet(() -> new RequestStatusResponse("unknown", "", ""));

    }

    @GetMapping("/getResult/{id:[0-9]+}")
    public ResponseEntity<Resource> getResult(@PathVariable String id) {
        LOGGER.info("Get " + id);

        long idd;

        try {
            idd = Long.parseLong(id);
        } catch (NumberFormatException ex) {
            return ResponseEntity.badRequest().build();
        }

        Optional<RequestStatus> status = requestService.getStatus(idd);
        if (!status.isPresent() || status.get() != RequestStatus.READY)
            return ResponseEntity.badRequest().build();

        Optional<RequestInfo> requestInfo = requestService.findById(idd);

        if (!requestInfo.isPresent())
            return ResponseEntity.badRequest().build();

        Resource resource = fileStorageService.loadFileAsResource(requestInfo.get().getResultPath());

        String contentType = "application/octet-stream";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
