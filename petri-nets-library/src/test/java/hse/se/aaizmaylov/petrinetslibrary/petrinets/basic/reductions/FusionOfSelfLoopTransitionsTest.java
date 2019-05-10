package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.reductions;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.ReductionHistory;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.TransformCallback;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FusionOfSelfLoopTransitionsTest {
    @Test
    void checkReduction() {
        Transition transition = new TransitionImpl("t1");
        Transition transition1 = new TransitionImpl("t2");
        Place place = Place.withMarks(0, "p1");

        transition.addInput(new FromPlaceToTransitionArc(place, transition));
        transition.addOutput(new FromTransitionToPlaceArc(transition, place));
        place.addInput(new FromTransitionToPlaceArc(transition1, place));

        FusionOfSelfLoopTransitions reduction = new FusionOfSelfLoopTransitions();

        TransformCallbackImpl callback = new TransformCallbackImpl();

        assertTrue(reduction.reduceFrom(place, callback,
                new ReductionHistory(Arrays.asList(transition1, transition, place))));
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

        transition.addInput(new FromPlaceToTransitionArc(place, transition));
        transition.addOutput(new FromTransitionToPlaceArc(transition, place));
        place.addInput(new FromTransitionToPlaceArc(transition1, place));
        transition.addInput(new FromPlaceToTransitionArc(place1, transition));

        FusionOfSelfLoopTransitions reduction = new FusionOfSelfLoopTransitions();

        TransformCallbackImpl callback = new TransformCallbackImpl();

        assertFalse(reduction.reduceFrom(place, callback,
                new ReductionHistory(Arrays.asList(transition1, transition, place1, place))));
        assertFalse(place.getOutputs().isEmpty());
        assertEquals(2, place.getInputs().size());

        assertEquals(0, callback.getTransitions());
    }

    @Test
    void disabledBecauseNothingLoops() {
        Transition transition = new TransitionImpl("t1");
        Place place = Place.withMarks(1, "p1");
        Place place1 = Place.withMarks(1, "p2");

        place.addOutput(new FromPlaceToTransitionArc(place, transition));
        transition.addOutput(new FromTransitionToPlaceArc(transition, place1));

        FusionOfSelfLoopTransitions reduction = new FusionOfSelfLoopTransitions();
        assertFalse(reduction.reduceFrom(place, TransformCallback.empty(),
                new ReductionHistory(Arrays.asList(transition, place1, place))));
    }
}