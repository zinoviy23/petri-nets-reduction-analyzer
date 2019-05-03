package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.Arc;
import lombok.Getter;
import lombok.Setter;

public class FromPlaceToTransitionArc implements Arc<Integer, Place, Transition> {

    @Getter
    @Setter
    private Place fromEndpoint;

    @Getter
    @Setter
    private Transition toEndpoint;

    public FromPlaceToTransitionArc(Place fromEndpoint, Transition toEndpoint) {
        this.fromEndpoint = fromEndpoint;
        this.toEndpoint = toEndpoint;
    }

    @Override
    public void getTokensFrom(Integer tokens) {
        fromEndpoint.removeMarks(tokens);
    }

    @Override
    public void putTokensTo(Integer tokens) {
    }
}
