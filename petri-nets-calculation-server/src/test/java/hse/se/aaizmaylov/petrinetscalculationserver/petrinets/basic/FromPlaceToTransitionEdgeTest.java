package hse.se.aaizmaylov.petrinetscalculationserver.petrinets.basic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FromPlaceToTransitionEdgeTest {
    @Test
    void getTokensFrom() {
        Place place = new PlaceImpl(10);
        FromPlaceToTransitionEdge edge = new FromPlaceToTransitionEdge(place, new TransitionImpl());

        edge.getTokensFrom(6);

        assertEquals(4, place.getMarks());
    }
}