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

    @Test
    void cloneTest() {
        FromPlaceToTransitionArc arc = new FromPlaceToTransitionArc(new PlaceImpl(0, "p"),
                new TransitionImpl("t"), 10).clone();

        assertEquals("p", arc.getFromEndpoint().label());
        assertEquals("t", arc.getToEndpoint().label());
        assertEquals(Long.valueOf(10), arc.weight());
    }

    @Test
    void weightEx() {
        assertThrows(IllegalArgumentException.class, () -> new FromPlaceToTransitionArc(
                new PlaceImpl(0, "p"), new TransitionImpl("t"), 0));
    }
}