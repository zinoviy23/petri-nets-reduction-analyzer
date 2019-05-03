package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.Arc;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.PetriNetVertex;

public interface Transition extends PetriNetVertex<
        Long,
        Long,
        Transition,
        Place,
        Arc<Long, Long, Place, Transition>,
        Arc<Long, Long, Transition, Place>> {
    void fire();

    boolean enabled();
}
