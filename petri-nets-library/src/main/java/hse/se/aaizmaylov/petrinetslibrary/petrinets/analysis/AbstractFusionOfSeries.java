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
        implements Reduction<TTarget, TNeighbour> {

    private final static Logger LOGGER = Logger.getLogger(AbstractFusionOfSeries.class);

    @Override
    public boolean reduceFrom(@NonNull TTarget target, @NonNull DeleteVertexCallback<TTarget, TNeighbour> callback) {
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

        mergeVertexConnectedByNeighbourVertex(reducedEdge, callback);

        LOGGER.debug("Series! " + target);
        return true;
    }

    private void mergeVertexConnectedByNeighbourVertex(
            Edge<TTokenContainer, TTarget, TNeighbour> edgeToConnectingNeighbour,
            DeleteVertexCallback<TTarget, TNeighbour> callback) {

        TTarget firstVertex = edgeToConnectingNeighbour.getFromEndpoint();
        TTarget secondVertex = first(edgeToConnectingNeighbour.getToEndpoint().getOutputs()).getToEndpoint();

        callback.onDeleteNeighbour(edgeToConnectingNeighbour.getToEndpoint());

        firstVertex.removeOutput(edgeToConnectingNeighbour);
        secondVertex.removeInput(first(edgeToConnectingNeighbour.getToEndpoint().getOutputs()));

        secondVertex.getInputs().forEach(e -> e.setToEndpoint(firstVertex));
        secondVertex.getOutputs().forEach(e -> e.setFromEndpoint(firstVertex));
        secondVertex.getInputs().forEach(firstVertex::addInput);
        secondVertex.getOutputs().forEach(firstVertex::addOutput);

        callback.onDeleteTarget(secondVertex);
    }

    protected abstract boolean check(TTarget target);

    protected abstract boolean checkMergedVertex(TTarget vertex);

    protected abstract boolean checkNeighbour(TNeighbour neighbour);
}
