package hse.se.aaizmaylov.petrinetscalculationserver.petrinets.basic.reductions;

import hse.se.aaizmaylov.petrinetscalculationserver.petrinets.analysis.AbstractFusionOfParallel;
import hse.se.aaizmaylov.petrinetscalculationserver.petrinets.basic.Place;
import hse.se.aaizmaylov.petrinetscalculationserver.petrinets.basic.Transition;

public class FusionOfParallelPlaces extends AbstractFusionOfParallel<Integer, Transition, Place> {
    @Override
    protected boolean check(Transition vertex) {
        return true;
    }

    @Override
    protected boolean checkNeighbour(Place place) {
        return place.getMarks() == 0;
    }
}
