package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.reductions;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.AbstractFusionOfSelfLoop;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Place;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Transition;
import org.jetbrains.annotations.NotNull;

public class FusionOfSelfLoopTransitions extends AbstractFusionOfSelfLoop<Long, Long, Place, Transition> {
    @Override
    protected boolean check(@NotNull Place vertex) {
        return true;
    }

    @Override
    protected boolean checkNeighbour(@NotNull Transition transition) {
        return !transition.getInputs().isEmpty();
    }
}
