package hse.se.aaizmaylov.petrinetscalculationserver.petrinets;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("unchecked")
class AbstractPetriNetVertexImplTest {

    @Test
    void getInputsUnmodifiable() {
        AbstractPetriNetVertexImpl vertex = new AbstractPetriNetVertexImpl() {};

        assertTrue(vertex.getInputs().isEmpty());

        assertThrows(UnsupportedOperationException.class, () -> vertex.getInputs().add(null));
    }

    @Test
    void getOutputsUnmodifiable() {
        AbstractPetriNetVertexImpl vertex = new AbstractPetriNetVertexImpl() {};

        assertTrue(vertex.getOutputs().isEmpty());

        assertThrows(UnsupportedOperationException.class, () -> vertex.getOutputs().add(null));
    }

    @Test
    void addOutput() {
        AbstractPetriNetVertexImpl vertex1 = new AbstractPetriNetVertexImpl() {};
        AbstractPetriNetVertexImpl vertex2 = new AbstractPetriNetVertexImpl() {};

        vertex1.addOutput(genEdge(vertex1, vertex2));

        assertEquals(1, vertex1.getOutputs().size());
        assertEquals(1, vertex2.getInputs().size());
    }

    @Test
    void removeOutput() {
        AbstractPetriNetVertexImpl vertex1 = new AbstractPetriNetVertexImpl() {};
        AbstractPetriNetVertexImpl vertex2 = new AbstractPetriNetVertexImpl() {};

        Edge edge = genEdge(vertex1, vertex2);

        vertex1.addOutput(edge);

        assertEquals(1, vertex1.getOutputs().size());
        assertEquals(1, vertex2.getInputs().size());

        vertex1.removeOutput(edge);

        assertTrue(vertex1.getOutputs().isEmpty());
        assertTrue(vertex2.getInputs().isEmpty());
    }

    @Test
    void removeInput() {
        AbstractPetriNetVertexImpl vertex1 = new AbstractPetriNetVertexImpl() {};
        AbstractPetriNetVertexImpl vertex2 = new AbstractPetriNetVertexImpl() {};

        Edge edge = genEdge(vertex2, vertex1);

        vertex1.addInput(edge);

        assertEquals(1, vertex1.getInputs().size());
        assertEquals(1, vertex2.getOutputs().size());

        vertex1.removeInput(edge);

        assertTrue(vertex1.getInputs().isEmpty());
        assertTrue(vertex2.getOutputs().isEmpty());
    }

    @Test
    void emptyRemove() {
        AbstractPetriNetVertexImpl vertex = new AbstractPetriNetVertexImpl() {};
        Edge edge = genEdge(vertex, vertex);

        assertFalse(vertex.removeInput(edge));
        assertFalse(vertex.removeOutput(edge));
    }

    @Test
    void nullChecking() {
        AbstractPetriNetVertexImpl vertex = new AbstractPetriNetVertexImpl() {};

        assertThrows(NullPointerException.class, () -> vertex.addInput(null));
        assertThrows(NullPointerException.class, () -> vertex.addOutput(null));
        assertThrows(NullPointerException.class, () -> vertex.removeInput(null));
        assertThrows(NullPointerException.class, () -> vertex.removeOutput(null));
    }

    @Test
    void addInput() {
        AbstractPetriNetVertexImpl vertex1 = new AbstractPetriNetVertexImpl() {};
        AbstractPetriNetVertexImpl vertex2 = new AbstractPetriNetVertexImpl() {};

        vertex1.addInput(genEdge(vertex2, vertex1));

        assertEquals(1, vertex1.getInputs().size());
        assertEquals(1, vertex2.getOutputs().size());
    }

    private Edge genEdge(Object vertex1, Object vertex2) {
        return new Edge() {
            @Override
            public Object getFrom() {
                return vertex1;
            }

            @Override
            public Object getTo() {
                return vertex2;
            }

            @Override
            public void setFrom(Object o) {

            }

            @Override
            public void setTo(Object o) {

            }

            @Override
            public void getTokensFrom(Object tokens) { }

            @Override
            public void putTokensTo(Object tokens) { }
        };
    }
}