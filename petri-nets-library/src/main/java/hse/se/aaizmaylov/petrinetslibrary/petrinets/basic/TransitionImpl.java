package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.AbstractPetriNetVertexImpl;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.Edge;

public class TransitionImpl extends AbstractPetriNetVertexImpl<
        Integer,
        Transition,
        Place,
        Edge<Integer, Place, Transition>,
        Edge<Integer, Transition, Place>> implements Transition {

    public TransitionImpl(String label) {
        super(label);
    }

    @Override
    public void fire() {
        if (!enabled())
            return;

        getInputs().forEach(p -> p.getTokensFrom(1));
        getOutputs().forEach(p -> p.putTokensTo(1));
    }

    @Override
    public boolean enabled() {
        return getInputs().stream().allMatch(p -> p.getFromEndpoint().getMarks() > 0);
    }
}
