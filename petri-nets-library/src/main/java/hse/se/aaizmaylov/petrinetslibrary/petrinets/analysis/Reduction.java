package hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis;

@FunctionalInterface
public interface Reduction<TElement, TNeighbour> {

    //TODO: Add callback

    boolean reduceFrom(TElement element, DeleteVertexCallback<TElement, TNeighbour> callback);
}
