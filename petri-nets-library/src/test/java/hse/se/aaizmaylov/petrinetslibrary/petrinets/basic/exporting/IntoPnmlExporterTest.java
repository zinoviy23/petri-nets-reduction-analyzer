package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.exporting;

import fr.lip6.move.pnml.framework.utils.exception.*;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.PetriNet;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.PetriNetReader;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Place;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.SomePetriNets;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Transition;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.importing.PnmlReader;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class IntoPnmlExporterTest {

    private static final Logger LOGGER = Logger.getLogger(IntoPnmlExporterTest.class);

    private static String readAllFromFileAndDelete(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new AssertionError(e);
        } finally {
            File file = new File(path);

            if (!file.delete()) {
                LOGGER.error("Cannot delete file " + path);
            }
        }
    }

    @Test
    void simpleTest() throws InvalidIDException, VoidRepositoryException, ValidationFailedException,
            IOException, BadFileFormatException, OCLValidationFailed, UnhandledNetType, OtherException,
            PetriNetReader.PetriNetReadingException {

        PetriNet<Place, Transition> pn = SomePetriNets.fromMurata16();

        IntoPnmlExporter exporter = new IntoPnmlExporter();

        String check = "file.pnml";

        exporter.export(pn, check);

        PnmlReader reader = new PnmlReader();

        PetriNet<Place, Transition> petriNet = reader.read(check);

        for (Place p : petriNet.getPlaces()) {
            assertTrue(pn.getPlacesMap().containsKey(p.label()));

            Place other = pn.getPlacesMap().get(p.label());

            assertEquals(other.getMarks(), p.getMarks());
            assertEquals(other.getInputs().size(), p.getInputs().size());
            assertEquals(other.getOutputs().size(), p.getOutputs().size());
        }

        for (Transition t : petriNet.getTransitions()) {
            assertTrue(pn.getTransitionsMap().containsKey(t.label()));

            Transition other = pn.getTransitionsMap().get(t.label());

            assertEquals(other.getInputs().size(), t.getInputs().size());
            assertEquals(other.getOutputs().size(), t.getOutputs().size());
        }

        LOGGER.debug(readAllFromFileAndDelete(check));
    }
}