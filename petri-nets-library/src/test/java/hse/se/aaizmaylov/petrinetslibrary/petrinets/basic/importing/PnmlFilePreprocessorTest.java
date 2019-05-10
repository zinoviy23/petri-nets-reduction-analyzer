package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.importing;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.stream.Collectors;

import static hse.se.aaizmaylov.petrinetslibrary.petrinets.TestUtil.getPath;
import static org.junit.jupiter.api.Assertions.*;

class PnmlFilePreprocessorTest {
    private static final Logger LOGGER = Logger.getLogger(PnmlFilePreprocessorTest.class);

    private static String readAllFromFileAndDelete(String path, boolean delete) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new AssertionError(e);
        } finally {
            if (delete) {
                File file = new File(path);

                if (!file.delete()) {
                    LOGGER.error("Cannot delete file " + path);
                }
            }
        }
    }

    @Test
    void checkOk1() throws PreprocessException {
        assertFalse(readAllFromFileAndDelete(new PnmlFilePreprocessor("c")
                .preprocess(getPath("processFiles/kek.xml", getClass())), true).contains("<c>"));
    }

    @Test
    void checkOk2() throws PreprocessException {
        assertFalse(readAllFromFileAndDelete(new PnmlFilePreprocessor("graphics", "toolspecific")
                .preprocess(getPath("processFiles/tmp1.pnml", getClass())), true)
                .matches(".*(<graphics>|</graphics>|<toolspecific>|</toolspecific>).*"));
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

    @Test
    void doesntModifyFile() throws PreprocessException {
        String filename = getPath("processFiles/tmp1.pnml", getClass());

        String content = readAllFromFileAndDelete(filename, false);

        String processed = new PnmlFilePreprocessor("graphics", "toolspecific").preprocess(filename);

        if (!new File(processed).delete()) {
            fail("Cannot delete file");
        }

        String updatedContent = readAllFromFileAndDelete(filename, false);

        assertEquals(content, updatedContent);
    }
}