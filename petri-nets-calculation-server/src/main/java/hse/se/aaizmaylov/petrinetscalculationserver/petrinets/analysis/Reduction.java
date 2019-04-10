package hse.se.aaizmaylov.petrinetscalculationserver.petrinets.analysis;

@FunctionalInterface
public interface Reduction<TElement> {

    //TODO: Add callback

    boolean reduceFrom(TElement element);
}
