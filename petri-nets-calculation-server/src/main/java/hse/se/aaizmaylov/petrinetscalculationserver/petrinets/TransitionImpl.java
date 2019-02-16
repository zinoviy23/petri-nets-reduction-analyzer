package hse.se.aaizmaylov.petrinetscalculationserver.petrinets;

public class TransitionImpl extends AbstractPetriNetVertexImpl<Transition, Place> implements Transition {
    @Override
    public void fire() {
        if (!enabled())
            return;

        getInputs().forEach(p -> p.removeMarks(1));
        getOutputs().forEach(p -> p.addMarks(1));
    }

    @Override
    public boolean enabled() {
        return getInputs().stream().allMatch(p -> p.getMarks() > 0);
    }
}
