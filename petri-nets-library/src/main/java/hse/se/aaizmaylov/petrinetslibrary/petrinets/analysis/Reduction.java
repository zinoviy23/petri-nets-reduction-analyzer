package hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface Reduction<TElement, TNeighbour> {
    boolean reduceFrom(@NotNull TElement element, @NotNull TransformCallback<TElement, TNeighbour> callback);
}
