package hse.se.aaizmaylov.petrinetscalculationserver.petrinets.basic.reductions;

import hse.se.aaizmaylov.petrinetscalculationserver.petrinets.basic.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FusionOfSelfLoopTransitionsTest {
    @Test
    void checkReduction() {
        Transition transition = new TransitionImpl();
        Transition transition1 = new TransitionImpl();
        Place place = Place.withMarks(0);

        transition.addInput(new FromPlaceToTransitionEdge(place, transition));
        transition.addOutput(new FromTransitionToPlaceEdge(transition, place));
        place.addInput(new FromTransitionToPlaceEdge(transition1, place));

        FusionOfSelfLoopTransitions reduction = new FusionOfSelfLoopTransitions();

        assertTrue(reduction.reduceFrom(transition));
        assertTrue(place.getOutputs().isEmpty());
        assertEquals(1, place.getInputs().size());
    }

    @Test
    void disabled() {
        Transition transition = new TransitionImpl();
        Transition transition1 = new TransitionImpl();
        Place place = Place.withMarks(0);
        Place place1 = Place.withMarks(1);

        transition.addInput(new FromPlaceToTransitionEdge(place, transition));
        transition.addOutput(new FromTransitionToPlaceEdge(transition, place));
        place.addInput(new FromTransitionToPlaceEdge(transition1, place));
        transition.addInput(new FromPlaceToTransitionEdge(place1, transition));

        FusionOfSelfLoopTransitions reduction = new FusionOfSelfLoopTransitions();

        assertFalse(reduction.reduceFrom(transition));
        assertFalse(place.getOutputs().isEmpty());
        assertEquals(2, place.getInputs().size());
    }
}