package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FromTransitionToPlaceArcTest {

    @Test
    void putTokensTo() {
        Place place = new PlaceImpl(10, "p1");
        FromTransitionToPlaceArc edge = new FromTransitionToPlaceArc(new TransitionImpl("t1"), place);

        edge.putTokensTo(3);

        assertEquals(13, place.getMarks());
    }
}