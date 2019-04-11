package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.reductions;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.Reduction;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FusionOfParallelTransitionsTest {

    @Test
    void checkReduction() {
        Place place1 = Place.withMarks(0);
        Place place2 = Place.withMarks(0);

        Transition transition1 = new TransitionImpl();
        Transition transition2 = new TransitionImpl();

        place1.addOutput(new FromPlaceToTransitionEdge(place1, transition1));
        transition1.addOutput(new FromTransitionToPlaceEdge(transition1, place2));

        place1.addOutput(new FromPlaceToTransitionEdge(place1, transition2));
        transition2.addOutput(new FromTransitionToPlaceEdge(transition2, place2));

        Reduction<Place> reduction = new FusionOfParallelTransitions();

        assertTrue(reduction.reduceFrom(place1));
        assertEquals(1, place1.getOutputs().size());
        assertEquals(1, place2.getInputs().size());
    }

    @Test
    void unavailableOnlyOneEdge() {
        Place place1 = Place.withMarks(0);
        Place place2 = Place.withMarks(0);

        Transition transition1 = new TransitionImpl();

        place1.addOutput(new FromPlaceToTransitionEdge(place1, transition1));
        transition1.addOutput(new FromTransitionToPlaceEdge(transition1, place2));


        Reduction<Place> reduction = new FusionOfParallelTransitions();

        assertFalse(reduction.reduceFrom(place1));
        assertEquals(1, place1.getOutputs().size());
        assertEquals(1, place2.getInputs().size());
    }
}