package hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis;

import org.jetbrains.annotations.NotNull;

public interface InitializedReduction<TData, TTarget, TNeighbour> extends Reduction<TTarget, TNeighbour> {
    void initialize(@NotNull TData net);
}
