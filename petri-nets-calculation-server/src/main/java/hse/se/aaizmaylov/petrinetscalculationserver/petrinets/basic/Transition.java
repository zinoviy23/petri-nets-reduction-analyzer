package hse.se.aaizmaylov.petrinetscalculationserver.petrinets.basic;

import hse.se.aaizmaylov.petrinetscalculationserver.petrinets.Edge;
import hse.se.aaizmaylov.petrinetscalculationserver.petrinets.PetriNetVertex;

public interface Transition extends PetriNetVertex<
        Integer,
        Transition,
        Place,
        Edge<Integer, Place, Transition>,
        Edge<Integer, Transition, Place>> {

    void fire();

    boolean enabled();
}
