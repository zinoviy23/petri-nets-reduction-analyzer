package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.analysis;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.PetriNet;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.ReductionHistory;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.*;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.reductions.*;
import hse.se.aaizmaylov.petrinetslibrary.utils.CollectionsUtils;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ReducerTest {

    private static final Logger LOGGER = Logger.getLogger(ReducerTest.class);

    // 17d from Murata
    PetriNet<Place, Transition> createPetriNetWithoutMarks() {
        Place p1 = Place.withMarks(0, "p1");
        Place p2 = Place.withMarks(0, "p2");
        Place p3 = Place.withMarks(1, "p3");
        Place p4 = Place.withMarks(0, "p4");

        Transition t1 = new TransitionImpl("t1");
        Transition t2 = new TransitionImpl("t2");
        Transition t3 = new TransitionImpl("t3");
        Transition t4 = new TransitionImpl("t4");

        t1.addInput(new FromPlaceToTransitionArc(p2, t1));
        t1.addOutput(new FromTransitionToPlaceArc(t1, p1));

        t2.addInput(new FromPlaceToTransitionArc(p1, t2));
        t2.addOutput(new FromTransitionToPlaceArc(t2, p2));

        t3.addInput(new FromPlaceToTransitionArc(p2, t3));
        t3.addInput(new FromPlaceToTransitionArc(p3, t3));
        t3.addOutput(new FromTransitionToPlaceArc(t3, p4));

        t4.addInput(new FromPlaceToTransitionArc(p4, t4));
        t4.addOutput(new FromTransitionToPlaceArc(t4, p3));

        return new PetriNet<>(Arrays.asList(p1, p2, p3, p4), Arrays.asList(t1, t2, t3, t4));
    }

    PetriNet<Place, Transition> cycleNetWithMark() {
        Place p1 = Place.withMarks(1, "p1");
        Place p2 = Place.withMarks(0, "p2");
        Place p3 = Place.withMarks(0, "p3");

        Transition t1 = new TransitionImpl("t1");
        Transition t2 = new TransitionImpl("t2");
        Transition t3 = new TransitionImpl("t3");

        p1.addOutput(new FromPlaceToTransitionArc(p1, t1));
        t1.addOutput(new FromTransitionToPlaceArc(t1, p2));
        p2.addOutput(new FromPlaceToTransitionArc(p2, t2));
        t2.addOutput(new FromTransitionToPlaceArc(t2, p3));
        p3.addOutput(new FromPlaceToTransitionArc(p3, t3));
        t3.addOutput(new FromTransitionToPlaceArc(t3, p1));

        return new PetriNet<>(Arrays.asList(p1, p2, p3), Arrays.asList(t1, t2, t3));
    }

    @SuppressWarnings("deprecation")
    @Test
    void simpleReduction() {
        for (int i = 0; i < 5; i++) {
            PetriNet<Place, Transition> petriNet = createPetriNetWithoutMarks();

            Reducer reducer = new Reducer(petriNet);

            ReductionHistory history =
                    reducer.reduce(Arrays.asList(new FusionOfSeriesPlaces(), new FusionOfSelfLoopTransitions()),
                    Arrays.asList(new FusionOfSeriesTransitions(), new FusionOfSelfLoopPlaces()));

            assertEquals(1, petriNet.getPlaces().size());
            assertEquals(1, petriNet.getTransitions().size());

            Place place = CollectionsUtils.first(petriNet.getPlaces());
            Transition transition = CollectionsUtils.first(petriNet.getTransitions());

            assertEquals(1, place.getOutputs().size());
            assertTrue(place.getInputs().isEmpty());

            assertTrue(history.contains(place));
            assertTrue(history.contains(transition));

            assertEquals(1, transition.getInputs().size());
            assertTrue(transition.getOutputs().isEmpty());

            LOGGER.debug(history.getAssociated(place));
            LOGGER.debug(history.getAssociated(transition));

            System.out.println("=========");
        }
    }

    @SuppressWarnings("deprecation")
    @Test
    void checkCycle() {
        PetriNet<Place, Transition> petriNet = cycleNetWithMark();

        Reducer reducer = new Reducer(petriNet);

        ReductionHistory history =
                reducer.reduce(Arrays.asList(new FusionOfSeriesPlaces(), new FusionOfSelfLoopTransitions()),
                Arrays.asList(new FusionOfSeriesTransitions(), new FusionOfSelfLoopPlaces()));

        assertTrue(petriNet.getPlaces().isEmpty() ^ petriNet.getTransitions().isEmpty());
        if (petriNet.getPlaces().isEmpty()) {
            Transition transition = CollectionsUtils.first(petriNet.getTransitions());
            assertTrue(history.contains(transition));
            assertTrue(transition.getInputs().isEmpty());
            assertTrue(transition.getOutputs().isEmpty());

            LOGGER.debug(history.getAssociated(transition));
        } else {
            Place place = CollectionsUtils.first(petriNet.getPlaces());
            assertTrue(history.contains(place));
            assertTrue(place.getInputs().isEmpty());
            assertTrue(place.getOutputs().isEmpty());
            assertEquals(1, place.getMarks());

            LOGGER.debug(history.getAssociated(place));
        }
    }

    @Test
    void initializedReduction() {
        PetriNet<Place, Transition> petriNet = SomePetriNets.fromDiazWithRedundantPlace();

        Reducer reducer = new Reducer(petriNet);
        Place p = petriNet.getPlacesMap().get("p");

        ReductionHistory history =  reducer.reduce(Collections.singletonList(new DeleteRedundantPlace()),
                Collections.emptyList());

        assertFalse(petriNet.getPlacesMap().containsKey("p"));
        assertEquals(2, petriNet.getPlaces().size());

        assertFalse(history.contains(p));
    }
}