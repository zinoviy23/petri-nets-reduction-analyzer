package hse.se.aaizmaylov.petrinetscalculationserver.petrinets;

public interface Place extends PetriNetVertex<Place, Transition> {
    int getMarks();

    void setMarks(int marks);

    int addMarks(int marks);

    int removeMarks(int marks);
}
