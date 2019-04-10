package hse.se.aaizmaylov.petrinetscalculationserver.petrinets.basic.reductions;

import hse.se.aaizmaylov.petrinetscalculationserver.petrinets.analysis.AbstractFusionOfParallel;
import hse.se.aaizmaylov.petrinetscalculationserver.petrinets.basic.Place;
import hse.se.aaizmaylov.petrinetscalculationserver.petrinets.basic.Transition;

public class FusionOfParallelTransitions extends AbstractFusionOfParallel<Integer, Place, Transition> {
    @Override
    protected boolean check(Place vertex) {
        return true;
    }

    @Override
    protected boolean checkNeighbour(Transition transition) {
        return true;
    }
}
