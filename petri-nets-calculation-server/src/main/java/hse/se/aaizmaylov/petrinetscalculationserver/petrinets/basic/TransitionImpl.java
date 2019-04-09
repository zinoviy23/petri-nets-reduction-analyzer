package hse.se.aaizmaylov.petrinetscalculationserver.petrinets.basic;

import hse.se.aaizmaylov.petrinetscalculationserver.petrinets.AbstractPetriNetVertexImpl;
import hse.se.aaizmaylov.petrinetscalculationserver.petrinets.Edge;

public class TransitionImpl extends AbstractPetriNetVertexImpl<
        Integer,
        Transition,
        Place,
        Edge<Integer, Place, Transition>,
        Edge<Integer, Transition, Place>> implements Transition {


    @Override
    public void fire() {
        if (!enabled())
            return;

        getInputs().forEach(p -> p.getTokensFrom(1));
        getOutputs().forEach(p -> p.putTokensTo(1));
    }

    @Override
    public boolean enabled() {
        return getInputs().stream().allMatch(p -> p.getFrom().getMarks() > 0);
    }
}
