package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.reductions;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.Arc;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.PetriNet;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.Reduction;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.ReductionHistory;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class PostAgglomerationOfTransitionsTest {

    PetriNet<Place, Transition> fromDiaz() {
        Place p1 = Place.withMarks(1, "p1");
        Place p2 = Place.withMarks(1, "p2");
        Place pRed = Place.withMarks(0, "pRed");
        Place p3 = Place.withMarks(1, "p3");
        Place p4 = Place.withMarks(1, "p4");
        Place p5 = Place.withMarks(1, "p5");

        Transition h1 = new TransitionImpl("h1");
        Transition h2 = new TransitionImpl("h2");
        Transition f1 = new TransitionImpl("f1");
        Transition f2 = new TransitionImpl("f2");

        h1.addInput(new FromPlaceToTransitionArc(p1, h1));
        h1.addOutput(new FromTransitionToPlaceArc(h1, p2));
        h1.addOutput(new FromTransitionToPlaceArc(h1, pRed));

        h2.addOutput(new FromTransitionToPlaceArc(h2, pRed));
        h2.addOutput(new FromTransitionToPlaceArc(h2, p3));

        f1.addInput(new FromPlaceToTransitionArc(pRed, f1));
        f1.addOutput(new FromTransitionToPlaceArc(f1, p4));

        f2.addInput(new FromPlaceToTransitionArc(pRed, f2));
        f2.addOutput(new FromTransitionToPlaceArc(f2, p5));

        return new PetriNet<>(Arrays.asList(p1, p2, pRed, p3, p4, p5), Arrays.asList(h1, h2, f1, f2));
    }

    @Test
    void simpleReduction() {
        PetriNet<Place, Transition> petriNet = fromDiaz();

        TransformCallbackImpl callback = new TransformCallbackImpl();

        Reduction<Place, Transition> reduction = new PostAgglomerationOfTransitions();

        ReductionHistory history = new ReductionHistory(petriNet.getPlaces(), petriNet.getTransitions());
        assertTrue(reduction.reduceFrom(petriNet.getPlacesMap().get("pRed"), callback, history));

        assertEquals(1, callback.getPlaces());
        assertEquals(4, callback.getTransitions());
        assertEquals(0, callback.getAddedPlaces());
        assertEquals(4, callback.getAddedTransitions());

        List<Transition> ts = petriNet.getPlacesMap().get("p1").getOutputs()
                .stream()
                .map(Arc::getToEndpoint)
                .collect(Collectors.toList());

        assertTrue(ts.stream().anyMatch(t -> t.label().equals("h1.f1")));
        assertTrue(ts.stream().anyMatch(t -> t.label().equals("h1.f2")));
        assertEquals(2, ts.size());

        ts = petriNet.getPlacesMap().get("p2").getInputs()
                .stream()
                .map(Arc::getFromEndpoint)
                .collect(Collectors.toList());

        assertTrue(ts.stream().anyMatch(t -> t.label().equals("h1.f1")));
        assertTrue(ts.stream().anyMatch(t -> t.label().equals("h1.f2")));
        assertEquals(2, ts.size());

        ts = petriNet.getPlacesMap().get("p3").getInputs()
                .stream()
                .map(Arc::getFromEndpoint)
                .collect(Collectors.toList());

        assertTrue(ts.stream().anyMatch(t -> t.label().equals("h2.f1")));
        assertTrue(ts.stream().anyMatch(t -> t.label().equals("h2.f2")));
        assertEquals(2, ts.size());

        ts = petriNet.getPlacesMap().get("p4").getInputs()
                .stream()
                .map(Arc::getFromEndpoint)
                .collect(Collectors.toList());

        assertTrue(ts.stream().anyMatch(t -> t.label().equals("h1.f1")));
        assertTrue(ts.stream().anyMatch(t -> t.label().equals("h2.f1")));
        assertEquals(2, ts.size());

        ts = petriNet.getPlacesMap().get("p5").getInputs()
                .stream()
                .map(Arc::getFromEndpoint)
                .collect(Collectors.toList());

        assertTrue(ts.stream().anyMatch(t -> t.label().equals("h1.f2")));
        assertTrue(ts.stream().anyMatch(t -> t.label().equals("h2.f2")));
        assertEquals(2, ts.size());

        Transition transition = ts.stream()
                .filter(t -> t.label().equals("h1.f2"))
                .findAny()
                .orElseThrow(() -> new AssertionError("Doesn't contains h1.f2"));

        List<String> associated = assertDoesNotThrow(() -> history.getAssociated(transition));

        assertTrue(associated.contains("h1"));
        assertTrue(associated.contains("f2"));
    }

}