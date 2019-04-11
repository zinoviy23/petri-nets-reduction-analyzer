package hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.Edge;
import hse.se.aaizmaylov.petrinetslibrary.petrinets.PetriNetVertex;
import lombok.NonNull;

import static hse.se.aaizmaylov.petrinetslibrary.utils.CollectionsUtils.first;

public abstract class AbstractFusionOfSelfLoop<
        TTokenContainer,
        TTarget extends PetriNetVertex<TTokenContainer, TTarget, TNeighbour, Edge<TTokenContainer, TNeighbour, TTarget>,
                Edge<TTokenContainer, TTarget, TNeighbour>>,
        TNeighbour extends PetriNetVertex<TTokenContainer, TNeighbour, TTarget, Edge<TTokenContainer, TTarget, TNeighbour>,
                Edge<TTokenContainer, TNeighbour, TTarget>>>
        implements Reduction<TTarget> {

    //TODO: call from neighbour

    @Override
    public boolean reduceFrom(@NonNull TTarget target) {
        if (!check(target))
            return false;

        if (target.getInputs().size() != 1 || target.getOutputs().size() != 1)
            return false;

        Edge<TTokenContainer, TNeighbour, TTarget> edgeFromNeighbour = first(target.getInputs());
        Edge<TTokenContainer, TTarget, TNeighbour> edgeToNeighbour = first(target.getOutputs());

        if (edgeFromNeighbour.getFromEndpoint() != edgeToNeighbour.getToEndpoint())
            return false;

        target.removeInput(edgeFromNeighbour);
        target.removeOutput(edgeToNeighbour);

        return true;
    }

    protected abstract boolean check(TTarget vertex);
}
