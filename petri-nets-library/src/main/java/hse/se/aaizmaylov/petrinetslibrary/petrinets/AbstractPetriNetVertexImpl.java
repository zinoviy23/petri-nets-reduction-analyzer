package hse.se.aaizmaylov.petrinetslibrary.petrinets;

import lombok.NonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractPetriNetVertexImpl<
        TTokenContainer,
        TSelf extends PetriNetVertex<TTokenContainer, TSelf, TNeighbours, TInput, TOutput>,
        TNeighbours extends PetriNetVertex<TTokenContainer, TNeighbours, TSelf, TOutput, TInput>,
        TInput extends Edge<TTokenContainer, TNeighbours, TSelf>,
        TOutput extends Edge<TTokenContainer, TSelf, TNeighbours>>
        implements PetriNetVertex<TTokenContainer, TSelf, TNeighbours, TInput, TOutput> {

    //TODO: add check, that added edge contains current vertex

    private Set<TInput> inputs = new HashSet<>();
    private Set<TOutput> outputs = new HashSet<>();

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
            outputEdge.getToEndpoint().addInput(outputEdge);
        }

        return insertionResult;
    }

    @Override
    public boolean removeOutput(@NonNull TOutput outputEdge) {
        boolean insertionResult = outputs.remove(outputEdge);

        if (insertionResult) {
            outputEdge.getToEndpoint().removeInput(outputEdge);
        }

        return insertionResult;
    }

    @Override
    public boolean removeInput(@NonNull TInput inputEdge) {
        boolean insertionResult = inputs.remove(inputEdge);

        if (insertionResult) {
            inputEdge.getFromEndpoint().removeOutput(inputEdge);
        }

        return insertionResult;
    }

    @Override
    public boolean addInput(@NonNull TInput inputEdge) {
        boolean insertionResult = inputs.add(inputEdge);

        if (insertionResult) {
            inputEdge.getFromEndpoint().addOutput(inputEdge);
        }

        return insertionResult;
    }
}
