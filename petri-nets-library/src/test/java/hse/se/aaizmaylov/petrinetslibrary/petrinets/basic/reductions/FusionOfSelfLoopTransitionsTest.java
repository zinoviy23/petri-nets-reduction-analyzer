package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.reductions;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.DeleteVertexCallback;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FusionOfSelfLoopTransitionsTest {
    @Test
    void checkReduction() {
        Transition transition = new TransitionImpl("t1");
        Transition transition1 = new TransitionImpl("t2");
        Place place = Place.withMarks(0, "p1");

        transition.addInput(new FromPlaceToTransitionEdge(place, transition));
        transition.addOutput(new FromTransitionToPlaceEdge(transition, place));
        place.addInput(new FromTransitionToPlaceEdge(transition1, place));

        FusionOfSelfLoopTransitions reduction = new FusionOfSelfLoopTransitions();

        DeleteVertexCallbackImpl callback = new DeleteVertexCallbackImpl();

        assertTrue(reduction.reduceFrom(place, callback));
        assertTrue(place.getOutputs().isEmpty());
        assertEquals(1, place.getInputs().size());

        assertTrue(transition.getOutputs().isEmpty() && transition.getInputs().isEmpty());

        assertEquals(1, callback.getTransitions());
    }

    @Test
    void disabled() {
        Transition transition = new TransitionImpl("t1");
        Transition transition1 = new TransitionImpl("t2");
        Place place = Place.withMarks(0, "p1");
        Place place1 = Place.withMarks(1, "p2");

        transition.addInput(new FromPlaceToTransitionEdge(place, transition));
        transition.addOutput(new FromTransitionToPlaceEdge(transition, place));
        place.addInput(new FromTransitionToPlaceEdge(transition1, place));
        transition.addInput(new FromPlaceToTransitionEdge(place1, transition));

        FusionOfSelfLoopTransitions reduction = new FusionOfSelfLoopTransitions();

        DeleteVertexCallbackImpl callback = new DeleteVertexCallbackImpl();

        assertFalse(reduction.reduceFrom(place, callback));
        assertFalse(place.getOutputs().isEmpty());
        assertEquals(2, place.getInputs().size());

        assertEquals(0, callback.getTransitions());
    }

    @Test
    void disabledBecauseNothingLoops() {
        Transition transition = new TransitionImpl("t1");
        Place place = Place.withMarks(1, "p1");
        Place place1 = Place.withMarks(1, "p2");

        place.addOutput(new FromPlaceToTransitionEdge(place, transition));
        transition.addOutput(new FromTransitionToPlaceEdge(transition, place1));

        FusionOfSelfLoopTransitions reduction = new FusionOfSelfLoopTransitions();
        assertFalse(reduction.reduceFrom(place, DeleteVertexCallback.empty()));
    }
}