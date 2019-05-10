package hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.LabeledVertex;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface Reduction<TElement, TNeighbour> {
    boolean reduceFrom(@NotNull TElement element,
                       @NotNull TransformCallback<TElement, TNeighbour> callback,
                       @NotNull ReductionHistory reductionHistory);
}
