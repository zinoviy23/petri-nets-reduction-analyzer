package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.reductions;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.AbstractFusionOfSelfLoop;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Place;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Transition;

public class FusionOfSelfLoopPlaces extends AbstractFusionOfSelfLoop<Integer, Transition, Place> {
    @Override
    protected boolean check(Transition vertex) {
        return true;
    }

    @Override
    protected boolean checkNeighbour(Place place) {
        return place.getMarks() > 0;
    }
}
