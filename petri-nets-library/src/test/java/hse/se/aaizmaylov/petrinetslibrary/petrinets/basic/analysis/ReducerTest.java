package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.analysis;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.*;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.reductions.FusionOfSelfLoopPlaces;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.reductions.FusionOfSelfLoopTransitions;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.reductions.FusionOfSeriesPlaces;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.reductions.FusionOfSeriesTransitions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReducerTest {

    // 17d from Murata
    PetriNet createPetriNetWithoutMarks(List<? super Place> places, List<? super Transition> transitions) {
        Place p1 = Place.withMarks(0);
        Place p2 = Place.withMarks(0);
        Place p3 = Place.withMarks(1);
        Place p4 = Place.withMarks(0);

        Transition t1 = new TransitionImpl();
        Transition t2 = new TransitionImpl();
        Transition t3 = new TransitionImpl();
        Transition t4 = new TransitionImpl();

        t1.addInput(new FromPlaceToTransitionEdge(p2, t1));
        t1.addOutput(new FromTransitionToPlaceEdge(t1, p1));

        t2.addInput(new FromPlaceToTransitionEdge(p1, t2));
        t2.addOutput(new FromTransitionToPlaceEdge(t2, p2));

        t3.addInput(new FromPlaceToTransitionEdge(p2, t3));
        t3.addInput(new FromPlaceToTransitionEdge(p3, t3));
        t3.addOutput(new FromTransitionToPlaceEdge(t3, p4));

        t4.addInput(new FromPlaceToTransitionEdge(p4, t4));
        t4.addOutput(new FromTransitionToPlaceEdge(t4, p3));

        places.add(p1);
        places.add(p2);
        places.add(p3);
        places.add(p4);

        transitions.add(t1);
        transitions.add(t2);
        transitions.add(t3);
        transitions.add(t4);

        return new PetriNet(Arrays.asList(p1, p2, p3, p4), Arrays.asList(t1, t2, t3, t4));
    }

    @Test
    void simpleReduction() {
        for (int i = 0; i < 100; i++) {
            List<Place> places = new ArrayList<>();
            List<Transition> transitions = new ArrayList<>();

            PetriNet petriNet = createPetriNetWithoutMarks(places, transitions);

            Reducer reducer = new Reducer(petriNet);

            reducer.reduce(Arrays.asList(new FusionOfSeriesPlaces(), new FusionOfSelfLoopTransitions()),
                    Arrays.asList(new FusionOfSeriesTransitions(), new FusionOfSelfLoopPlaces()));

            assertTrue((1 == places.get(1).getOutputs().size()) || (places.get(0).getOutputs().size() == 1));
            assertTrue(places.get(1).getInputs().isEmpty());

            assertEquals(1, transitions.get(2).getInputs().size());
            assertTrue(transitions.get(2).getOutputs().isEmpty());

            System.out.println("=========");
        }
    }
}