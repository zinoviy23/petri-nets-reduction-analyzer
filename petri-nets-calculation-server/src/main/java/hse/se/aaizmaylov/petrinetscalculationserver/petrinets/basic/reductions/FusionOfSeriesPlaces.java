package hse.se.aaizmaylov.petrinetscalculationserver.petrinets.basic.reductions;

import hse.se.aaizmaylov.petrinetscalculationserver.petrinets.analysis.AbstractFusionOfSeries;
import hse.se.aaizmaylov.petrinetscalculationserver.petrinets.basic.Place;
import hse.se.aaizmaylov.petrinetscalculationserver.petrinets.basic.Transition;

public class FusionOfSeriesPlaces extends AbstractFusionOfSeries<Integer, Place, Transition> {

    //TODO проверить, что ссылка на удалённый объёкт Place удалилась(нет весящих ссылок)

    @Override
    protected boolean check(Place place) {
        return place.getMarks() == 0 && place.getOutputs().size() == 1;
    }

    @Override
    protected boolean checkMergedVertex(Place vertex) {
        return vertex.getMarks() == 0;
    }

    @Override
    protected boolean checkNeighbour(Transition transition) {
        return true;
    }

}
