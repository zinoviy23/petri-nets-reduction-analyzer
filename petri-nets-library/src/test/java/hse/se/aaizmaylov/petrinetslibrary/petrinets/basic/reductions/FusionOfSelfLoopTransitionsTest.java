package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.reductions;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.*;
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

        assertTrue(reduction.reduceFrom(place));
        assertTrue(place.getOutputs().isEmpty());
        assertEquals(1, place.getInputs().size());

        assertTrue(transition.getOutputs().isEmpty() && transition.getInputs().isEmpty());
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

        assertFalse(reduction.reduceFrom(place));
        assertFalse(place.getOutputs().isEmpty());
        assertEquals(2, place.getInputs().size());
    }

    @Test
    void disabledBecauseNothingLoops() {
        Transition transition = new TransitionImpl();
        Place place = Place.withMarks(1);
        Place place1 = Place.withMarks(1);

        place.addOutput(new FromPlaceToTransitionEdge(place, transition));
        transition.addOutput(new FromTransitionToPlaceEdge(transition, place1));

        FusionOfSelfLoopTransitions reduction = new FusionOfSelfLoopTransitions();
        assertFalse(reduction.reduceFrom(place));
    }
}