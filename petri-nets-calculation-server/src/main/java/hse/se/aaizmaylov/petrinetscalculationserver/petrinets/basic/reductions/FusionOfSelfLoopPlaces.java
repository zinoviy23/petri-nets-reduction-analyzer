package hse.se.aaizmaylov.petrinetscalculationserver.petrinets.basic.reductions;

import hse.se.aaizmaylov.petrinetscalculationserver.petrinets.analysis.AbstractFusionOfSelfLoop;
import hse.se.aaizmaylov.petrinetscalculationserver.petrinets.basic.Place;
import hse.se.aaizmaylov.petrinetscalculationserver.petrinets.basic.Transition;

public class FusionOfSelfLoopPlaces extends AbstractFusionOfSelfLoop<Integer, Place, Transition> {
    @Override
    protected boolean check(Place vertex) {
        return vertex.getMarks() > 0;
    }
}
