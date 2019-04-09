package hse.se.aaizmaylov.petrinetscalculationserver.petrinets.basic;

import hse.se.aaizmaylov.petrinetscalculationserver.petrinets.Edge;
import lombok.Getter;
import lombok.Setter;

public class FromTransitionToPlaceEdge implements Edge<Integer, Transition, Place> {

    @Getter
    @Setter
    private Transition from;

    @Getter
    @Setter
    private Place to;

    public FromTransitionToPlaceEdge(Transition from, Place to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public void getTokensFrom(Integer tokens) {
    }

    @Override
    public void putTokensTo(Integer tokens) {
        to.addMarks(tokens);
    }
}
