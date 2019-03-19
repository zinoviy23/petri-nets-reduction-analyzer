package hse.se.aaizmaylov.petrinetscalculationserver.petrinets.basic;

import hse.se.aaizmaylov.petrinetscalculationserver.petrinets.Edge;
import hse.se.aaizmaylov.petrinetscalculationserver.petrinets.PetriNetVertex;

public interface Place extends PetriNetVertex<
        Integer,
        Place,
        Transition,
        Edge<Integer, Transition, Place>,
        Edge<Integer, Place, Transition>> {

    int getMarks();

    void setMarks(int marks);

    int addMarks(int marks);

    int removeMarks(int marks);
}
