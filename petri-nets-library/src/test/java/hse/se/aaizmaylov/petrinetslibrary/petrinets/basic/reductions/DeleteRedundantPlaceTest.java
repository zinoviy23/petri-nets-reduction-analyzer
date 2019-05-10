package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.reductions;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.PetriNet;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.InitializedReduction;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.ReductionHistory;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.TransformCallback;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.*;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.analysis.DefaultReductionInitializationData;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.SomePetriNets.fromDiazWithRedundantPlace;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeleteRedundantPlaceTest {
    @Test
    void deletePlace() {
        PetriNet<Place, Transition> petriNet = fromDiazWithRedundantPlace();
        InitializedReduction<DefaultReductionInitializationData, Place, Transition> reduction =
                new DeleteRedundantPlace();
        TransformCallbackImpl callback = new TransformCallbackImpl();

        reduction.initialize(new DefaultReductionInitializationData(petriNet));

        assertTrue(reduction.reduceFrom(petriNet.getPlacesMap().get("p"), callback,
                new ReductionHistory(petriNet.getTransitions(), petriNet.getPlaces())));

        assertTrue(petriNet.getTransitionsMap().get("t1").getOutputs()
                .stream()
                .noneMatch(arc -> arc.getToEndpoint().label().equals("p")));

        assertTrue(petriNet.getTransitionsMap().get("t3").getInputs()
                .stream()
                .noneMatch(arc -> arc.getFromEndpoint().label().equals("p")));
    }

    @Test
    void selfLoop() {
        Place p = Place.withMarks(1, "p");
        Transition t = new TransitionImpl("t");

        t.addInput(new FromPlaceToTransitionArc(p, t));
        t.addOutput(new FromTransitionToPlaceArc(t, p));

        PetriNet<Place, Transition> petriNet = new PetriNet<>(Collections.singleton(p), Collections.singleton(t));

        InitializedReduction<DefaultReductionInitializationData, Place, Transition> reduction =
                new DeleteRedundantPlace();

        reduction.initialize(new DefaultReductionInitializationData(petriNet));

        assertTrue(reduction.reduceFrom(p, TransformCallback.empty(), new ReductionHistory(Arrays.asList(p, t))));
    }

    @Test
    void selfLoopNotDisabling() {
        Place p = Place.withMarks(0, "p");
        Transition t = new TransitionImpl("t");

        t.addInput(new FromPlaceToTransitionArc(p, t));
        t.addOutput(new FromTransitionToPlaceArc(t, p));

        PetriNet<Place, Transition> petriNet = new PetriNet<>(Collections.singleton(p), Collections.singleton(t));

        InitializedReduction<DefaultReductionInitializationData, Place, Transition> reduction =
                new DeleteRedundantPlace();

        reduction.initialize(new DefaultReductionInitializationData(petriNet));

        assertFalse(reduction.reduceFrom(p, TransformCallback.empty(), new ReductionHistory(Arrays.asList(p, t))));
    }
}