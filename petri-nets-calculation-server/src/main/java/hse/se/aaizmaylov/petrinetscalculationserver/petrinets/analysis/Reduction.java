package hse.se.aaizmaylov.petrinetscalculationserver.petrinets.analysis;

@FunctionalInterface
public interface Reduction<TElement> {

    boolean reduceFrom(TElement element);
}
