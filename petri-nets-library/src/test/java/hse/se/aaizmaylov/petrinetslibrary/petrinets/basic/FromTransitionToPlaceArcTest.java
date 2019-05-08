package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FromTransitionToPlaceArcTest {

    @Test
    void putTokensTo() {
        Place place = new PlaceImpl(10, "p1");
        FromTransitionToPlaceArc arc = new FromTransitionToPlaceArc(new TransitionImpl("t1"), place);

        arc.putTokensTo(3L);

        assertEquals(13, place.getMarks());
    }

    @Test
    void defaultCtor() {
        FromTransitionToPlaceArc arc = new FromTransitionToPlaceArc(new TransitionImpl("t"),
                new PlaceImpl(0,"p"));

        assertEquals(Long.valueOf(1), arc.weight());
    }

    @Test
    void cloneTest() {
        FromTransitionToPlaceArc arc = new FromTransitionToPlaceArc(new TransitionImpl("t"),
                new PlaceImpl(0, "p"), 10).clone();

        assertEquals("p", arc.getToEndpoint().label());
        assertEquals("t", arc.getFromEndpoint().label());
        assertEquals(Long.valueOf(10), arc.weight());
    }

    @Test
    void weightEx() {
        assertThrows(IllegalArgumentException.class, () -> new FromTransitionToPlaceArc(
                new TransitionImpl("t"), new PlaceImpl(0, "p"), 0));
    }
}