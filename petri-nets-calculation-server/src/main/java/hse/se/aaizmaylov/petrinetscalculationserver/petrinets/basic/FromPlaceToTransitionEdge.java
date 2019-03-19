package hse.se.aaizmaylov.petrinetscalculationserver.petrinets.basic;

import hse.se.aaizmaylov.petrinetscalculationserver.petrinets.Edge;
import lombok.Getter;

public class FromPlaceToTransitionEdge implements Edge<Integer, Place, Transition> {

    @Getter
    private Place input;

    @Getter
    private Transition output;

    public FromPlaceToTransitionEdge(Place input, Transition output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public void getTokensFrom(Integer tokens) {
        input.removeMarks(tokens);
    }

    @Override
    public void putTokensTo(Integer tokens) { }
}
