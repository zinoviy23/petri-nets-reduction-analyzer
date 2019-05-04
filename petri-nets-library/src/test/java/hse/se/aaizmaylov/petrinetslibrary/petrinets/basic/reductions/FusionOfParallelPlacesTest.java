package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.reductions;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.TransformCallback;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.Reduction;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FusionOfParallelPlacesTest {
    @Test
    void checkReduction() {
        Transition transition1 = new TransitionImpl("t1");
        Transition transition2 = new TransitionImpl("t2");
        Place place1 = Place.withMarks(0, "p1");
        Place place2 = Place.withMarks(0, "p2");

        transition1.addOutput(new FromTransitionToPlaceArc(transition1, place1));
        place1.addOutput(new FromPlaceToTransitionArc(place1, transition2));

        transition1.addOutput(new FromTransitionToPlaceArc(transition1, place2));
        place2.addOutput(new FromPlaceToTransitionArc(place2, transition2));

        Reduction<Transition, Place> reduction = new FusionOfParallelPlaces();

        TransformCallbackImpl callback = new TransformCallbackImpl();

        assertTrue(reduction.reduceFrom(transition1, TransformCallback.invertedAdapter(callback)));
        assertEquals(1, transition1.getOutputs().size());
        assertEquals(1, transition2.getInputs().size());

        assertTrue((place2.getInputs().isEmpty() && place2.getOutputs().isEmpty())
                ^ (place1.getInputs().isEmpty() && place1.getOutputs().isEmpty()));

        assertEquals(1, callback.getPlaces());
    }

    @Test
    void unavailableBecauseOfMarks() {
        Transition transition1 = new TransitionImpl("t1");
        Transition transition2 = new TransitionImpl("t2");
        Place place1 = Place.withMarks(1, "p1");
        Place place2 = Place.withMarks(1, "p2");

        transition1.addOutput(new FromTransitionToPlaceArc(transition1, place1));
        place1.addOutput(new FromPlaceToTransitionArc(place1, transition2));

        transition1.addOutput(new FromTransitionToPlaceArc(transition1, place2));
        place2.addOutput(new FromPlaceToTransitionArc(place2, transition2));

        Reduction<Transition, Place> reduction = new FusionOfParallelPlaces();

        TransformCallbackImpl callback = new TransformCallbackImpl();

        assertFalse(reduction.reduceFrom(transition1, TransformCallback.invertedAdapter(callback)));
        assertEquals(2, transition1.getOutputs().size());
        assertEquals(2, transition2.getInputs().size());

        assertEquals(0, callback.getPlaces());
    }

    @Test
    void unavailableBecauseOfOneEdge() {
        Transition transition1 = new TransitionImpl("t1");
        Transition transition2 = new TransitionImpl("t2");
        Place place1 = Place.withMarks(0, "p1");

        transition1.addOutput(new FromTransitionToPlaceArc(transition1, place1));
        place1.addOutput(new FromPlaceToTransitionArc(place1, transition2));

        Reduction<Transition, Place> reduction = new FusionOfParallelPlaces();

        assertFalse(reduction.reduceFrom(transition1, TransformCallback.empty()));
        assertEquals(1, transition1.getOutputs().size());
        assertEquals(1, transition2.getInputs().size());
    }
}