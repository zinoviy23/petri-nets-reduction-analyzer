package hse.se.aaizmaylov.petrinetscalculationserver.petrinets;

public class PlaceImpl extends AbstractPetriNetVertexImpl<Place, Transition> implements Place {
    private int marksCount;

    PlaceImpl(int marksCount) {
        checkMarksCount(marksCount);
        this.marksCount = marksCount;
    }

    @Override
    public int getMarks() {
        return marksCount;
    }

    @Override
    public void setMarks(int marks) {
        checkMarksCount(marks);
        marksCount = marks;
    }

    @Override
    public int addMarks(int marks) {
        checkMarksCount(marksCount + marks);
        return marksCount += marks;
    }

    @Override
    public int removeMarks(int marks) {
        checkMarksCount(marksCount - marks);
        return marksCount -= marks;
    }

    private static void checkMarksCount(int marksCount) {
        if (marksCount < 0)
            throw new IllegalArgumentException("Marks count must be more or equals than 0");
    }
}
