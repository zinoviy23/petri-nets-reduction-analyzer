package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.reductions;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.DeleteVertexCallback;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.Reduction;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FusionOfSeriesTransitionsTest {
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

        DeleteVertexCallbackImpl callback = new DeleteVertexCallbackImpl();

        assertTrue(reduction.reduceFrom(transition1, DeleteVertexCallback.invertedAdapter(callback)));

        assertEquals(0, transition1.getInputs().size());
        assertEquals(1, transition2.getOutputs().size());
        assertEquals(place2, transition1.getOutputs().iterator().next().getToEndpoint());

        assertEquals(1, callback.getTransitions());
        assertEquals(1, callback.getPlaces());
    }
}