package hse.se.aaizmaylov.petrinetscalculationserver.petrinets.analysis;

import hse.se.aaizmaylov.petrinetscalculationserver.petrinets.Edge;
import hse.se.aaizmaylov.petrinetscalculationserver.petrinets.PetriNetVertex;
import lombok.NonNull;

import static hse.se.aaizmaylov.petrinetscalculationserver.utils.CollectionsUtils.first;

public abstract class AbstractFusionOfSelfLoop<
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

        if (target.getInputs().size() != 1 || target.getOutputs().size() != 1)
            return false;

        Edge<TTokenContainer, TNeighbour, TTarget> edgeFromNeighbour = first(target.getInputs());
        Edge<TTokenContainer, TTarget, TNeighbour> edgeToNeighbour = first(target.getOutputs());

        if (edgeFromNeighbour.getFrom() != edgeToNeighbour.getTo())
            return false;

        target.removeInput(edgeFromNeighbour);
        target.removeOutput(edgeToNeighbour);

        return true;
    }

    protected abstract boolean check(TTarget vertex);
}
