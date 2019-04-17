package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FromTransitionToPlaceEdgeTest {

    @Test
    void putTokensTo() {
        Place place = new PlaceImpl(10, "p1");
        FromTransitionToPlaceEdge edge = new FromTransitionToPlaceEdge(new TransitionImpl("t1"), place);

        edge.putTokensTo(3);

        assertEquals(13, place.getMarks());
    }
}