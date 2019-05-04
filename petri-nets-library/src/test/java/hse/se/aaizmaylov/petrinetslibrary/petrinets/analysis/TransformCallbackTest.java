package hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransformCallbackTest {

    @Test
    void inverseAdapter() {
        @SuppressWarnings("unchecked")
        TransformCallback<Integer, String> transformCallback = mock(TransformCallback.class);

        List<Integer> integerList = new ArrayList<>();
        List<String> stringList = new ArrayList<>();

        doAnswer(i -> {
            integerList.add(i.getArgument(0));
            return null;
        }).when(transformCallback).onDeleteTarget(anyInt());

        doAnswer(i -> {
            stringList.add(i.getArgument(0));
            return null;
        }).when(transformCallback).onDeleteNeighbour(anyString());

        TransformCallback<String, Integer> inverted = TransformCallback.invertedAdapter(transformCallback);
        inverted.onDeleteNeighbour(1);
        inverted.onDeleteTarget("kek");

        assertTrue(integerList.contains(1));
        assertTrue(stringList.contains("kek"));
    }

    @Test
    void sameEmptyAndNotNull() {
        assertNotNull(TransformCallback.empty());
        assertEquals(TransformCallback.empty(), TransformCallback.empty());
    }
}