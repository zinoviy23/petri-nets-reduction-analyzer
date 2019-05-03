package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.Arc;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.PetriNetVertex;

public interface Place extends PetriNetVertex<
        Integer,
        Place,
        Transition,
        Arc<Integer, Transition, Place>,
        Arc<Integer, Place, Transition>> {
    int getMarks();

    void setMarks(int marks);

    int addMarks(int marks);

    int removeMarks(int marks);

    static Place withMarks(int marks, String label) {
        return new PlaceImpl(marks, label);
    }
}
