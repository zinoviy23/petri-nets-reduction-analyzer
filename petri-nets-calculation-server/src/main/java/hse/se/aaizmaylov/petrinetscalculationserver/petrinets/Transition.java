package hse.se.aaizmaylov.petrinetscalculationserver.petrinets;

public interface Transition extends PetriNetVertex<Transition, Place> {
    void fire();

    boolean enabled();
}
