package hse.se.aaizmaylov.petrinetscalculationserver.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class RequestInfo {
    @Id
    @GeneratedValue
    @Getter
    private long id;

    @Getter
    @Setter
    private String originalFileName;

    @Getter
    @Setter
    private String resultPath;

    @Getter
    @Setter
    @ElementCollection
    private List<String> reductions;

    @Getter
    @Setter
    private RequestType requestType;

    @Getter
    @Setter
    private RequestStatus requestStatus;

    @Getter
    @Setter
    private String error;

    @Getter
    @Setter
    private String errorLog;

    public RequestInfo(String originalFileName, RequestType requestType) {
        this.originalFileName = originalFileName;
        reductions = new ArrayList<>();
        this.requestType = requestType;
        this.requestStatus = RequestStatus.IN_QUEUE;
    }

    private RequestInfo() {}
}
