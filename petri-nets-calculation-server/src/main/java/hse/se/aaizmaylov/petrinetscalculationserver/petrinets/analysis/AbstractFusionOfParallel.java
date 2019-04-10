package hse.se.aaizmaylov.petrinetscalculationserver.petrinets.analysis;

import hse.se.aaizmaylov.petrinetscalculationserver.petrinets.Edge;
import hse.se.aaizmaylov.petrinetscalculationserver.petrinets.PetriNetVertex;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static hse.se.aaizmaylov.petrinetscalculationserver.utils.CollectionsUtils.first;

public abstract class AbstractFusionOfParallel<
        TTokenContainer,
        TTarget extends PetriNetVertex<TTokenContainer, TTarget, TNeighbour, Edge<TTokenContainer, TNeighbour, TTarget>,
                Edge<TTokenContainer, TTarget, TNeighbour>>,
        TNeighbour extends PetriNetVertex<TTokenContainer, TNeighbour, TTarget, Edge<TTokenContainer, TTarget, TNeighbour>,
                Edge<TTokenContainer, TNeighbour, TTarget>>>
        implements Reduction<TTarget>  {

    @Override
    public boolean reduceFrom(@NonNull TTarget target) {
        if (!check(target))
            return false;

        Map<TTarget, List<EdgePairs<TTokenContainer, TTarget, TNeighbour>>> edgesToMerge = new HashMap<>();

        for (Edge<TTokenContainer, TTarget, TNeighbour> outputEdge : target.getOutputs()) {
            if (checkNeighbour(outputEdge.getToEndpoint())) {
                TNeighbour possibleVertexToMerge = outputEdge.getToEndpoint();

                if (possibleVertexToMerge.getInputs().size() == 1 && possibleVertexToMerge.getOutputs().size() == 1) {
                    Edge<TTokenContainer, TNeighbour, TTarget> fromPossibleVertexEdge =
                            first(possibleVertexToMerge.getOutputs());

                    edgesToMerge.computeIfAbsent(fromPossibleVertexEdge.getToEndpoint(), unused -> new ArrayList<>())
                            .add(new EdgePairs<>(outputEdge, fromPossibleVertexEdge));
                }
            }
        }

        return mergeEdges(edgesToMerge);
    }

    private boolean mergeEdges(Map<TTarget, List<EdgePairs<TTokenContainer, TTarget, TNeighbour>>> edgesToMerge) {
        boolean mergedSomething = false;

        for (Map.Entry<TTarget, List<EdgePairs<TTokenContainer, TTarget, TNeighbour>>> entry :
                edgesToMerge.entrySet()) {
            if (entry.getValue().size() < 2)
                continue;

            for (int i = 1; i < entry.getValue().size(); i++) {
                EdgePairs<TTokenContainer, TTarget, TNeighbour> currentPair = entry.getValue().get(i);

                currentPair.edgeToMergingVertex.getFromEndpoint().removeOutput(currentPair.edgeToMergingVertex);
                currentPair.edgeFromMergingVertex.getToEndpoint().removeInput(currentPair.edgeFromMergingVertex);
            }

            mergedSomething = true;
        }

        return mergedSomething;
    }

    private static class EdgePairs<TTokenContainer, TTarget, TNeighbour> {
        private final Edge<TTokenContainer, TTarget, TNeighbour> edgeToMergingVertex;

        private final Edge<TTokenContainer, TNeighbour, TTarget> edgeFromMergingVertex;

        private EdgePairs(Edge<TTokenContainer, TTarget, TNeighbour> edgeToMergingVertex,
                          Edge<TTokenContainer, TNeighbour, TTarget> edgeFromMergingVertex) {
            this.edgeToMergingVertex = edgeToMergingVertex;
            this.edgeFromMergingVertex = edgeFromMergingVertex;
        }
    }

    protected abstract boolean check(TTarget vertex);

    protected abstract boolean checkNeighbour(TNeighbour neighbour);
}
