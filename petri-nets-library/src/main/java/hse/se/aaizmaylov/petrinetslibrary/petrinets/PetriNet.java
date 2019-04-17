package hse.se.aaizmaylov.petrinetslibrary.petrinets;

import lombok.NonNull;

import java.util.*;
import java.util.stream.Collectors;

public class PetriNet<TPlace extends LabeledVertex, TTransition extends LabeledVertex> {
    private Map<String, TPlace> places;

    private Map<String, TTransition> transitions;

    public PetriNet(Collection<? extends TPlace> places, Collection<? extends TTransition> transitions) {
        this.places = places.stream().collect(Collectors.toMap(LabeledVertex::label, p->p));
        this.transitions = transitions.stream().collect(Collectors.toMap(LabeledVertex::label, t->t));
    }

    public PetriNet() {
        this(Collections.emptyList(), Collections.emptyList());
    }

    public void addTransition(@NonNull TTransition transition) {
        transitions.put(transition.label(), transition);
    }

    public void addPlace(@NonNull TPlace place) {
        places.put(place.label(), place);
    }

    public void removePlace(TPlace place) {
        places.remove(place.label());
    }

    public void removeTransition(TTransition transition) {
        transitions.remove(transition.label());
    }

    public Collection<TPlace> getPlaces() {
        return Collections.unmodifiableCollection(places.values());
    }

    public Collection<TTransition> getTransitions() {
        return Collections.unmodifiableCollection(transitions.values());
    }

    public Map<String, TPlace> getPlacesMap() {
        return Collections.unmodifiableMap(places);
    }

    public Map<String, TTransition> getTransitionsMap() {
        return Collections.unmodifiableMap(transitions);
    }
}
