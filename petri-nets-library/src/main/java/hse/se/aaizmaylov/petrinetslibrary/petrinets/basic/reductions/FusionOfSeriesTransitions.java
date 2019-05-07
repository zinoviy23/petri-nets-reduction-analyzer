package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.reductions;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.AbstractFusionOfSeries;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Place;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Transition;
import org.jetbrains.annotations.NotNull;

@Deprecated
public class FusionOfSeriesTransitions extends AbstractFusionOfSeries<Long, Long, Transition, Place> {
    @Override
    protected boolean check(@NotNull Transition transition) {
        return true;
    }

    @Override
    protected boolean checkMergedVertex(@NotNull Transition transition) {
        return transition.getInputs().size() == 1;
    }

    @Override
    protected boolean checkNeighbour(@NotNull Place place) {
//        return place.getMarks() == 0;
        return true;
    }
}
