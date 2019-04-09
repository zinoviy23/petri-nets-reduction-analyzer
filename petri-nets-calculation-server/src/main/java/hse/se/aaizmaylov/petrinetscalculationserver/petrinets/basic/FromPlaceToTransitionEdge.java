package hse.se.aaizmaylov.petrinetscalculationserver.petrinets.basic;

import hse.se.aaizmaylov.petrinetscalculationserver.petrinets.Edge;
import lombok.Getter;
import lombok.Setter;

public class FromPlaceToTransitionEdge implements Edge<Integer, Place, Transition> {

    @Getter
    @Setter
    private Place from;

    @Getter
    @Setter
    private Transition to;

    public FromPlaceToTransitionEdge(Place from, Transition to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public void getTokensFrom(Integer tokens) {
        from.removeMarks(tokens);
    }

    @Override
    public void putTokensTo(Integer tokens) {
    }
}
