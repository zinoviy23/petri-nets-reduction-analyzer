package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.reductions;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.*;
import org.junit.jupiter.api.Test;

import static hse.se.aaizmaylov.petrinetslibrary.utils.CollectionsUtils.first;
import static org.junit.jupiter.api.Assertions.*;

class FusionOfSelfLoopPlacesTest {
    @Test
    void checkReduction() {
        Place place = Place.withMarks(1);
        Transition transition = new TransitionImpl();
        Place place1 = Place.withMarks(0);

        transition.addOutput(new FromTransitionToPlaceEdge(transition, place));
        transition.addInput(new FromPlaceToTransitionEdge(place, transition));
        transition.addOutput(new FromTransitionToPlaceEdge(transition, place1));

        FusionOfSelfLoopPlaces reduction = new FusionOfSelfLoopPlaces();

        assertTrue(reduction.reduceFrom(place));
        assertTrue(transition.getInputs().isEmpty());
        assertEquals(1, transition.getOutputs().size());
        assertEquals(place1, first(transition.getOutputs()).getToEndpoint());
    }

    @Test
    void checkReductionDisabledBecauseOfMarks() {
        Place place = Place.withMarks(0);
        Transition transition = new TransitionImpl();
        Place place1 = Place.withMarks(0);

        transition.addOutput(new FromTransitionToPlaceEdge(transition, place));
        transition.addInput(new FromPlaceToTransitionEdge(place, transition));
        transition.addOutput(new FromTransitionToPlaceEdge(transition, place1));

        FusionOfSelfLoopPlaces reduction = new FusionOfSelfLoopPlaces();

        assertFalse(reduction.reduceFrom(place));
        assertEquals(1, transition.getInputs().size());
        assertEquals(2, transition.getOutputs().size());
    }

    @Test
    void checkReductionDisabledBecauseOfEdges() {
        Place place = Place.withMarks(1);
        Transition transition = new TransitionImpl();
        Transition transition1 = new TransitionImpl();
        Place place1 = Place.withMarks(0);

        transition.addOutput(new FromTransitionToPlaceEdge(transition, place));
        transition.addInput(new FromPlaceToTransitionEdge(place, transition));
        transition.addOutput(new FromTransitionToPlaceEdge(transition, place1));
        transition1.addOutput(new FromTransitionToPlaceEdge(transition1, place));

        FusionOfSelfLoopPlaces reduction = new FusionOfSelfLoopPlaces();

        assertFalse(reduction.reduceFrom(place));
        assertEquals(1, transition.getInputs().size());
        assertEquals(2, transition.getOutputs().size());
        assertEquals(2, place.getInputs().size());
        assertEquals(1, place.getOutputs().size());
    }
}