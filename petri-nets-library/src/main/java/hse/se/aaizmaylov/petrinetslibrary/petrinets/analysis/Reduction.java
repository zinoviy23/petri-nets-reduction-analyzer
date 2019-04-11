package hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis;

@FunctionalInterface
public interface Reduction<TElement> {

    //TODO: Add callback

    boolean reduceFrom(TElement element);
}
