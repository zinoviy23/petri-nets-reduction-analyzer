package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.reductions;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.AbstractFusionOfSeries;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Place;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Transition;

public class FusionOfSeriesTransitions extends AbstractFusionOfSeries<Integer, Transition, Place> {
    @Override
    protected boolean check(Transition transition) {
        return true;
    }

    @Override
    protected boolean checkMergedVertex(Transition transition) {
        return transition.getInputs().size() == 1;
    }

    @Override
    protected boolean checkNeighbour(Place place) {
//        return place.getMarks() == 0;
        return true;
    }
}