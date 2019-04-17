package hse.se.aaizmaylov.petrinetscalculationserver;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Place;
import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleTest {
    @Test
    public void test() {
        Place place = Place.withMarks(10, "label");
        assertEquals(10, place.getMarks());
    }
}
