package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic;

import org.junit.jupiter.api.Test;

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
}