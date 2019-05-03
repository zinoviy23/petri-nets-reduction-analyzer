package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.Arc;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.PetriNetVertex;

public interface Place extends PetriNetVertex<
        Long,
        Long,
        Place,
        Transition,
        Arc<Long, Long, Transition, Place>,
        Arc<Long, Long, Place, Transition>> {
    long getMarks();

    void setMarks(long marks);

    void addMarks(long marks);

    void removeMarks(long marks);

    static Place withMarks(long marks, String label) {
        return new PlaceImpl(marks, label);
    }
}
