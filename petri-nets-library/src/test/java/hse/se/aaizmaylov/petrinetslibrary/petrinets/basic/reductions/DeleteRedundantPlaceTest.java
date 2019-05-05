package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.reductions;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.PetriNet;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.InitializedReduction;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.Reduction;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DeleteRedundantPlaceTest {

    @Contract(" -> new")
    @NotNull
    private PetriNet<Place, Transition> fromDiaz() {
        Transition t1 = new TransitionImpl("t1");
        Transition t2 = new TransitionImpl("t2");
        Transition t3 = new TransitionImpl("t3");

        Place p = Place.withMarks(0, "p");
        Place q = Place.withMarks(0, "q");
        Place r = Place.withMarks(0, "r");

        t1.addOutput(new FromTransitionToPlaceArc(t1, q));
        t1.addOutput(new FromTransitionToPlaceArc(t1, p));

        t2.addInput(new FromPlaceToTransitionArc(q, t2));
        t2.addOutput(new FromTransitionToPlaceArc(t2, r));

        t3.addInput(new FromPlaceToTransitionArc(r, t3));
        t3.addInput(new FromPlaceToTransitionArc(p, t3));

        return new PetriNet<>(Arrays.asList(p, q, r), Arrays.asList(t1, t2, t3));
    }

    @Test
    void deletePlace() {
        PetriNet<Place, Transition> petriNet = fromDiaz();
        InitializedReduction<PetriNet<Place, Transition>, Place, Transition> reduction = new DeleteRedundantPlace();
        TransformCallbackImpl callback = new TransformCallbackImpl();

        reduction.initialize(petriNet);

        assertTrue(reduction.reduceFrom(petriNet.getPlacesMap().get("p"), callback));

        assertTrue(petriNet.getTransitionsMap().get("t1").getOutputs()
                .stream()
                .noneMatch(arc -> arc.getToEndpoint().label().equals("p")));

        assertTrue(petriNet.getTransitionsMap().get("t3").getInputs()
                .stream()
                .noneMatch(arc -> arc.getFromEndpoint().label().equals("p")));
    }
}