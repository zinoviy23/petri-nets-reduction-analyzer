package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.AbstractPetriNetVertexImpl;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.Arc;
import lombok.Getter;

public class PlaceImpl extends AbstractPetriNetVertexImpl<
        Long,
        Long,
        Place,
        Transition,
        Arc<Long, Long, Transition, Place>,
        Arc<Long, Long, Place, Transition>> implements Place {

    @Getter
    private long marks;

    PlaceImpl(long marks, String label) {
        super(label);
        checkMarksCount(marks);
        this.marks = marks;
    }

    @Override
    public void setMarks(long marks) {
        checkMarksCount(marks);
        this.marks = marks;
    }

    @Override
    public void addMarks(long marks) {
        checkMarksCount(this.marks + marks);
        this.marks += marks;
    }

    @Override
    public void removeMarks(long marks) {
        checkMarksCount(this.marks - marks);
        this.marks -= marks;
    }

    private static void checkMarksCount(long marksCount) {
        if (marksCount < 0)
            throw new IllegalArgumentException("Marks count must be more or equals than 0");
    }
}
