package hse.se.aaizmaylov.petrinetscalculationserver.petrinets.basic.reductions;

import hse.se.aaizmaylov.petrinetscalculationserver.petrinets.analysis.AbstractFusionOfSelfLoop;
import hse.se.aaizmaylov.petrinetscalculationserver.petrinets.basic.Place;
import hse.se.aaizmaylov.petrinetscalculationserver.petrinets.basic.Transition;

public class FusionOfSelfLoopTransitions extends AbstractFusionOfSelfLoop<Integer, Transition, Place> {
    @Override
    protected boolean check(Transition vertex) {
        return true;
    }
}
