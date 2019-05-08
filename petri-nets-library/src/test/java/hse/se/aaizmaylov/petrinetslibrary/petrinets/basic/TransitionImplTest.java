package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TransitionImplTest {

    @Test
    void fireEnabled() {
        Transition transition = new TransitionImpl("t");
        Place place1 = new PlaceImpl(1, "p1");
        Place place2 = new PlaceImpl(2, "p2");
        Place place3 = new PlaceImpl(3, "p3");

        transition.addInput(new FromPlaceToTransitionArc(place1, transition));
        transition.addInput(new FromPlaceToTransitionArc(place2, transition));

        transition.addOutput(new FromTransitionToPlaceArc(transition, place3));

        transition.fire();

        assertEquals(0, place1.getMarks());
        assertEquals(1, place2.getMarks());
        assertEquals(4, place3.getMarks());
    }

    @Test
    void fireDisabled() {
        Transition transition = new TransitionImpl("t");
        Place place1 = new PlaceImpl(0, "p1");
        Place place2 = new PlaceImpl(2, "p2");
        Place place3 = new PlaceImpl(3, "p3");

        transition.addInput(new FromPlaceToTransitionArc(place1, transition));
        transition.addInput(new FromPlaceToTransitionArc(place2, transition));

        transition.addOutput(new FromTransitionToPlaceArc(transition, place3));

        transition.fire();

        assertEquals(0, place1.getMarks());
        assertEquals(2, place2.getMarks());
        assertEquals(3, place3.getMarks());
    }

    @Test
    void enabledTrue() {
        Transition transition = new TransitionImpl("t");
        Place place1 = new PlaceImpl(1, "p1");
        Place place2 = new PlaceImpl(2, "p2");
        Place place3 = new PlaceImpl(3, "p3");

        transition.addInput(new FromPlaceToTransitionArc(place1, transition));
        transition.addInput(new FromPlaceToTransitionArc(place2, transition));

        transition.addOutput(new FromTransitionToPlaceArc(transition, place3));

        assertTrue(transition.enabled());
    }

    @Test
    void enableFalse() {
        Transition transition = new TransitionImpl("t");
        Place place1 = new PlaceImpl(0, "p1");
        Place place2 = new PlaceImpl(2, "p2");
        Place place3 = new PlaceImpl(3, "p3");

        transition.addInput(new FromPlaceToTransitionArc(place1, transition));
        transition.addInput(new FromPlaceToTransitionArc(place2, transition));

        transition.addOutput(new FromTransitionToPlaceArc(transition, place3));

        assertFalse(transition.enabled());
    }

    @Test
    void enabledEmpty() {
        Transition transition = new TransitionImpl("t");

        assertTrue(transition.enabled());
    }

    @Test
    void enabledWithWeights() {
        Transition t = new TransitionImpl("t");
        Place p1 = new PlaceImpl(2, "p1");
        Place p2 = new PlaceImpl(0, "p2");
        Place p3 = new PlaceImpl(3, "p3");

        t.addInput(new FromPlaceToTransitionArc(p1, t, 2));
        t.addInput(new FromPlaceToTransitionArc(p3, t, 2));
        t.addOutput(new FromTransitionToPlaceArc(t, p2, 3));

        assertTrue(t.enabled());

        t.fire();

        assertEquals(0, p1.getMarks());
        assertEquals(1, p3.getMarks());
        assertEquals(3, p2.getMarks());
    }

    @Test
    void infinityTest() {
        Place p = new PlaceImpl(1, "p");
        Transition t = new TransitionImpl("t");
        Place p1 = new PlaceImpl(2, "p1");
        Place p2 = new PlaceImpl(0, "p2");
        Place p3 = new PlaceImpl(1, "p3");

        t.addInput(new FromPlaceToTransitionArc(p, t, 100));
        t.addInput(new FromPlaceToTransitionArc(p1, t));

        t.addOutput(new FromTransitionToPlaceArc(t, p2, 10));
        t.addOutput(new FromTransitionToPlaceArc(t, p3));

        Map<Place, Long> marking = new HashMap<>();
        marking.put(p, -1L);
        marking.put(p1, 1L);

        marking.put(p2, 0L);
        marking.put(p3, -1L);

        assertTrue(t.enabled(marking));
        t.fire(marking);

        assertEquals(Long.valueOf(-1), marking.get(p));
        assertEquals(Long.valueOf(0), marking.get(p1));
        assertEquals(Long.valueOf(-1), marking.get(p3));
        assertEquals(Long.valueOf(10), marking.get(p2));
    }
}