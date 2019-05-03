package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FromPlaceToTransitionArcTest {
    @Test
    void getTokensFrom() {
        Place place = new PlaceImpl(10, "p1");

        FromPlaceToTransitionArc arc = new FromPlaceToTransitionArc(place, new TransitionImpl("t1"));

        arc.getTokensFrom(6L);

        assertEquals(4, place.getMarks());
    }

    @Test
    void defaultCtor() {
        FromPlaceToTransitionArc arc = new FromPlaceToTransitionArc(new PlaceImpl(0, "p"),
                new TransitionImpl("t"));

        assertEquals(Long.valueOf(1), arc.weight());
    }
}