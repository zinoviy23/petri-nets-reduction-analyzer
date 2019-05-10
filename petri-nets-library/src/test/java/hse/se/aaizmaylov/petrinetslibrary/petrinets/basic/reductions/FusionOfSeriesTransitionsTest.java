package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.reductions;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.ReductionHistory;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.TransformCallback;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.Reduction;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FusionOfSeriesTransitionsTest {
    @SuppressWarnings("deprecation")
    @Test
    void checkReduction() {
        Transition transition1 = new TransitionImpl("t1");
        Transition transition2 = new TransitionImpl("t2");
        Place place1 = Place.withMarks(2, "p1");
        Place place2 = Place.withMarks(1, "p2");

        transition1.addOutput(new FromTransitionToPlaceArc(transition1, place1));
        transition2.addInput(new FromPlaceToTransitionArc(place1, transition2));
        transition2.addOutput(new FromTransitionToPlaceArc(transition2, place2));

        Reduction<Transition, Place> reduction = new FusionOfSeriesTransitions();

        TransformCallbackImpl callback = new TransformCallbackImpl();

        assertTrue(reduction.reduceFrom(transition1, TransformCallback.invertedAdapter(callback),
                new ReductionHistory(Arrays.asList(transition1, transition2, place1, place2))));

        assertEquals(0, transition1.getInputs().size());
        assertEquals(1, transition2.getOutputs().size());
        assertEquals(place2, transition1.getOutputs().iterator().next().getToEndpoint());

        assertEquals(1, callback.getTransitions());
        assertEquals(1, callback.getPlaces());
    }
}