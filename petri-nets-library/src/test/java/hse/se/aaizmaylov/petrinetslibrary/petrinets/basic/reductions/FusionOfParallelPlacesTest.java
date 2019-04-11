package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.reductions;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.Reduction;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FusionOfParallelPlacesTest {
    @Test
    void checkReduction() {
        Transition transition1 = new TransitionImpl();
        Transition transition2 = new TransitionImpl();
        Place place1 = Place.withMarks(0);
        Place place2 = Place.withMarks(0);

        transition1.addOutput(new FromTransitionToPlaceEdge(transition1, place1));
        place1.addOutput(new FromPlaceToTransitionEdge(place1, transition2));

        transition1.addOutput(new FromTransitionToPlaceEdge(transition1, place2));
        place2.addOutput(new FromPlaceToTransitionEdge(place2, transition2));

        Reduction<Transition> reduction = new FusionOfParallelPlaces();

        assertTrue(reduction.reduceFrom(transition1));
        assertEquals(1, transition1.getOutputs().size());
        assertEquals(1, transition2.getInputs().size());
    }

    @Test
    void unavailableBecauseOfMarks() {
        Transition transition1 = new TransitionImpl();
        Transition transition2 = new TransitionImpl();
        Place place1 = Place.withMarks(1);
        Place place2 = Place.withMarks(1);

        transition1.addOutput(new FromTransitionToPlaceEdge(transition1, place1));
        place1.addOutput(new FromPlaceToTransitionEdge(place1, transition2));

        transition1.addOutput(new FromTransitionToPlaceEdge(transition1, place2));
        place2.addOutput(new FromPlaceToTransitionEdge(place2, transition2));

        Reduction<Transition> reduction = new FusionOfParallelPlaces();

        assertFalse(reduction.reduceFrom(transition1));
        assertEquals(2, transition1.getOutputs().size());
        assertEquals(2, transition2.getInputs().size());
    }

    @Test
    void unavailableBecauseOfOneEdge() {
        Transition transition1 = new TransitionImpl();
        Transition transition2 = new TransitionImpl();
        Place place1 = Place.withMarks(0);

        transition1.addOutput(new FromTransitionToPlaceEdge(transition1, place1));
        place1.addOutput(new FromPlaceToTransitionEdge(place1, transition2));

        Reduction<Transition> reduction = new FusionOfParallelPlaces();

        assertFalse(reduction.reduceFrom(transition1));
        assertEquals(1, transition1.getOutputs().size());
        assertEquals(1, transition2.getInputs().size());
    }
}