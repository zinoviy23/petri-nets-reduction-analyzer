package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.reductions;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.DeleteVertexCallback;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.Reduction;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.*;
import org.junit.jupiter.api.Test;

import static hse.se.aaizmaylov.petrinetslibrary.utils.CollectionsUtils.first;
import static org.junit.jupiter.api.Assertions.*;

class FusionOfSelfLoopPlacesTest {
    @Test
    void checkReduction() {
        Place place = Place.withMarks(1, "p1");
        Transition transition = new TransitionImpl("t1");
        Place place1 = Place.withMarks(0, "p2");

        transition.addOutput(new FromTransitionToPlaceArc(transition, place));
        transition.addInput(new FromPlaceToTransitionArc(place, transition));
        transition.addOutput(new FromTransitionToPlaceArc(transition, place1));

        FusionOfSelfLoopPlaces reduction = new FusionOfSelfLoopPlaces();

        DeleteVertexCallbackImpl callback = new DeleteVertexCallbackImpl();

        assertTrue(reduction.reduceFrom(transition, DeleteVertexCallback.invertedAdapter(callback)));
        assertTrue(transition.getInputs().isEmpty());
        assertEquals(1, transition.getOutputs().size());
        assertEquals(place1, first(transition.getOutputs()).getToEndpoint());

        assertTrue(place.getInputs().isEmpty() && place.getOutputs().isEmpty());

        assertEquals(1, callback.getPlaces());
    }

    @Test
    void checkReductionDisabledBecauseOfMarks() {
        Place place = Place.withMarks(0, "p1");
        Transition transition = new TransitionImpl("t1");
        Place place1 = Place.withMarks(0, "p2");

        transition.addOutput(new FromTransitionToPlaceArc(transition, place));
        transition.addInput(new FromPlaceToTransitionArc(place, transition));
        transition.addOutput(new FromTransitionToPlaceArc(transition, place1));

        FusionOfSelfLoopPlaces reduction = new FusionOfSelfLoopPlaces();

        DeleteVertexCallbackImpl callback = new DeleteVertexCallbackImpl();

        assertFalse(reduction.reduceFrom(transition, DeleteVertexCallback.invertedAdapter(callback)));
        assertEquals(1, transition.getInputs().size());
        assertEquals(2, transition.getOutputs().size());

        assertEquals(0, callback.getPlaces());
    }

    @Test
    void checkReductionDisabledBecauseOfEdges() {
        Place place = Place.withMarks(1, "p1");
        Transition transition = new TransitionImpl("t1");
        Transition transition1 = new TransitionImpl("t2");
        Place place1 = Place.withMarks(0, "p2");

        transition.addOutput(new FromTransitionToPlaceArc(transition, place));
        transition.addInput(new FromPlaceToTransitionArc(place, transition));
        transition.addOutput(new FromTransitionToPlaceArc(transition, place1));
        transition1.addOutput(new FromTransitionToPlaceArc(transition1, place));

        FusionOfSelfLoopPlaces reduction = new FusionOfSelfLoopPlaces();

        DeleteVertexCallbackImpl callback = new DeleteVertexCallbackImpl();

        assertFalse(reduction.reduceFrom(transition, DeleteVertexCallback.invertedAdapter(callback)));
        assertEquals(1, transition.getInputs().size());
        assertEquals(2, transition.getOutputs().size());
        assertEquals(2, place.getInputs().size());
        assertEquals(1, place.getOutputs().size());
    }

    @Test
    void disabledBecauseNothingLoops() {
        Transition transition = new TransitionImpl("t1");
        Transition transition1 = new TransitionImpl("t2");
        Place place = Place.withMarks(1, "p1");

        place.addOutput(new FromPlaceToTransitionArc(place, transition1));
        transition.addOutput(new FromTransitionToPlaceArc(transition, place));

        Reduction<Transition, Place> reduction = new FusionOfSelfLoopPlaces();
        assertFalse(reduction.reduceFrom(transition, DeleteVertexCallback.empty()));
    }
}