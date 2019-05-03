package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.importing;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.PetriNet;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.PetriNetReader;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Place;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Transition;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

import static hse.se.aaizmaylov.petrinetslibrary.petrinets.TestUtil.getPath;
import static hse.se.aaizmaylov.petrinetslibrary.utils.CollectionsUtils.first;
import static org.junit.jupiter.api.Assertions.*;

class PnmlReaderTest {
//    private static final Logger LOGGER = Logger.getLogger(PnmlReaderTest.class);

    @Test
    void readSuccessPetriNetWithOnePlace() throws PetriNetReader.PetriNetReadingException {
        PetriNetReader<Place, Transition> reader = new PnmlReader();
        PetriNet<Place, Transition> petriNet = reader.read(getPath("readingFromPnml/onlyWith1Place.pnml",
                getClass()));

        assertEquals(1, petriNet.getPlaces().size());
        assertTrue(petriNet.getTransitions().isEmpty());

        assertTrue(petriNet.getPlacesMap().containsKey("p1"));
        assertEquals(10, petriNet.getPlacesMap().get("p1").getMarks());
    }

    @Test
    void readSuccessSimplePetriNet() throws PetriNetReader.PetriNetReadingException {
        PetriNetReader<Place, Transition> reader = new PnmlReader();
        PetriNet<Place, Transition> petriNet = reader.read(getPath("readingFromPnml/simpleNet.pnml", getClass()));

        assertEquals(1, petriNet.getPlaces().size());
        assertEquals(1, petriNet.getTransitions().size());

        assertTrue(petriNet.getPlacesMap().containsKey("p1"));
        assertTrue(petriNet.getTransitionsMap().containsKey("t1"));

        assertEquals(1, petriNet.getPlacesMap().get("p1").getInputs().size());
        assertEquals(1, petriNet.getPlacesMap().get("p1").getOutputs().size());
        assertEquals(1, petriNet.getTransitionsMap().get("t1").getInputs().size());
        assertEquals(1, petriNet.getTransitionsMap().get("t1").getOutputs().size());

        Place p1 = petriNet.getPlacesMap().get("p1");
        Transition t1 = petriNet.getTransitionsMap().get("t1");

        assertEquals(p1, first(t1.getInputs()).getFromEndpoint());
        assertEquals(p1, first(t1.getOutputs()).getToEndpoint());

        assertEquals(t1, first(p1.getInputs()).getFromEndpoint());
        assertEquals(t1, first(p1.getOutputs()).getToEndpoint());

        assertEquals(Long.valueOf(1), first(p1.getInputs()).weight());
        assertEquals(Long.valueOf(1), first(p1.getOutputs()).weight());
    }

    @Test
    void wrongEdge() {
        PetriNetReader<Place, Transition> reader = new PnmlReader();

        PetriNetReader.PetriNetReadingException ex = assertThrows(PetriNetReader.PetriNetReadingException.class,
                () -> reader.read(getPath("readingFromPnml/wrongEdge.pnml", getClass())));

        assertTrue(ex.getCause() instanceof PnmlReader.WrongEdgeTypeException);
    }

    @Test
    void twoPages() throws PetriNetReader.PetriNetReadingException {
        PetriNetReader<Place, Transition> reader = new PnmlReader();
        PetriNet<Place, Transition> petriNet = reader.read(getPath("readingFromPnml/twoPages.pnml", getClass()));

        assertEquals(1, petriNet.getPlaces().size());
        assertEquals(1, petriNet.getTransitions().size());

        assertTrue(petriNet.getPlacesMap().containsKey("p1"));
        assertTrue(petriNet.getTransitionsMap().containsKey("t1"));

        assertEquals(1, petriNet.getPlacesMap().get("p1").getOutputs().size());
        assertEquals(1, petriNet.getTransitionsMap().get("t1").getInputs().size());

        Place p1 = petriNet.getPlacesMap().get("p1");
        Transition t1 = petriNet.getTransitionsMap().get("t1");

        assertEquals(p1, first(t1.getInputs()).getFromEndpoint());

        assertEquals(t1, first(p1.getOutputs()).getToEndpoint());
    }

    @Test
    void edgeAnnotation() throws PetriNetReader.PetriNetReadingException {
        PetriNetReader<Place, Transition> reader = new PnmlReader();
        PetriNet<Place, Transition> petriNet = reader.read(getPath("readingFromPnml/withEdgeAnnotation.pnml",
                getClass()));

        assertEquals(1, petriNet.getPlaces().size());
        assertEquals(1, petriNet.getTransitions().size());

        Place p = first(petriNet.getPlaces());

        assertEquals(Long.valueOf(10), first(p.getOutputs()).weight());
    }
}