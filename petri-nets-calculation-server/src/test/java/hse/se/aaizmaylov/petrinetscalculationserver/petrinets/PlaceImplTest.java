package hse.se.aaizmaylov.petrinetscalculationserver.petrinets;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlaceImplTest {

    @Test
    public void addMarks() {
        Place place = new PlaceImpl(0);

        place.addMarks(10);

        assertEquals(10, place.getMarks());
    }

    @Test
    public void removeMarks() {
        Place place = new PlaceImpl(0);

        place.setMarks(10);

        place.removeMarks(2);

        assertEquals(8, place.getMarks());
    }

    @Test
    public void getMarks() {
        Place place = new PlaceImpl(0);

        assertEquals(0, place.getMarks());
    }

    @Test
    public void setMarks() {
        Place place = new PlaceImpl(0);

        place.setMarks(13);

        assertEquals(13, place.getMarks());
    }


    @Test(expected = IllegalArgumentException.class)
    public void illegalMarksCount() {
        new PlaceImpl(-1);
    }
}