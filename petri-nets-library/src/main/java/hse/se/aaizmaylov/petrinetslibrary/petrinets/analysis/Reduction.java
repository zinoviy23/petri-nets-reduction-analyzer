package hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface Reduction<TElement, TNeighbour> {
    boolean reduceFrom(@NotNull TElement element, @NotNull DeleteVertexCallback<TElement, TNeighbour> callback);
}
