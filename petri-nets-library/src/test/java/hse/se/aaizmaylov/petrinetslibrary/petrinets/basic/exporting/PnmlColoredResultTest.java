package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.exporting;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Place;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Transition;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.TransitionImpl;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static hse.se.aaizmaylov.petrinetslibrary.petrinets.TestUtil.getPath;
import static org.junit.jupiter.api.Assertions.*;

class PnmlColoredResultTest {

    private static Document getDocument(String path) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        return builder.parse(new File(path));
    }

    private void checkFileWithPlace(String filename) throws IOException, SAXException,
            ParserConfigurationException, TransformerException, XPathExpressionException {

        String path = getPath(filename, getClass());

        Path p = Paths.get(path);
        Path res = p.resolveSibling(p.getFileName() + "_kek");

        Place p1 = Place.withMarks(0, "p1");

        PnmlColoredResult result = new PnmlColoredResult(path);

        result.markPlaceUnbounded(p1).saveToFile(res.toString());

        Document document = getDocument(res.toString());

        XPath xpath = XPathFactory.newInstance().newXPath();

        String expression = "pnml/net/page/place/graphics/fill";

        NodeList list = (NodeList) xpath.compile(expression).evaluate(document, XPathConstants.NODESET);

        assertEquals(1, list.getLength());
        assertEquals(PnmlColoredResult.VertexColor.UNBOUND.getHexString(),
                list.item(0).getAttributes().getNamedItem("color").getNodeValue());

        if (!res.toFile().delete()) {
            fail("Cannot delete file!");
        }
    }

    @Test
    void simpleMarking() throws IOException, SAXException, ParserConfigurationException,
            TransformerException, XPathExpressionException {

        checkFileWithPlace("exporting/withAllTags.pnml");
    }

    @Test
    void withoutFill() throws IOException, SAXException, ParserConfigurationException,
            TransformerException, XPathExpressionException {

        checkFileWithPlace("exporting/withoutFill.pnml");
    }

    @Test
    void withoutGraphics() throws IOException, SAXException, ParserConfigurationException,
            TransformerException, XPathExpressionException {

        checkFileWithPlace("exporting/withoutGraphics.pnml");
    }

    @Test
    void unexpectedVertex() throws IOException, SAXException, ParserConfigurationException {
        String path = getPath("exporting/withoutGraphics.pnml", getClass());

        Place p1 = Place.withMarks(0, "p2");

        PnmlColoredResult result = new PnmlColoredResult(path);

        assertThrows(UnexpectedVertexException.class, () -> result.markPlaceUnbounded(p1));
    }

    @Test
    void withTransition() throws IOException, SAXException, ParserConfigurationException,
            TransformerException, XPathExpressionException {

        String path = getPath("exporting/withTransition.pnml", getClass());

        Path p = Paths.get(path);
        Path res = p.resolveSibling(p.getFileName() + "_kek");

        Transition t1 = new TransitionImpl("t1");

        PnmlColoredResult result = new PnmlColoredResult(path);

        result.markTransitionDead(t1).saveToFile(res.toString());

        Document document = getDocument(res.toString());

        XPath xpath = XPathFactory.newInstance().newXPath();

        String expression = "pnml/net/page/transition/graphics/fill";

        NodeList list = (NodeList) xpath.compile(expression).evaluate(document, XPathConstants.NODESET);

        assertEquals(1, list.getLength());
        assertEquals(PnmlColoredResult.VertexColor.DEAD.getHexString(),
                list.item(0).getAttributes().getNamedItem("color").getNodeValue());

        if (!res.toFile().delete()) {
            fail("Cannot delete file!");
        }
    }
}