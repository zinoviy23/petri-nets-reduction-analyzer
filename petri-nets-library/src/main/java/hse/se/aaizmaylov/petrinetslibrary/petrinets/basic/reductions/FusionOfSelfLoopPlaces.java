package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.reductions;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.AbstractFusionOfSelfLoop;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Place;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Transition;

public class FusionOfSelfLoopPlaces extends AbstractFusionOfSelfLoop<Integer, Place, Transition> {
    @Override
    protected boolean check(Place vertex) {
        return vertex.getMarks() > 0;
    }
}
