package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.Arc;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class FromTransitionToPlaceArc implements Arc<Long, Long, Transition, Place> {

    @Getter
    @Setter
    private Transition fromEndpoint;

    @Getter
    @Setter
    private Place toEndpoint;

    private Long weight;

    public FromTransitionToPlaceArc(@NonNull Transition fromEndpoint, @NonNull Place toEndpoint) {
        this(fromEndpoint, toEndpoint, 1);
    }

    public FromTransitionToPlaceArc(@NonNull Transition fromEndpoint, @NonNull Place toEndpoint, long weight) {
        if (weight < 1)
            throw new IllegalArgumentException("Weight cannot be less than 1!");

        this.fromEndpoint = fromEndpoint;
        this.toEndpoint = toEndpoint;
        this.weight = weight;
    }

    @Override
    public void getTokensFrom(Long tokens) {
    }

    @Override
    public void putTokensTo(Long tokens) {
        toEndpoint.addMarks(tokens);
    }

    @Override
    public FromTransitionToPlaceArc clone() {
        try {
            return (FromTransitionToPlaceArc) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        throw new AssertionError("Cannot be here!");
    }

    @NotNull
    @Override
    public Long weight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FromTransitionToPlaceArc)) return false;
        FromTransitionToPlaceArc that = (FromTransitionToPlaceArc) o;
        return Objects.equals(fromEndpoint, that.fromEndpoint) &&
                Objects.equals(toEndpoint, that.toEndpoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromEndpoint, toEndpoint);
    }
}
