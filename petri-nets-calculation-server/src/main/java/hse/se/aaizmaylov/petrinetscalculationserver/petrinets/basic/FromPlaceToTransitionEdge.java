package hse.se.aaizmaylov.petrinetscalculationserver.petrinets.basic;

import hse.se.aaizmaylov.petrinetscalculationserver.petrinets.Edge;
import lombok.Getter;
import lombok.Setter;

public class FromPlaceToTransitionEdge implements Edge<Integer, Place, Transition> {

    @Getter
    @Setter
    private Place fromEndpoint;

    @Getter
    @Setter
    private Transition toEndpoint;

    public FromPlaceToTransitionEdge(Place fromEndpoint, Transition toEndpoint) {
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
