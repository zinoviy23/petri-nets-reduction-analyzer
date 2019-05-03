package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.Arc;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.PetriNetVertex;

public interface Transition extends PetriNetVertex<
        Integer,
        Transition,
        Place,
        Arc<Integer, Place, Transition>,
        Arc<Integer, Transition, Place>> {
    void fire();

    boolean enabled();
}
