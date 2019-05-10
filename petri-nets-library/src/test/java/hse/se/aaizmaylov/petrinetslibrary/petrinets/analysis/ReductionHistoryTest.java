package hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Place;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ReductionHistoryTest {
    @Test
    void mergeToNew() {
        Place p1 = Place.withMarks(1, "p1");
        Place p2 = Place.withMarks(1, "p2");
        Place p3 = Place.withMarks(1, "p3");

        ReductionHistory history = new ReductionHistory(Arrays.asList(p1, p2, p3));

        Place p4 = Place.withMarks(1, "p4");

        history.merge(p4, p1, p2);

        assertTrue(history.contains(p4));

        assertFalse(history.contains(p1));
        assertFalse(history.contains(p2));

        assertEquals(2, history.get(p4).size());

        assertTrue(history.get(p4).contains("p1"));
        assertTrue(history.get(p4).contains("p2"));
    }

    @Test
    void mergeToOld() {
        Place p1 = Place.withMarks(1, "p1");
        Place p2 = Place.withMarks(1, "p2");
        Place p3 = Place.withMarks(1, "p3");

        ReductionHistory history = new ReductionHistory(Arrays.asList(p1, p2, p3));

        history.merge(p3, p1, p2);

        assertTrue(history.contains(p3));

        assertFalse(history.contains(p1));
        assertFalse(history.contains(p2));

        assertEquals(3, history.get(p3).size());

        assertTrue(history.get(p3).contains("p1"));
        assertTrue(history.get(p3).contains("p2"));
        assertTrue(history.get(p3).contains("p3"));
    }

    @Test
    void delete() {
        Place p1 = Place.withMarks(1, "p1");
        Place p2 = Place.withMarks(1, "p2");
        Place p3 = Place.withMarks(1, "p3");

        ReductionHistory history = new ReductionHistory(Arrays.asList(p1, p2, p3));

        history.delete(p2);

        assertTrue(history.contains(p1));
        assertFalse(history.contains(p2));
        assertTrue(history.contains(p3));
    }
}