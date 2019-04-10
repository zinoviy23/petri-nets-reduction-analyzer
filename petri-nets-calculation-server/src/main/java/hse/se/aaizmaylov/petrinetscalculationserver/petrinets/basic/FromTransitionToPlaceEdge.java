package hse.se.aaizmaylov.petrinetscalculationserver.petrinets.basic;

import hse.se.aaizmaylov.petrinetscalculationserver.petrinets.Edge;
import lombok.Getter;
import lombok.Setter;

public class FromTransitionToPlaceEdge implements Edge<Integer, Transition, Place> {

    @Getter
    @Setter
    private Transition fromEndpoint;

    @Getter
    @Setter
    private Place toEndpoint;

    public FromTransitionToPlaceEdge(Transition fromEndpoint, Place toEndpoint) {
        this.fromEndpoint = fromEndpoint;
        this.toEndpoint = toEndpoint;
    }

    @Override
    public void getTokensFrom(Integer tokens) {
    }

    @Override
    public void putTokensTo(Integer tokens) {
        toEndpoint.addMarks(tokens);
    }
}
