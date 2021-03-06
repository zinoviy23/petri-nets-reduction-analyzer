package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.importing;

import org.jetbrains.annotations.NotNull;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import static hse.se.aaizmaylov.petrinetslibrary.utils.XmlUtils.attributesToList;

public class PnmlFilePreprocessor {

    private final Set<String> ignoredTags;

    public PnmlFilePreprocessor(String... tags) {
        ignoredTags = new HashSet<>(Arrays.asList(tags));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    String preprocess(String path) throws PreprocessException {
        Path filePath = Paths.get(path);
        String newPath = findAppropriateFileName(
                filePath.resolveSibling(filePath.getFileName().toString() + "_pp").toString());

        try (InputStreamReader reader =
                new InputStreamReader(new FileInputStream(path), Charset.forName("UTF-8"));
             PrintWriter writer = new PrintWriter(new FileWriter(newPath), true)) {

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();

            InputSource source = new InputSource();
            source.setCharacterStream(reader);
            parser.parse(source, new RemoveNodesHandler(writer, ignoredTags));

        } catch (IOException | ParserConfigurationException | SAXException e) {
            File file = new File(newPath);
            file.delete();
            throw new PreprocessException(e);
        }

//        try (FileChannel inputChannel = new FileInputStream(newPath).getChannel();
//             FileChannel outputChannel = new FileOutputStream(path).getChannel()) {
//
//            inputChannel.transferTo(0, inputChannel.size(), outputChannel);
//        } catch (IOException e) {
//            throw new PreprocessException(e);
//        }

        return newPath;
    }

    @NotNull
    private String findAppropriateFileName(@NotNull String filename) throws PreprocessException {
        if (!Files.exists(Paths.get(filename)))
            return filename;

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if (!Files.exists(Paths.get(filename + i)))
                return filename;
        }

        throw new PreprocessException("Cannot create temporary file");
    }

    private static class RemoveNodesHandler extends DefaultHandler {
        private LinkedList<String> stackOfIgnoringTags = new LinkedList<>();
        private PrintWriter writer;
        private Set<String> tags;

        private RemoveNodesHandler(PrintWriter writer, Set<String> tags) {
            this.writer = writer;
            this.tags = new HashSet<>(tags);
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            if (tags.contains(qName)) {
                stackOfIgnoringTags.push(qName);
            }

            if (!stackOfIgnoringTags.isEmpty()){
                return;
            }

            writer.printf("<%s %s>", qName, attributesToList(attributes));
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            if (stackOfIgnoringTags.isEmpty()) {
                writer.printf("</%s>", qName);
                return;
            }

            if (stackOfIgnoringTags.peek().equals(qName)) {
                stackOfIgnoringTags.pop();
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            if (stackOfIgnoringTags.isEmpty()) {
                writer.print(new String(ch, start, length));
            }
        }
    }
}
