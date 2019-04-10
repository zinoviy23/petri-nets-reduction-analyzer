package hse.se.aaizmaylov.petrinetscalculationserver.petrinets.basic.reductions;

import hse.se.aaizmaylov.petrinetscalculationserver.petrinets.analysis.Reduction;
import hse.se.aaizmaylov.petrinetscalculationserver.petrinets.basic.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FusionOfSeriesTransitionsTest {
    @Test
    void checkReduction() {
        Transition transition1 = new TransitionImpl();
        Transition transition2 = new TransitionImpl();
        Place place1 = Place.withMarks(2);
        Place place2 = Place.withMarks(1);

        transition1.addOutput(new FromTransitionToPlaceEdge(transition1, place1));
        transition2.addInput(new FromPlaceToTransitionEdge(place1, transition2));
        transition2.addOutput(new FromTransitionToPlaceEdge(transition2, place2));

        Reduction<Transition> reduction = new FusionOfSeriesTransitions();

        assertTrue(reduction.reduceFrom(transition1));

        assertEquals(0, transition1.getInputs().size());
        assertEquals(1, transition2.getOutputs().size());
        assertEquals(place2, transition1.getOutputs().iterator().next().getToEndpoint());
    }
}