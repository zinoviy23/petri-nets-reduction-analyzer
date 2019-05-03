package hse.se.aaizmaylov.petrinetslibrary.petrinets;

import lombok.NonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class AbstractPetriNetVertexImpl<
        TTokenContainer,
        TWeight,
        TSelf extends PetriNetVertex<TTokenContainer, TWeight, TSelf, TNeighbours, TInput, TOutput>,
        TNeighbours extends PetriNetVertex<TTokenContainer, TWeight, TNeighbours, TSelf, TOutput, TInput>,
        TInput extends Arc<TTokenContainer, TWeight, TNeighbours, TSelf>,
        TOutput extends Arc<TTokenContainer, TWeight, TSelf, TNeighbours>>
        implements PetriNetVertex<TTokenContainer, TWeight, TSelf, TNeighbours, TInput, TOutput> {

    //TODO: add check, that added edge contains current vertex, for TC

    private final String label;

    private Set<TInput> inputs = new HashSet<>();
    private Set<TOutput> outputs = new HashSet<>();

    protected AbstractPetriNetVertexImpl(@NonNull String label) {
        this.label = label;
    }

    @Override
    public String label() {
        return label;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractPetriNetVertexImpl)) return false;
        AbstractPetriNetVertexImpl<?, ?, ?, ?, ?, ?> that = (AbstractPetriNetVertexImpl<?, ?, ?, ?, ?, ?>) o;
        return label.equals(that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "label='" + label + '\'' +
                '}';
    }
}
