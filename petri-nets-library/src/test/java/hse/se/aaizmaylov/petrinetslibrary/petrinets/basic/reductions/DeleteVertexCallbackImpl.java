package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.reductions;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.DeleteVertexCallback;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Place;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Transition;

class DeleteVertexCallbackImpl implements DeleteVertexCallback<Place, Transition> {

    private int places;

    private int transitions;

    @Override
    public void onDeleteTarget(Place place) {
        places++;
    }

    @Override
    public void onDeleteNeighbour(Transition transition) {
        transitions++;
    }

    int getTransitions() {
        return transitions;
    }

    int getPlaces() {
        return places;
    }
}
