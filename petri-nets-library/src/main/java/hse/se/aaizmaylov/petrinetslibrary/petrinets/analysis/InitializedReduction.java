package hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis;

import org.jetbrains.annotations.NotNull;

public interface InitializedReduction<TNet, TTarget, TNeighbour> extends Reduction<TTarget, TNeighbour> {
    void initialize(@NotNull TNet net);
}
