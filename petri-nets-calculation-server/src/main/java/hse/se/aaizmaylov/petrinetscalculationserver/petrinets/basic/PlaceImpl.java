package hse.se.aaizmaylov.petrinetscalculationserver.petrinets.basic;

import hse.se.aaizmaylov.petrinetscalculationserver.petrinets.AbstractPetriNetVertexImpl;
import hse.se.aaizmaylov.petrinetscalculationserver.petrinets.Edge;
import lombok.Getter;

public class PlaceImpl extends AbstractPetriNetVertexImpl<
        Integer,
        Place,
        Transition,
        Edge<Integer, Transition, Place>,
        Edge<Integer, Place, Transition>> implements Place {

    @Getter
    private int marks;

    PlaceImpl(int marks) {
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
