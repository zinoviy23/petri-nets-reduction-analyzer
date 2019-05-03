package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.Arc;
import lombok.Getter;
import lombok.Setter;

public class FromTransitionToPlaceArc implements Arc<Integer, Transition, Place> {

    @Getter
    @Setter
    private Transition fromEndpoint;

    @Getter
    @Setter
    private Place toEndpoint;

    public FromTransitionToPlaceArc(Transition fromEndpoint, Place toEndpoint) {
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
