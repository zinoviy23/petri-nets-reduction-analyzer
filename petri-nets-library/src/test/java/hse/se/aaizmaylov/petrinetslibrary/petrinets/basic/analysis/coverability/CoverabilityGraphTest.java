package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.analysis.coverability;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.PetriNet;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
}