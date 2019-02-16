package hse.se.aaizmaylov.petrinetscalculationserver.petrinets;

import java.util.Set;

@SuppressWarnings({"UnusedReturnValue", "unused"})
public interface PetriNetVertex<TSelf extends PetriNetVertex<TSelf, T>, T extends PetriNetVertex<T, TSelf>> {
    Set<T> getInputs();

    Set<T> getOutputs();

    boolean removeOutput(T output);

    boolean addOutput(T output);

    boolean removeInput(T input);

    boolean addInput(T input);
}
