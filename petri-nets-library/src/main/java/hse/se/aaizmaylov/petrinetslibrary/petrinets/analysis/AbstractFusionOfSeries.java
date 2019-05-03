package hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.Arc;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.PetriNetVertex;
import lombok.NonNull;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import static hse.se.aaizmaylov.petrinetslibrary.utils.CollectionsUtils.first;

public abstract class AbstractFusionOfSeries<
        TTokenContainer,
        TWeight,
        TTarget extends PetriNetVertex<TTokenContainer, TWeight, TTarget, TNeighbour,
                Arc<TTokenContainer, TWeight, TNeighbour, TTarget>,
                Arc<TTokenContainer, TWeight, TTarget, TNeighbour>>,
        TNeighbour extends PetriNetVertex<TTokenContainer, TWeight, TNeighbour, TTarget,
                Arc<TTokenContainer, TWeight, TTarget, TNeighbour>,
                Arc<TTokenContainer, TWeight, TNeighbour, TTarget>>>
        implements Reduction<TTarget, TNeighbour> {

    private final static Logger LOGGER = Logger.getLogger(AbstractFusionOfSeries.class);

    @Override
    public boolean reduceFrom(@NonNull TTarget target, @NonNull DeleteVertexCallback<TTarget, TNeighbour> callback) {
        if (!check(target))
            return false;

        Arc<TTokenContainer, TWeight, TTarget, TNeighbour> reducedArc = null;

        for (Arc<TTokenContainer, TWeight, TTarget, TNeighbour> output : target.getOutputs()) {
            if (output.getToEndpoint().getOutputs().size() == 1 && output.getToEndpoint().getInputs().size() == 1 &&
                    checkNeighbour(output.getToEndpoint())) {
                TTarget vertexToMerge = first(output.getToEndpoint().getOutputs()).getToEndpoint();

                if (checkMergedVertex(vertexToMerge)) {
                    reducedArc = output;
                    break;
                }
            }
        }

        if (reducedArc == null)
            return false;

        mergeVertexConnectedByNeighbourVertex(reducedArc, callback);

        LOGGER.debug("Series! " + target);
        return true;
    }

    private void mergeVertexConnectedByNeighbourVertex(
            Arc<TTokenContainer, TWeight, TTarget, TNeighbour> arcToConnectingNeighbour,
            DeleteVertexCallback<TTarget, TNeighbour> callback) {

        TTarget firstVertex = arcToConnectingNeighbour.getFromEndpoint();
        TTarget secondVertex = first(arcToConnectingNeighbour.getToEndpoint().getOutputs()).getToEndpoint();

        callback.onDeleteNeighbour(arcToConnectingNeighbour.getToEndpoint());

        firstVertex.removeOutput(arcToConnectingNeighbour);
        secondVertex.removeInput(first(arcToConnectingNeighbour.getToEndpoint().getOutputs()));

        secondVertex.getInputs().forEach(e -> e.setToEndpoint(firstVertex));
        secondVertex.getOutputs().forEach(e -> e.setFromEndpoint(firstVertex));
        secondVertex.getInputs().forEach(firstVertex::addInput);
        secondVertex.getOutputs().forEach(firstVertex::addOutput);

        callback.onDeleteTarget(secondVertex);
    }

    protected abstract boolean check(@NotNull TTarget target);

    protected abstract boolean checkMergedVertex(@NotNull TTarget vertex);

    protected abstract boolean checkNeighbour(@NotNull TNeighbour neighbour);
}
