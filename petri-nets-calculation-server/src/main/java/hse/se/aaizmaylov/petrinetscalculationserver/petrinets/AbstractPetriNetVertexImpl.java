package hse.se.aaizmaylov.petrinetscalculationserver.petrinets;

import java.util.*;

/**
 * Implementation for PetriNetVertex
 * @param <TSelf> type of current vertex. (Must be parent of class, which extends {@link AbstractPetriNetVertexImpl})
 * @param <T> type of neighbours
 */
abstract class AbstractPetriNetVertexImpl<TSelf extends PetriNetVertex<TSelf, T>, T extends PetriNetVertex<T, TSelf>>
        implements PetriNetVertex<TSelf, T> {

    private Set<T> inputs = new LinkedHashSet<>();
    private Set<T> outputs = new LinkedHashSet<>();

    @Override
    public Set<T> getInputs() {
        return Collections.unmodifiableSet(inputs);
    }

    @Override
    public Set<T> getOutputs() {
        return Collections.unmodifiableSet(outputs);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean removeOutput(T output) {
        boolean result = outputs.remove(output);

        if (result)
            output.removeInput((TSelf) this);

        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean addOutput(T output) {
        boolean result = outputs.add(output);

        if (result)
            output.addInput((TSelf) this);

        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean removeInput(T input) {
        boolean result = inputs.remove(input);

        if (result)
            input.removeOutput((TSelf) this);

        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean addInput(T input) {
        boolean result = inputs.add(input);

        if (result)
            input.addOutput((TSelf) this);

        return result;
    }
}
