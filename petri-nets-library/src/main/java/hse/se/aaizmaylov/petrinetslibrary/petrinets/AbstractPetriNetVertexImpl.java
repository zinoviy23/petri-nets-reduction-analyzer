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
    public boolean addOutput(@NonNull TOutput outputArc) {
        if (outputArc.getFromEndpoint() != this)
            throw new ForeignArcException("Try to add output arc, which doesn't go from this. " + outputArc);

        if (outputs.stream().anyMatch(a -> a.equals(outputArc)))
            return false;

        boolean insertionResult = outputs.add(outputArc);

        if (insertionResult) {
            outputArc.getToEndpoint().addInput(outputArc);
        }

        return insertionResult;
    }

    @Override
    public boolean removeOutput(@NonNull TOutput outputArc) {
        if (outputArc.getFromEndpoint() != this)
            throw new ForeignArcException("Try to remove output arc, which doesn't go from this. " + outputArc);

        boolean insertionResult = outputs.remove(outputArc);

        if (insertionResult) {
            outputArc.getToEndpoint().removeInput(outputArc);
        }

        return insertionResult;
    }

    @Override
    public boolean removeInput(@NonNull TInput inputArc) {
        if (inputArc.getToEndpoint() != this)
            throw new ForeignArcException("Try to remove input arc, which doesn't go to this. " + inputArc);

        boolean insertionResult = inputs.remove(inputArc);

        if (insertionResult) {
            inputArc.getFromEndpoint().removeOutput(inputArc);
        }

        return insertionResult;
    }

    @Override
    public boolean addInput(@NonNull TInput inputArc) {
        if (inputArc.getToEndpoint() != this)
            throw new ForeignArcException("Try to add input arc, which doesn't go to this. " + inputArc);

        if (inputs.stream().anyMatch(a -> a.equals(inputArc)))
            return false;

        boolean insertionResult = inputs.add(inputArc);

        if (insertionResult) {
            inputArc.getFromEndpoint().addOutput(inputArc);
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
