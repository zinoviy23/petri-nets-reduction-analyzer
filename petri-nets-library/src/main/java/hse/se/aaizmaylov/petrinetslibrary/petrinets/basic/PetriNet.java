package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PetriNet {
    private Set<Place> places;

    private Set<Transition> transitions;

    public PetriNet(Collection<? extends Place> places, Collection<? extends Transition> transitions) {
        this.places = new HashSet<>(places);
        this.transitions = new HashSet<>(transitions);
    }

    public PetriNet() {
        this(Collections.emptyList(), Collections.emptyList());
    }

    public void addTransition(Transition transition) {
        transitions.add(transition);
    }

    public void addPlace(Place place) {
        places.add(place);
    }

    public void removePlace(Place place) {
        places.remove(place);
    }

    public void removeTransition(Transition transition) {
        transitions.remove(transition);
    }

    public Set<Place> getPlaces() {
        return Collections.unmodifiableSet(places);
    }

    public Set<Transition> getTransitions() {
        return Collections.unmodifiableSet(transitions);
    }
}
