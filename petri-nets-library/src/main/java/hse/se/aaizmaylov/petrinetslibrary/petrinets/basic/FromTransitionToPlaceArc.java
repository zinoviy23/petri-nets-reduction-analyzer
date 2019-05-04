package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.Arc;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

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
}
