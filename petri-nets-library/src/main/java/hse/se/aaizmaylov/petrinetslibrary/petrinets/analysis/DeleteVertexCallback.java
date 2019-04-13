package hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis;

import lombok.NonNull;

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
}
