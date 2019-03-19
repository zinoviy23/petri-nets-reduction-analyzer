package hse.se.aaizmaylov.petrinetscalculationserver.petrinets;

import lombok.NonNull;

import java.util.*;

public abstract class AbstractPetriNetVertexImpl<
        TTokenContainer,
        TSelf extends PetriNetVertex<TTokenContainer, TSelf, TNeighbours, TInput, TOutput>,
        TNeighbours extends PetriNetVertex<TTokenContainer, TNeighbours, TSelf, TOutput, TInput>,
        TInput extends Edge<TTokenContainer, TNeighbours, TSelf>,
        TOutput extends Edge<TTokenContainer, TSelf, TNeighbours>>
        implements PetriNetVertex<TTokenContainer, TSelf, TNeighbours, TInput, TOutput> {

    private Set<TInput> inputs = new LinkedHashSet<>();
    private Set<TOutput> outputs = new LinkedHashSet<>();

    @Override
    public Set<TInput> getInputs() {
        return Collections.unmodifiableSet(inputs);
    }

    @Override
    public Set<TOutput> getOutputs() {
        return Collections.unmodifiableSet(outputs);
    }

    @Override
    public boolean addOutput(@NonNull TOutput outputEdge) {
        boolean insertionResult = outputs.add(outputEdge);

        if (insertionResult) {
            outputEdge.getOutput().addInput(outputEdge);
        }

        return insertionResult;
    }

    @Override
    public boolean removeOutput(@NonNull TOutput outputEdge) {
        boolean insertionResult = outputs.remove(outputEdge);

        if (insertionResult) {
            outputEdge.getOutput().removeInput(outputEdge);
        }

        return insertionResult;
    }

    @Override
    public boolean removeInput(@NonNull TInput inputEdge) {
        boolean insertionResult = inputs.remove(inputEdge);

        if (insertionResult) {
            inputEdge.getInput().removeOutput(inputEdge);
        }

        return insertionResult;
    }

    @Override
    public boolean addInput(@NonNull TInput inputEdge) {
        boolean insertionResult = inputs.add(inputEdge);

        if (insertionResult) {
            inputEdge.getInput().addOutput(inputEdge);
        }

        return insertionResult;
    }
}
