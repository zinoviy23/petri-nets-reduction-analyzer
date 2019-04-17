package hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteVertexCallbackTest {

    @Test
    void inverseAdapter() {
        @SuppressWarnings("unchecked")
        DeleteVertexCallback<Integer, String> deleteVertexCallback = mock(DeleteVertexCallback.class);

        List<Integer> integerList = new ArrayList<>();
        List<String> stringList = new ArrayList<>();

        doAnswer(i -> {
            integerList.add(i.getArgument(0));
            return null;
        }).when(deleteVertexCallback).onDeleteTarget(anyInt());

        doAnswer(i -> {
            stringList.add(i.getArgument(0));
            return null;
        }).when(deleteVertexCallback).onDeleteNeighbour(anyString());

        DeleteVertexCallback<String, Integer> inverted = DeleteVertexCallback.invertedAdapter(deleteVertexCallback);
        inverted.onDeleteNeighbour(1);
        inverted.onDeleteTarget("kek");

        assertTrue(integerList.contains(1));
        assertTrue(stringList.contains("kek"));
    }

    @Test
    void sameEmptyAndNotNull() {
        assertNotNull(DeleteVertexCallback.empty());
        assertEquals(DeleteVertexCallback.empty(), DeleteVertexCallback.empty());
    }
}