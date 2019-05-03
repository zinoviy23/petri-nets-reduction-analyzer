package hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.Arc;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.PetriNetVertex;
import lombok.NonNull;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static hse.se.aaizmaylov.petrinetslibrary.utils.CollectionsUtils.first;

public abstract class AbstractFusionOfSelfLoop<
        TTokenContainer,
        TTarget extends PetriNetVertex<TTokenContainer, TTarget, TNeighbour, Arc<TTokenContainer, TNeighbour, TTarget>,
                Arc<TTokenContainer, TTarget, TNeighbour>>,
        TNeighbour extends PetriNetVertex<TTokenContainer, TNeighbour, TTarget, Arc<TTokenContainer, TTarget, TNeighbour>,
                Arc<TTokenContainer, TNeighbour, TTarget>>>
        implements Reduction<TTarget, TNeighbour> {

    private final static Logger LOGGER = Logger.getLogger(AbstractFusionOfSelfLoop.class);

    @Override
    public boolean reduceFrom(@NonNull TTarget target, @NonNull DeleteVertexCallback<TTarget, TNeighbour> callback) {
        if (!check(target))
            return false;

        List<EdgesPairIncidentWithVertex<TTokenContainer, TTarget, TNeighbour>> edgesToRemove = new ArrayList<>();

        for (Arc<TTokenContainer, TTarget, TNeighbour> arcToPotentialVertex : target.getOutputs()) {
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

        deleteEdgesToLoopedVertices(edgesToRemove, callback);

        LOGGER.debug("Self loop! " + target);

        return true;
    }

    private void deleteEdgesToLoopedVertices(
            List<EdgesPairIncidentWithVertex<TTokenContainer, TTarget, TNeighbour>> edgesToRemove,
            DeleteVertexCallback<TTarget, TNeighbour> callback) {

        for (EdgesPairIncidentWithVertex<TTokenContainer, TTarget, TNeighbour> pair : edgesToRemove) {
            pair.arcFromVertex.getToEndpoint().removeInput(pair.arcFromVertex);
            pair.arcToVertex.getFromEndpoint().removeOutput(pair.arcToVertex);
            callback.onDeleteNeighbour(pair.arcToVertex.getToEndpoint());
        }
    }

    protected abstract boolean check(TTarget vertex);

    protected abstract boolean checkNeighbour(TNeighbour neighbour);
}
