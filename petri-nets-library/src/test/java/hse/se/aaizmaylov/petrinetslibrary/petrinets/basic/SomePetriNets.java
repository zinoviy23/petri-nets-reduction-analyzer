package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.PetriNet;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class SomePetriNets {
    @Contract(" -> new")
    @NotNull
    public static PetriNet<Place, Transition> fromDiazWithRedundantPlace() {
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
}
