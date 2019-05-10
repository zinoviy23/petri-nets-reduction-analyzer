package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.reductions;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.ReductionHistory;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FusionOfSeriesPlacesTest {

    @Test
    void checkReduction() {
        Place place1 = Place.withMarks(0, "p1");
        Place place2 = Place.withMarks(0, "p2");

        Transition transition = new TransitionImpl("t1");
        Transition transition1 = new TransitionImpl("t2");

        transition.addInput(new FromPlaceToTransitionArc(place1, transition));
        transition.addOutput(new FromTransitionToPlaceArc(transition, place2));

        place2.addInput(new FromTransitionToPlaceArc(transition1, place2));

        FusionOfSeriesPlaces reduction = new FusionOfSeriesPlaces();

        TransformCallbackImpl callback = new TransformCallbackImpl();

        assertTrue(reduction.reduceFrom(place1, callback,
                new ReductionHistory(Arrays.asList(transition1, transition, place1, place2))));

        assertEquals(1, place1.getInputs().size());
        assertEquals(0, place1.getOutputs().size());
        assertEquals(transition1, place1.getInputs().iterator().next().getFromEndpoint());

        assertEquals(1, callback.getPlaces());
        assertEquals(1, callback.getTransitions());
    }
}