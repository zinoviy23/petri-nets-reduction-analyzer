package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.analysis;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.Reduction;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.PetriNet;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Place;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Transition;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Reducer {
    private PetriNet petriNet;

    private boolean reduced = false;

    private List<Place> placesToDelete = new ArrayList<>();
    private List<Transition> transitionsToDelete = new ArrayList<>();

    public Reducer(@NonNull PetriNet petriNet) {
        this.petriNet = petriNet;
    }

    public void reduce(Collection<? extends Reduction<Place>> reductionsOnPlaces,
                       Collection<? extends Reduction<Transition>> reductionsOnTransitions) {
        if (reduced) {
            throw new IllegalStateException("Petri Net already reduced");
        }


        reduced = true;
    }
}
