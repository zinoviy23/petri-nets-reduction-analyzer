package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FromPlaceToTransitionEdgeTest {
    @Test
    void getTokensFrom() {
        Place place = new PlaceImpl(10, "p1");

        FromPlaceToTransitionEdge edge = new FromPlaceToTransitionEdge(place, new TransitionImpl("t1"));

        edge.getTokensFrom(6);

        assertEquals(4, place.getMarks());
    }
}