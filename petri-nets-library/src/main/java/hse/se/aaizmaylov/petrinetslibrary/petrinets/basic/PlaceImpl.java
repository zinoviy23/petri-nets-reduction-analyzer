package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.AbstractPetriNetVertexImpl;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.Arc;
import lombok.Getter;

public class PlaceImpl extends AbstractPetriNetVertexImpl<
        Integer,
        Place,
        Transition,
        Arc<Integer, Transition, Place>,
        Arc<Integer, Place, Transition>> implements Place {

    @Getter
    private int marks;

    PlaceImpl(int marks, String label) {
        super(label);
        checkMarksCount(marks);
        this.marks = marks;
    }

    @Override
    public void setMarks(int marks) {
        checkMarksCount(marks);
        this.marks = marks;
    }

    @Override
    public int addMarks(int marks) {
        checkMarksCount(this.marks + marks);
        return this.marks += marks;
    }

    @Override
    public int removeMarks(int marks) {
        checkMarksCount(this.marks - marks);
        return this.marks -= marks;
    }

    private static void checkMarksCount(int marksCount) {
        if (marksCount < 0)
            throw new IllegalArgumentException("Marks count must be more or equals than 0");
    }
}
