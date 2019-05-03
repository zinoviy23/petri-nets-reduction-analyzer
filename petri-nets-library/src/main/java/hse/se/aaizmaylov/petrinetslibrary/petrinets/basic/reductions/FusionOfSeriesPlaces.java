package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.reductions;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.AbstractFusionOfSeries;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Place;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Transition;
import org.jetbrains.annotations.NotNull;

public class FusionOfSeriesPlaces extends AbstractFusionOfSeries<Long, Long, Place, Transition> {

    @Override
    protected boolean check(@NotNull Place place) {
        return place.getMarks() == 0 && place.getOutputs().size() == 1;
    }

    @Override
    protected boolean checkMergedVertex(@NotNull Place vertex) {
        return vertex.getMarks() == 0;
    }

    @Override
    protected boolean checkNeighbour(@NotNull Transition transition) {
        return true;
    }

}
