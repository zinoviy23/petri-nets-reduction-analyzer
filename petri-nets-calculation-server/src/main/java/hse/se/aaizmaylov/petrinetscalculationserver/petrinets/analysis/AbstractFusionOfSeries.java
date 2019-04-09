package hse.se.aaizmaylov.petrinetscalculationserver.petrinets.analysis;

import hse.se.aaizmaylov.petrinetscalculationserver.petrinets.Edge;
import hse.se.aaizmaylov.petrinetscalculationserver.petrinets.PetriNetVertex;
import lombok.NonNull;

import static hse.se.aaizmaylov.petrinetscalculationserver.utils.CollectionsUtils.first;

public abstract class AbstractFusionOfSeries<
        TTokenContainer,
        TTarget extends PetriNetVertex<TTokenContainer, TTarget, TNeighbour, Edge<TTokenContainer, TNeighbour, TTarget>,
                Edge<TTokenContainer, TTarget, TNeighbour>>,
        TNeighbour extends PetriNetVertex<TTokenContainer, TNeighbour, TTarget, Edge<TTokenContainer, TTarget, TNeighbour>,
                Edge<TTokenContainer, TNeighbour, TTarget>>>
        implements Reduction<TTarget> {
    @Override
    public boolean reduceFrom(@NonNull TTarget target) {
        if (!check(target))
            return false;

        Edge<TTokenContainer, TTarget, TNeighbour> reducedEdge = null;

        for (Edge<TTokenContainer, TTarget, TNeighbour> output : target.getOutputs()) {
            if (output.getTo().getOutputs().size() == 1 && output.getTo().getInputs().size() == 1 &&
                    checkNeighbour(output.getTo())) {
                TTarget vertexToMerge = first(output.getTo().getOutputs()).getTo();

                if (checkMergedVertex(vertexToMerge)) {
                    reducedEdge = output;
                    break;
                }
            }
        }

        if (reducedEdge == null)
            return false;

        mergePlacesConnectedByTransition(reducedEdge);

        return true;
    }

    private void mergePlacesConnectedByTransition(Edge<TTokenContainer, TTarget, TNeighbour> connectingTransition) {
        TTarget firstPlace = connectingTransition.getFrom();
        TTarget secondPlace = first(connectingTransition.getTo().getOutputs()).getTo();

        firstPlace.removeOutput(connectingTransition);
        secondPlace.removeInput(first(connectingTransition.getTo().getOutputs()));

        secondPlace.getInputs().forEach(e -> e.setTo(firstPlace));
        secondPlace.getOutputs().forEach(e -> e.setFrom(firstPlace));
        secondPlace.getInputs().forEach(firstPlace::addInput);
        secondPlace.getOutputs().forEach(firstPlace::addOutput);
    }

    protected abstract boolean check(TTarget target);

    protected abstract boolean checkMergedVertex(TTarget vertex);

    protected abstract boolean checkNeighbour(TNeighbour neighbour);
}
