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

    @Contract(" -> new")
    @NotNull
    public static PetriNet<Place, Transition> fromMurata16() {
        Place p1 = Place.withMarks(1, "p1");
        Place p2 = Place.withMarks(0, "p2");
        Place p3 = Place.withMarks(0, "p3");

        Transition t0 = new TransitionImpl("t0");
        Transition t1 = new TransitionImpl("t1");
        Transition t2 = new TransitionImpl("t2");
        Transition t3 = new TransitionImpl("t3");

        t0.addInput(new FromPlaceToTransitionArc(p1, t0));
        t0.addInput(new FromPlaceToTransitionArc(p3, t0));

        t1.addInput(new FromPlaceToTransitionArc(p1, t1));
        t1.addOutput(new FromTransitionToPlaceArc(t1, p3));

        t2.addInput(new FromPlaceToTransitionArc(p3, t2));
        t2.addInput(new FromPlaceToTransitionArc(p2, t2));
        t2.addOutput(new FromTransitionToPlaceArc(t2, p3));

        t3.addOutput(new FromTransitionToPlaceArc(t3, p1));
        t3.addOutput(new FromTransitionToPlaceArc(t3, p2));
        t3.addInput(new FromPlaceToTransitionArc(p1, t3));

        return new PetriNet<>(Arrays.asList(p1, p2, p3), Arrays.asList(t0, t1, t2, t3));
    }

    @Contract(" -> new")
    @NotNull
    public static PetriNet<Place, Transition> fromMurata19a() {
        Place p1 = new PlaceImpl(1, "p1");
        Place p2 = new PlaceImpl(0, "p2");
        Place p3 = new PlaceImpl(0, "p3");

        Transition t1 = new TransitionImpl("t1");
        Transition t2 = new TransitionImpl("t2");
        Transition t3 = new TransitionImpl("t3");
        Transition t4 = new TransitionImpl("t4");

        t1.addInput(new FromPlaceToTransitionArc(p1, t1));
        t1.addOutput(new FromTransitionToPlaceArc(t1, p1));
        t1.addOutput(new FromTransitionToPlaceArc(t1, p3));

        t2.addInput(new FromPlaceToTransitionArc(p1, t2));
        t2.addInput(new FromPlaceToTransitionArc(p3, t2));
        t2.addOutput(new FromTransitionToPlaceArc(t2, p3));
        t2.addOutput(new FromTransitionToPlaceArc(t2, p2));

        t3.addInput(new FromPlaceToTransitionArc(p3, t3));
        t3.addInput(new FromPlaceToTransitionArc(p2, t3));
        t3.addOutput(new FromTransitionToPlaceArc(t3, p2));

        t4.addInput(new FromPlaceToTransitionArc(p2, t4));
        t4.addOutput(new FromTransitionToPlaceArc(t4, p1));

        return new PetriNet<>(Arrays.asList(p1, p2, p3), Arrays.asList(t1, t2, t3, t4));
    }
}
