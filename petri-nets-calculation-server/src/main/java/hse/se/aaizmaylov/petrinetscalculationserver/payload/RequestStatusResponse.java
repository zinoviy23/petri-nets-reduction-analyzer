package hse.se.aaizmaylov.petrinetscalculationserver.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequestStatusResponse {
    private String status;

    private String error;

    private String errorLog;
}
