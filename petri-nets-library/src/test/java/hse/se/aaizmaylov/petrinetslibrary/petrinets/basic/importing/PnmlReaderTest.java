package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.importing;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.PetriNet;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.PetriNetReader;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Place;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Transition;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

import static hse.se.aaizmaylov.petrinetslibrary.petrinets.TestUtil.getPath;
import static org.junit.jupiter.api.Assertions.*;

class PnmlReaderTest {
    private static final Logger LOGGER = Logger.getLogger(PnmlReaderTest.class);

    @Test
    void readSuccessSimplePetriNet() throws PetriNetReader.PetriNetReadingException {
        PetriNetReader<Place, Transition> reader = new PnmlReader();
        PetriNet<Place, Transition> petriNet = reader.read(getPath("readingFromPnml/onlyWith1Place.pnml",
                getClass()));

        assertEquals(1, petriNet.getPlaces().size());
        assertTrue(petriNet.getTransitions().isEmpty());

        assertTrue(petriNet.getPlacesMap().containsKey("p1"));
        assertEquals(10, petriNet.getPlacesMap().get("p1").getMarks());
    }
}