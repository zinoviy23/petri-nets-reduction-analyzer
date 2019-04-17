package hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.Edge;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.PetriNetVertex;
import lombok.NonNull;
import org.apache.log4j.Logger;

import static hse.se.aaizmaylov.petrinetslibrary.utils.CollectionsUtils.first;

public abstract class AbstractFusionOfSeries<
        TTokenContainer,
        TTarget extends PetriNetVertex<TTokenContainer, TTarget, TNeighbour, Edge<TTokenContainer, TNeighbour, TTarget>,
                Edge<TTokenContainer, TTarget, TNeighbour>>,
        TNeighbour extends PetriNetVertex<TTokenContainer, TNeighbour, TTarget, Edge<TTokenContainer, TTarget, TNeighbour>,
                Edge<TTokenContainer, TNeighbour, TTarget>>>
        implements Reduction<TTarget> {

    private final static Logger LOGGER = Logger.getLogger(AbstractFusionOfSeries.class);

    @Override
    public boolean reduceFrom(@NonNull TTarget target) {
        if (!check(target))
            return false;

        Edge<TTokenContainer, TTarget, TNeighbour> reducedEdge = null;

        for (Edge<TTokenContainer, TTarget, TNeighbour> output : target.getOutputs()) {
            if (output.getToEndpoint().getOutputs().size() == 1 && output.getToEndpoint().getInputs().size() == 1 &&
                    checkNeighbour(output.getToEndpoint())) {
                TTarget vertexToMerge = first(output.getToEndpoint().getOutputs()).getToEndpoint();

                if (checkMergedVertex(vertexToMerge)) {
                    reducedEdge = output;
                    break;
                }
            }
        }

        if (reducedEdge == null)
            return false;

        mergePlacesConnectedByTransition(reducedEdge);

        LOGGER.debug("Series! " + target);
        return true;
    }

    private void mergePlacesConnectedByTransition(Edge<TTokenContainer, TTarget, TNeighbour> connectingTransition) {
        TTarget firstPlace = connectingTransition.getFromEndpoint();
        TTarget secondPlace = first(connectingTransition.getToEndpoint().getOutputs()).getToEndpoint();

        firstPlace.removeOutput(connectingTransition);
        secondPlace.removeInput(first(connectingTransition.getToEndpoint().getOutputs()));

        secondPlace.getInputs().forEach(e -> e.setToEndpoint(firstPlace));
        secondPlace.getOutputs().forEach(e -> e.setFromEndpoint(firstPlace));
        secondPlace.getInputs().forEach(firstPlace::addInput);
        secondPlace.getOutputs().forEach(firstPlace::addOutput);
    }

    protected abstract boolean check(TTarget target);

    protected abstract boolean checkMergedVertex(TTarget vertex);

    protected abstract boolean checkNeighbour(TNeighbour neighbour);
}
