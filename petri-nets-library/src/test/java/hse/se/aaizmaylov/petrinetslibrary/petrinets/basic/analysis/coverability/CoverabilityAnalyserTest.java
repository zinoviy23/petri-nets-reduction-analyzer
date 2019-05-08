package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.analysis.coverability;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.PetriNet;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Place;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.SomePetriNets;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Transition;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CoverabilityAnalyserTest {
    @Test
    void simpleAnalysis() {
        PetriNet<Place, Transition> petriNet = SomePetriNets.fromMurata16();

        CoverabilityAnalyser analyser = new CoverabilityAnalyser(petriNet);

        assertEquals(1, analyser.getUnboundedPlaces().size());
        assertTrue(analyser.getUnboundedPlaces().contains(petriNet.getPlacesMap().get("p2")));

        assertEquals(1, analyser.getDeadTransitions().size());
        assertTrue(analyser.getDeadTransitions().contains(petriNet.getTransitionsMap().get("t0")));
    }

    @Test
    void anotherSimpleAnalysis() {
        PetriNet<Place, Transition> petriNet = SomePetriNets.fromMurata19a();

        CoverabilityAnalyser analyser = new CoverabilityAnalyser(petriNet);

        assertEquals(1, analyser.getUnboundedPlaces().size());
        assertTrue(analyser.getUnboundedPlaces().contains(petriNet.getPlacesMap().get("p3")));
        assertTrue(analyser.getDeadTransitions().isEmpty());
    }
}