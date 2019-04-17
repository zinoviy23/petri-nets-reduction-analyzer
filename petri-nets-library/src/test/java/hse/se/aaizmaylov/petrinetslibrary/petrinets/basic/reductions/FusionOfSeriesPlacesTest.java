package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.reductions;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FusionOfSeriesPlacesTest {

    @Test
    void checkReduction() {
        Place place1 = Place.withMarks(0, "p1");
        Place place2 = Place.withMarks(0, "p2");

        Transition transition = new TransitionImpl("t1");
        Transition transition1 = new TransitionImpl("t2");

        transition.addInput(new FromPlaceToTransitionEdge(place1, transition));
        transition.addOutput(new FromTransitionToPlaceEdge(transition, place2));

        place2.addInput(new FromTransitionToPlaceEdge(transition1, place2));

        FusionOfSeriesPlaces reduction = new FusionOfSeriesPlaces();

        assertTrue(reduction.reduceFrom(place1));

        assertEquals(1, place1.getInputs().size());
        assertEquals(0, place1.getOutputs().size());
        assertEquals(transition1, place1.getInputs().iterator().next().getFromEndpoint());
    }
}