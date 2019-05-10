package hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.Arc;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.PetriNetVertex;
import lombok.NonNull;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static hse.se.aaizmaylov.petrinetslibrary.utils.CollectionsUtils.first;

public abstract class AbstractFusionOfSelfLoop<
        TTokenContainer,
        TWeight,
        TTarget extends PetriNetVertex<TTokenContainer, TWeight, TTarget, TNeighbour,
                Arc<TTokenContainer, TWeight, TNeighbour, TTarget>,
                Arc<TTokenContainer, TWeight, TTarget, TNeighbour>>,
        TNeighbour extends PetriNetVertex<TTokenContainer, TWeight, TNeighbour, TTarget,
                Arc<TTokenContainer, TWeight, TTarget, TNeighbour>,
                Arc<TTokenContainer, TWeight, TNeighbour, TTarget>>>
        implements Reduction<TTarget, TNeighbour> {

    private final static Logger LOGGER = Logger.getLogger(AbstractFusionOfSelfLoop.class);

    @Override
    public boolean reduceFrom(@NonNull TTarget target, @NonNull TransformCallback<TTarget, TNeighbour> callback,
                              @NonNull ReductionHistory reductionHistory) {
        if (!check(target))
            return false;

        List<EdgesPairIncidentWithVertex<TTokenContainer, TWeight, TTarget, TNeighbour>> edgesToRemove =
                new ArrayList<>();

        for (Arc<TTokenContainer, TWeight, TTarget, TNeighbour> arcToPotentialVertex : target.getOutputs()) {
            TNeighbour potentialVertex = arcToPotentialVertex.getToEndpoint();

            if (!checkNeighbour(potentialVertex) || potentialVertex.getOutputs().size() != 1 ||
                    potentialVertex.getInputs().size() != 1)
                continue;

            if (first(potentialVertex.getOutputs()).getToEndpoint() == target) {
                edgesToRemove.add(new EdgesPairIncidentWithVertex<>(arcToPotentialVertex,
                        first(potentialVertex.getOutputs())));
            }
        }

        if (edgesToRemove.isEmpty())
            return false;

        deleteEdgesToLoopedVertices(edgesToRemove, callback, reductionHistory);

        LOGGER.debug("Self loop! " + target);

        return true;
    }

    private void deleteEdgesToLoopedVertices(
            List<EdgesPairIncidentWithVertex<TTokenContainer, TWeight, TTarget, TNeighbour>> edgesToRemove,
            TransformCallback<TTarget, TNeighbour> callback,
            ReductionHistory history) {

        for (EdgesPairIncidentWithVertex<TTokenContainer, TWeight, TTarget, TNeighbour> pair : edgesToRemove) {
            pair.arcFromVertex.getToEndpoint().removeInput(pair.arcFromVertex);
            pair.arcToVertex.getFromEndpoint().removeOutput(pair.arcToVertex);
            callback.onDeleteNeighbour(pair.arcToVertex.getToEndpoint());
            history.delete(pair.arcToVertex.getToEndpoint());
        }
    }

    protected abstract boolean check(@NotNull TTarget vertex);

    protected abstract boolean checkNeighbour(@NotNull TNeighbour neighbour);
}
