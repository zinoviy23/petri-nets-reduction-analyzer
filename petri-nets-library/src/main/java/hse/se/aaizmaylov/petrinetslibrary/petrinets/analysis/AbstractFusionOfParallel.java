package hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.Arc;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.PetriNetVertex;
import lombok.NonNull;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static hse.se.aaizmaylov.petrinetslibrary.utils.CollectionsUtils.first;

public abstract class AbstractFusionOfParallel<
        TTokenContainer,
        TWeight,
        TTarget extends PetriNetVertex<TTokenContainer, TWeight, TTarget, TNeighbour,
                Arc<TTokenContainer, TWeight, TNeighbour, TTarget>,
                Arc<TTokenContainer, TWeight, TTarget, TNeighbour>>,
        TNeighbour extends PetriNetVertex<TTokenContainer, TWeight, TNeighbour, TTarget,
                Arc<TTokenContainer, TWeight, TTarget, TNeighbour>,
                Arc<TTokenContainer, TWeight, TNeighbour, TTarget>>>
        implements Reduction<TTarget, TNeighbour>  {

    private static final Logger LOGGER = Logger.getLogger(AbstractFusionOfParallel.class);

    @Override
    public boolean reduceFrom(@NonNull TTarget target,
                              @NonNull TransformCallback<TTarget, TNeighbour> callback,
                              @NonNull ReductionHistory reductionHistory) {
        if (!check(target))
            return false;

        Map<TTarget, List<EdgesPairIncidentWithVertex<TTokenContainer, TWeight, TTarget, TNeighbour>>> edgesToMerge
                = new HashMap<>();

        for (Arc<TTokenContainer, TWeight, TTarget, TNeighbour> outputArc : target.getOutputs()) {
            if (checkNeighbour(outputArc.getToEndpoint())) {
                TNeighbour possibleVertexToMerge = outputArc.getToEndpoint();

                if (possibleVertexToMerge.getInputs().size() == 1 && possibleVertexToMerge.getOutputs().size() == 1) {
                    Arc<TTokenContainer, TWeight, TNeighbour, TTarget> fromPossibleVertexArc =
                            first(possibleVertexToMerge.getOutputs());

                    edgesToMerge.computeIfAbsent(fromPossibleVertexArc.getToEndpoint(), unused -> new ArrayList<>())
                            .add(new EdgesPairIncidentWithVertex<>(outputArc, fromPossibleVertexArc));
                }
            }
        }

        return logIfResult(mergeEdges(edgesToMerge, callback, reductionHistory), "Parallel! " + target);
    }

    private static boolean logIfResult(boolean result, String value) {
        if (result)
            LOGGER.debug(value);

        return result;
    }

    private boolean mergeEdges(
            Map<TTarget, List<EdgesPairIncidentWithVertex<TTokenContainer, TWeight, TTarget, TNeighbour>>> edgesToMerge,
            TransformCallback<TTarget, TNeighbour> callback,
            ReductionHistory history) {

        boolean mergedSomething = false;

        for (Map.Entry<TTarget,
                List<EdgesPairIncidentWithVertex<TTokenContainer, TWeight, TTarget, TNeighbour>>> entry :
                edgesToMerge.entrySet()) {
            if (entry.getValue().size() < 2)
                continue;

            for (int i = 1; i < entry.getValue().size(); i++) {
                EdgesPairIncidentWithVertex<TTokenContainer, TWeight, TTarget, TNeighbour> currentPair
                        = entry.getValue().get(i);

                currentPair.arcToVertex.getFromEndpoint().removeOutput(currentPair.arcToVertex);
                currentPair.arcFromVertex.getToEndpoint().removeInput(currentPair.arcFromVertex);
                history.delete(currentPair.arcToVertex.getToEndpoint());
                callback.onDeleteNeighbour(currentPair.arcToVertex.getToEndpoint());
            }

            mergedSomething = true;
        }

        return mergedSomething;
    }

    protected abstract boolean check(@NotNull TTarget vertex);

    protected abstract boolean checkNeighbour(@NotNull TNeighbour neighbour);
}
