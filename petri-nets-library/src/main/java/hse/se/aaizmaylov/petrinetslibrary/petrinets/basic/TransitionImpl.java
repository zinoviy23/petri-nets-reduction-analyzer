package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.AbstractPetriNetVertexImpl;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.Arc;

import java.util.Map;

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

    @Override
    public void fire(Map<Place, Long> marking) {
        if (!enabled(marking))
            return;

        getInputs().forEach(arc -> marking.merge(arc.getFromEndpoint(), arc.weight(), TransitionImpl::subtract));
        getOutputs().forEach(arc -> marking.merge(arc.getToEndpoint(), arc.weight(), TransitionImpl::sum));
    }

    @Override
    public boolean enabled(Map<Place, Long> marking) {
        return getInputs()
                .stream()
                .allMatch(p -> marking.get(p.getFromEndpoint()) >= p.weight()
                        || marking.get(p.getFromEndpoint()) == -1);
    }

    private static long subtract(long a, long b) {
        if (a == -1)
            return -1;

        return a - b;
    }

    private static long sum(long a, long b) {
        if (a == -1)
            return -1;

        return a + b;
    }
}
