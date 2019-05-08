package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.analysis.coverability;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.PetriNet;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CoverabilityGraphTest {

    private int vertexCount;
    private int edgesCount;

    @BeforeEach
    void setUp() {
        vertexCount = 0;
        edgesCount = 0;
    }

    @Test
    void simpleGraph() {
        Place p = Place.withMarks(1, "p");
        Transition t = new TransitionImpl("t");

        p.addOutput(new FromPlaceToTransitionArc(p, t));

        PetriNet<Place, Transition> petriNet = new PetriNet<>(Collections.singleton(p),
                Collections.singleton(t));

        CoverabilityGraph graph = new CoverabilityGraph(petriNet);

        Set<String> labels = new HashSet<>();

        graph.bfs((node) -> vertexCount++, tt -> labels.add(tt.label()));

        assertEquals(2, vertexCount);
        assertEquals(1, labels.size());
        assertTrue(labels.contains("t"));
    }

    @Test
    void unboundedFromMurata() {
        PetriNet<Place, Transition> petriNet = SomePetriNets.fromMurata16();

        CoverabilityGraph graph = new CoverabilityGraph(petriNet);

        graph.bfs(__ -> vertexCount++, t -> edgesCount++);

        assertEquals(4, vertexCount);
        assertEquals(5, edgesCount);
    }

    @Test
    void toStringTest() {
        Place p1 = Place.withMarks(0, "p1");
        Place p2 = Place.withMarks(0, "p2");

        Map<Place, Long> marking = new HashMap<>();
        marking.put(p1, -1L);
        marking.put(p2, 10L);

        Map<Place, Integer> indices = new HashMap<>();
        indices.put(p1, 0);
        indices.put(p2, 1);

        CoverabilityGraph.MarkingNode node = new CoverabilityGraph.MarkingNode(marking, indices, null);

        assertEquals("(\u221E,10)", node.toString());
    }

    @SuppressWarnings("EqualsWithItself")
    @Test
    void compareToCovering() {
        Place p1 = Place.withMarks(0, "p1");
        Place p2 = Place.withMarks(0, "p2");
        Place p3 = Place.withMarks(0, "p3");
        Place p4 = Place.withMarks(0, "p4");
        Place p5 = Place.withMarks(0, "p5");

        Map<Place, Integer> indices = new HashMap<>();
        indices.put(p1, 0);
        indices.put(p2, 1);
        indices.put(p3, 2);
        indices.put(p4, 3);
        indices.put(p5, 4);

        Map<Place, Long> marking1 = new HashMap<>();
        marking1.put(p1, -1L);
        marking1.put(p2, 10L);
        marking1.put(p3, 2L);
        marking1.put(p4, 3L);
        marking1.put(p5, -1L);

        Map<Place, Long> marking2 = new HashMap<>();
        marking2.put(p1, -1L);
        marking2.put(p2, 10L);
        marking2.put(p3, 3L);
        marking2.put(p4, 3L);
        marking2.put(p5, -1L);

        CoverabilityGraph.MarkingNode node1 = new CoverabilityGraph.MarkingNode(marking1, indices, null);
        CoverabilityGraph.MarkingNode node2 = new CoverabilityGraph.MarkingNode(marking2, indices, null);

        assertEquals(1, node1.compareTo(node1));
        assertEquals(-1, node1.compareTo(node2));
        assertEquals(1, node2.compareTo(node1));
    }
}