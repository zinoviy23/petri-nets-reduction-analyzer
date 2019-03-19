package hse.se.aaizmaylov.petrinetscalculationserver.petrinets.basic;

import hse.se.aaizmaylov.petrinetscalculationserver.petrinets.Edge;
import lombok.Getter;

public class FromTransitionToPlaceEdge implements Edge<Integer, Transition, Place> {

    @Getter
    private Transition input;

    @Getter
    private Place output;

    public FromTransitionToPlaceEdge(Transition input, Place output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public void getTokensFrom(Integer tokens) { }

    @Override
    public void putTokensTo(Integer tokens) {
        output.addMarks(tokens);
    }
}
