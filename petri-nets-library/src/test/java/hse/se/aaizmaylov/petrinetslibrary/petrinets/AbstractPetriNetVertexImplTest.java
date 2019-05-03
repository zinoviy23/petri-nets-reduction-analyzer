package hse.se.aaizmaylov.petrinetslibrary.petrinets;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("unchecked")
class AbstractPetriNetVertexImplTest {

    @Test
    void getInputsUnmodifiable() {
        AbstractPetriNetVertexImpl vertex = new AbstractPetriNetVertexImpl("v") {};

        assertTrue(vertex.getInputs().isEmpty());

        assertThrows(UnsupportedOperationException.class, () -> vertex.getInputs().add(null));
    }

    @Test
    void getOutputsUnmodifiable() {
        AbstractPetriNetVertexImpl vertex = new AbstractPetriNetVertexImpl("v") {};

        assertTrue(vertex.getOutputs().isEmpty());

        assertThrows(UnsupportedOperationException.class, () -> vertex.getOutputs().add(null));
    }

    @Test
    void addOutput() {
        AbstractPetriNetVertexImpl vertex1 = new AbstractPetriNetVertexImpl("v") {};
        AbstractPetriNetVertexImpl vertex2 = new AbstractPetriNetVertexImpl("v") {};

        vertex1.addOutput(genEdge(vertex1, vertex2));

        assertEquals(1, vertex1.getOutputs().size());
        assertEquals(1, vertex2.getInputs().size());
    }

    @Test
    void removeOutput() {
        AbstractPetriNetVertexImpl vertex1 = new AbstractPetriNetVertexImpl("v") {};
        AbstractPetriNetVertexImpl vertex2 = new AbstractPetriNetVertexImpl("v") {};

        Arc arc = genEdge(vertex1, vertex2);

        vertex1.addOutput(arc);

        assertEquals(1, vertex1.getOutputs().size());
        assertEquals(1, vertex2.getInputs().size());

        vertex1.removeOutput(arc);

        assertTrue(vertex1.getOutputs().isEmpty());
        assertTrue(vertex2.getInputs().isEmpty());
    }

    @Test
    void removeInput() {
        AbstractPetriNetVertexImpl vertex1 = new AbstractPetriNetVertexImpl("v") {};
        AbstractPetriNetVertexImpl vertex2 = new AbstractPetriNetVertexImpl("v") {};

        Arc arc = genEdge(vertex2, vertex1);

        vertex1.addInput(arc);

        assertEquals(1, vertex1.getInputs().size());
        assertEquals(1, vertex2.getOutputs().size());

        vertex1.removeInput(arc);

        assertTrue(vertex1.getInputs().isEmpty());
        assertTrue(vertex2.getOutputs().isEmpty());
    }

    @Test
    void emptyRemove() {
        AbstractPetriNetVertexImpl vertex = new AbstractPetriNetVertexImpl("v") {};
        Arc arc = genEdge(vertex, vertex);

        assertFalse(vertex.removeInput(arc));
        assertFalse(vertex.removeOutput(arc));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void nullChecking() {
        AbstractPetriNetVertexImpl vertex = new AbstractPetriNetVertexImpl("v") {};

        assertThrows(NullPointerException.class, () -> vertex.addInput(null));
        assertThrows(NullPointerException.class, () -> vertex.addOutput(null));
        assertThrows(NullPointerException.class, () -> vertex.removeInput(null));
        assertThrows(NullPointerException.class, () -> vertex.removeOutput(null));
    }

    @Test
    void addInput() {
        AbstractPetriNetVertexImpl vertex1 = new AbstractPetriNetVertexImpl("v") {};
        AbstractPetriNetVertexImpl vertex2 = new AbstractPetriNetVertexImpl("v") {};

        vertex1.addInput(genEdge(vertex2, vertex1));

        assertEquals(1, vertex1.getInputs().size());
        assertEquals(1, vertex2.getOutputs().size());
    }

    @Test
    void foreignArcs() {
        AbstractPetriNetVertexImpl vertex1 = new AbstractPetriNetVertexImpl("v1") {};
        AbstractPetriNetVertexImpl vertex2 = new AbstractPetriNetVertexImpl("v2") {};
        AbstractPetriNetVertexImpl vertex3 = new AbstractPetriNetVertexImpl("v3") {};

        final Arc arc = genEdge(vertex2, vertex3);

        assertThrows(ForeignArcException.class, () -> vertex1.addInput(arc));
        assertThrows(ForeignArcException.class, () -> vertex1.removeInput(arc));
        assertThrows(ForeignArcException.class, () -> vertex1.addOutput(arc));
        assertThrows(ForeignArcException.class, () -> vertex1.removeOutput(arc));
    }

    private Arc genEdge(Object vertex1, Object vertex2) {
        return new Arc() {
            @NotNull
            @Override
            public Object getFromEndpoint() {
                return vertex1;
            }

            @NotNull
            @Override
            public Object getToEndpoint() {
                return vertex2;
            }

            @NotNull
            @Override
            public Object weight() {
                return 1;
            }

            @Override
            public void setFromEndpoint(Object o) {

            }

            @Override
            public void setToEndpoint(Object o) {

            }

            @Override
            public void getTokensFrom(Object tokens) { }

            @Override
            public void putTokensTo(Object tokens) { }
        };
    }

    @Test
    void label() {
        AbstractPetriNetVertexImpl vertex = new AbstractPetriNetVertexImpl("kekkekkek") {};
        assertEquals("kekkekkek", vertex.label());
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void labelNonNull() {
        assertThrows(NullPointerException.class, () -> new AbstractPetriNetVertexImpl(null) {});
    }
}