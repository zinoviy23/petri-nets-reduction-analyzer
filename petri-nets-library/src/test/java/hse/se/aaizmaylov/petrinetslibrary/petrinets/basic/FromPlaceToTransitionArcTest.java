package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FromPlaceToTransitionArcTest {
    @Test
    void getTokensFrom() {
        Place place = new PlaceImpl(10, "p1");

        FromPlaceToTransitionArc edge = new FromPlaceToTransitionArc(place, new TransitionImpl("t1"));

        edge.getTokensFrom(6);

        assertEquals(4, place.getMarks());
    }
}