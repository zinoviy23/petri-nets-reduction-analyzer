package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.AbstractPetriNetVertexImpl;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.Arc;

public class TransitionImpl extends AbstractPetriNetVertexImpl<
        Long,
        Long,
        Transition,
        Place,
        Arc<Long, Long, Place, Transition>,
        Arc<Long, Long, Transition, Place>> implements Transition {

    public TransitionImpl(String label) {
        super(label);
    }

    @Override
    public void fire() {
        if (!enabled())
            return;

        getInputs().forEach(p -> p.getTokensFrom(p.weight()));
        getOutputs().forEach(p -> p.putTokensTo(p.weight()));
    }

    @Override
    public boolean enabled() {
        return getInputs().stream().allMatch(p -> p.getFromEndpoint().getMarks() >= p.weight());
    }
}
