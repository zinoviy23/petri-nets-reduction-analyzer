package hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis;

import lombok.NonNull;

import static hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis.EmptyCallback.EMPTY;

public interface DeleteVertexCallback<TTarget, TNeighbour> {
    void onDeleteTarget(TTarget target);

    void onDeleteNeighbour(TNeighbour neighbour);

    static <TTarget, TNeighbour> DeleteVertexCallback<TNeighbour, TTarget> invertedAdapter(
            @NonNull DeleteVertexCallback<TTarget, TNeighbour> callback) {

        return new DeleteVertexCallback<TNeighbour, TTarget>() {
            @Override
            public void onDeleteTarget(TNeighbour neighbour) {
                callback.onDeleteNeighbour(neighbour);
            }

            @Override
            public void onDeleteNeighbour(TTarget target) {
                callback.onDeleteTarget(target);
            }
        };
    }

    @SuppressWarnings("unchecked")
    static <TTarget, TNeighbour> DeleteVertexCallback<TTarget, TNeighbour> empty() {
        return EMPTY;
    }
}
