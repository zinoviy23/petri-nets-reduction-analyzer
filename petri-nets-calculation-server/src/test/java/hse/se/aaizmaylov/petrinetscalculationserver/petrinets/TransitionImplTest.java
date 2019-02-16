package hse.se.aaizmaylov.petrinetscalculationserver.petrinets;

import org.junit.Test;

import static org.junit.Assert.*;

public class TransitionImplTest {
    @Test
    public void fire() {
        Place place1 = new PlaceImpl(1);
        Place place2 = new PlaceImpl(2);
        Place place3 = new PlaceImpl(4);

        Transition transition = new TransitionImpl();
        transition.addInput(place1);
        transition.addInput(place2);
        transition.addOutput(place3);

        transition.fire();

        assertEquals(0, place1.getMarks());
        assertEquals(1, place2.getMarks());
        assertEquals(5, place3.getMarks());
    }

    @Test
    public void enabled() {
        Place place1 = new PlaceImpl(1);
        Place place2 = new PlaceImpl(2);
        Place place3 = new PlaceImpl(4);

        Transition transition = new TransitionImpl();
        transition.addInput(place1);
        transition.addInput(place2);
        transition.addOutput(place3);

        assertTrue(transition.enabled());
    }

    @Test
    public void disabled() {
        Place place1 = new PlaceImpl(1);
        Place place2 = new PlaceImpl(0);
        Place place3 = new PlaceImpl(4);

        Transition transition = new TransitionImpl();
        transition.addInput(place1);
        transition.addInput(place2);
        transition.addOutput(place3);

        assertFalse(transition.enabled());
    }
}