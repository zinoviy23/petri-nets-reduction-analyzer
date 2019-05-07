package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransitionsTest {

    @Test
    void revertFiring() {
        Transition t = new TransitionImpl("t");
        Place p1 = new PlaceImpl(1, "p1");
        Place p2 = new PlaceImpl(4, "p2");
        Place p3 = new PlaceImpl(5, "p3");

        t.addInput(new FromPlaceToTransitionArc(p1, t));
        t.addInput(new FromPlaceToTransitionArc(p2, t, 3));
        t.addOutput(new FromTransitionToPlaceArc(t, p3, 2));

        t.fire();

        Transitions.revertFiring(t);

        assertEquals(1, p1.getMarks());
        assertEquals(4, p2.getMarks());
        assertEquals(5, p3.getMarks());
    }
}