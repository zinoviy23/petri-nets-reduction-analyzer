package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlaceImplTest {

    @Test
    void ctor() {
        Place place = new PlaceImpl(12, "p");

        assertEquals(12, place.getMarks());
    }

    @Test
    void setMarks() {
        Place place = new PlaceImpl(10, "p");

        place.setMarks(11);

        assertEquals(11, place.getMarks());
    }

    @Test
    void addMarks() {
        Place place = new PlaceImpl(3, "p");

        place.addMarks(12);

        assertEquals(15, place.getMarks());
    }

    @Test
    void removeMarks() {
        Place place = new PlaceImpl(10, "p");

        place.removeMarks(7);

        assertEquals(3, place.getMarks());
    }

    @Test
    void removeMarksToZero() {
        Place place = new PlaceImpl(4, "p");

        place.removeMarks(4);

        assertEquals(0, place.getMarks());
    }

    @Test
    void checkForNegativeThrowing() {
        Place place = new PlaceImpl(5, "p");

        assertThrows(IllegalArgumentException.class, () -> place.removeMarks(6));
    }
}