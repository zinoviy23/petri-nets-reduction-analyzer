package hse.se.aaizmaylov.petrinetscalculationserver.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddRequestResponse {
    private long id;

    private String error;
}
