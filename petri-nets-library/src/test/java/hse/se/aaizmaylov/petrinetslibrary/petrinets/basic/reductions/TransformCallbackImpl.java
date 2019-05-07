package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.reductions;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.TransformCallback;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Place;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Transition;
import lombok.Getter;

class TransformCallbackImpl implements TransformCallback<Place, Transition> {

    @Getter
    private int places;

    @Getter
    private int transitions;

    @Getter
    private int addedPlaces;

    @Getter
    private int addedTransitions;

    @Override
    public void onDeleteTarget(Place place) {
        places++;
    }

    @Override
    public void onDeleteNeighbour(Transition transition) {
        transitions++;
    }

    @Override
    public void onAddTarget(Place place) {
        addedPlaces++;
    }

    @Override
    public void onAddNeighbour(Transition transition) {
        addedTransitions++;
    }
}
