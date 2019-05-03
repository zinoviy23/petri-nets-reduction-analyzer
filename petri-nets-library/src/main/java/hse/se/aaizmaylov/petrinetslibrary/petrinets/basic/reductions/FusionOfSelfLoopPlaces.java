package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.reductions;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.AbstractFusionOfSelfLoop;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Place;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.Transition;
import org.jetbrains.annotations.NotNull;

public class FusionOfSelfLoopPlaces extends AbstractFusionOfSelfLoop<Long, Long, Transition, Place> {
    @Override
    protected boolean check(@NotNull Transition vertex) {
        return true;
    }

    @Override
    protected boolean checkNeighbour(@NotNull Place place) {
        return place.getMarks() > 0;
    }
}
