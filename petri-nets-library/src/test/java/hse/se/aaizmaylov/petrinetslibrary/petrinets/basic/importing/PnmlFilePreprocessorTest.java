package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.importing;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.stream.Collectors;

import static hse.se.aaizmaylov.petrinetslibrary.petrinets.TestUtil.getPath;
import static org.junit.jupiter.api.Assertions.*;

class PnmlFilePreprocessorTest {
    private static String readAllFromFile(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    @Test
    void checkOk1() throws PreprocessException {
        assertFalse(readAllFromFile(new PnmlFilePreprocessor("c")
                .preprocess(getPath("processFiles/kek.xml", getClass()))).contains("<c>"));
    }

    @Test
    void checkOk2() throws PreprocessException {
        assertFalse(readAllFromFile(new PnmlFilePreprocessor("graphics, toolspecific")
                .preprocess(getPath("processFiles/tmp1.pnml", getClass())))
                .matches(".*(<graphic>|</graphic>|<toolspecific>|</toolspecific>).*"));
    }

    @Test
    void cannotReadBecauseOfBroken() {
        assertThrows(PreprocessException.class, () -> new PnmlFilePreprocessor("a")
                .preprocess(getPath("processFiles/broken1.xml", getClass())));
    }

    @Test
    void cannotReadNonexistentFile() {
        assertThrows(PreprocessException.class, () -> new PnmlFilePreprocessor("a")
                .preprocess("lol.kek"));
    }
}