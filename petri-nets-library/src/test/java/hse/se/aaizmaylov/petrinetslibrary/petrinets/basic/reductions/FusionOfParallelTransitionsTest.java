package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.reductions;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.Reduction;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FusionOfParallelTransitionsTest {

    @Test
    void checkReduction() {
        Place place1 = Place.withMarks(0, "p1");
        Place place2 = Place.withMarks(0, "p2");

        Transition transition1 = new TransitionImpl("t1");
        Transition transition2 = new TransitionImpl("t2");

        place1.addOutput(new FromPlaceToTransitionArc(place1, transition1));
        transition1.addOutput(new FromTransitionToPlaceArc(transition1, place2));

        place1.addOutput(new FromPlaceToTransitionArc(place1, transition2));
        transition2.addOutput(new FromTransitionToPlaceArc(transition2, place2));

        Reduction<Place, Transition> reduction = new FusionOfParallelTransitions();

        DeleteVertexCallbackImpl callback = new DeleteVertexCallbackImpl();

        assertTrue(reduction.reduceFrom(place1, callback));
        assertEquals(1, place1.getOutputs().size());
        assertEquals(1, place2.getInputs().size());

        assertTrue((transition1.getInputs().isEmpty() && transition1.getOutputs().isEmpty())
            ^ (transition2.getInputs().isEmpty() && transition2.getOutputs().isEmpty()));

        assertEquals(1, callback.getTransitions());
    }

    @Test
    void unavailableOnlyOneEdge() {
        Place place1 = Place.withMarks(0, "p1");
        Place place2 = Place.withMarks(0, "p2");

        Transition transition1 = new TransitionImpl("t1");

        place1.addOutput(new FromPlaceToTransitionArc(place1, transition1));
        transition1.addOutput(new FromTransitionToPlaceArc(transition1, place2));

        DeleteVertexCallbackImpl callback = new DeleteVertexCallbackImpl();

        Reduction<Place, Transition> reduction = new FusionOfParallelTransitions();

        assertFalse(reduction.reduceFrom(place1, callback));
        assertEquals(1, place1.getOutputs().size());
        assertEquals(1, place2.getInputs().size());

        assertEquals(0, callback.getTransitions());
    }
}