package hse.se.aaizmaylov.petrinetscalculationserver.petrinets;

import org.junit.Test;

import static org.junit.Assert.*;

public class AbstractPetriNetVertexImplTest {

    @Test
    public void addInput() {
        Place place = new PlaceImpl(1);
        Transition transition = new TransitionImpl();

        boolean res = place.addInput(transition);

        assertTrue(res);

        assertTrue(transition.getOutputs().contains(place));
        assertTrue(place.getInputs().contains(transition));

        res = place.addInput(transition);

        assertFalse(res);
    }

    @Test
    public void addOutput() {
        Place place = new PlaceImpl(1);
        Transition transition = new TransitionImpl();

        boolean res = place.addOutput(transition);

        assertTrue(res);

        assertTrue(transition.getInputs().contains(place));
        assertTrue(place.getOutputs().contains(transition));

        res = place.addOutput(transition);

        assertFalse(res);
    }

    @Test
    public void removeInput() {
        Place place = new PlaceImpl(1);
        Transition transition = new TransitionImpl();

        place.addOutput(transition);

        boolean res = transition.removeInput(place);

        assertTrue(res);

        assertFalse(transition.getInputs().contains(place));
        assertFalse(place.getOutputs().contains(transition));

        res = transition.removeInput(place);

        assertFalse(res);
    }

    @Test
    public void removeOutput() {
        Place place = new PlaceImpl(1);
        Transition transition = new TransitionImpl();

        place.addInput(transition);

        boolean res = transition.removeOutput(place);

        assertTrue(res);

        assertFalse(transition.getOutputs().contains(place));
        assertFalse(place.getInputs().contains(transition));

        res = transition.removeOutput(place);

        assertFalse(res);
    }

    @Test
    public void getInputs() {
        Place place = new PlaceImpl(1);
        place.addInput(new TransitionImpl());
        place.addInput(new TransitionImpl());
        place.addInput(new TransitionImpl());

        assertEquals(3, place.getInputs().size());
    }

    @Test
    public void getOutputs() {
        Place place = new PlaceImpl(1);
        place.addOutput(new TransitionImpl());
        place.addOutput(new TransitionImpl());
        place.addOutput(new TransitionImpl());

        assertEquals(3, place.getOutputs().size());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getInputsImmutable() {
        Place place = new PlaceImpl(1);

        place.getInputs().add(new TransitionImpl());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getOutputsImmutable() {
        Place place = new PlaceImpl(1);

        place.getOutputs().add(new TransitionImpl());
    }
}