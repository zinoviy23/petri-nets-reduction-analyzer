package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.Edge;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.PetriNetVertex;

public interface Transition extends PetriNetVertex<
        Integer,
        Transition,
        Place,
        Edge<Integer, Place, Transition>,
        Edge<Integer, Transition, Place>> {
    void fire();

    boolean enabled();
}
