package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.reductions;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.Arc;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.PetriNet;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.Reduction;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PreAgglomerationOfTransitionTest {

    PetriNet<Place, Transition> getNetFromDiaz() {
        Place p1 = Place.withMarks(1, "p1");
        Place p2 = Place.withMarks(1, "p2");
        Place p3 = Place.withMarks(1, "p3");
        Place pRed = Place.withMarks(0, "pRed");
        Place p4 = Place.withMarks(1, "p4");
        Place p5 = Place.withMarks(1, "p5");

        Transition h = new TransitionImpl("h");
        Transition f1 = new TransitionImpl("f1");
        Transition f2 = new TransitionImpl("f2");

        h.addInput(new FromPlaceToTransitionArc(p1, h));
        h.addInput(new FromPlaceToTransitionArc(p2, h));
        h.addOutput(new FromTransitionToPlaceArc(h, pRed));

        f1.addInput(new FromPlaceToTransitionArc(p3, f1));
        f1.addInput(new FromPlaceToTransitionArc(pRed, f1));
        f1.addOutput(new FromTransitionToPlaceArc(f1, p5));

        f2.addInput(new FromPlaceToTransitionArc(pRed, f2));
        f2.addInput(new FromPlaceToTransitionArc(p4, f2));

        return new PetriNet<>(
                Arrays.asList(p1, p2, p3, pRed, p4, p5),
                Arrays.asList(h, f1, f2));
    }

    @Test
    void simpleReduction() {
        PetriNet<Place, Transition> petriNet = getNetFromDiaz();

        Reduction<Place, Transition> reduction = new PreAgglomerationOfTransition();

        TransformCallbackImpl callback = new TransformCallbackImpl();

        assertTrue(reduction.reduceFrom(petriNet.getPlacesMap().get("pRed"), callback));

        assertEquals(1, callback.getPlaces());
        assertEquals(1, callback.getTransitions());

        assertTrue(petriNet.getPlaces()
                .stream()
                .flatMap(p -> p.getOutputs().stream())
                .map(Arc::getToEndpoint)
                .noneMatch(t -> t.label().equals("h"))
        );

        assertTrue(petriNet.getTransitions()
                .stream()
                .filter(t -> !t.label().equals("h"))
                .flatMap(t -> t.getOutputs().stream())
                .map(Arc::getToEndpoint)
                .noneMatch(p -> p.label().equals("pRed"))
        );

        assertEquals(2, petriNet.getPlacesMap().get("p1").getOutputs()
                .stream()
                .map(Arc::getToEndpoint)
                .filter(t -> t.label().equals("f1") || t.label().equals("f2"))
                .count()
        );

        assertEquals(2, petriNet.getPlacesMap().get("p2").getOutputs()
                .stream()
                .map(Arc::getToEndpoint)
                .filter(t -> t.label().equals("f1") || t.label().equals("f2"))
                .count()
        );
    }
}