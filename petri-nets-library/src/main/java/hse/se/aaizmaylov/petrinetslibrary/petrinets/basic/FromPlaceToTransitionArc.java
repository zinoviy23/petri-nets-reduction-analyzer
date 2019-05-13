package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.Arc;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class FromPlaceToTransitionArc implements Arc<Long, Long, Place, Transition> {

    @Getter
    @Setter
    private Place fromEndpoint;

    @Getter
    @Setter
    private Transition toEndpoint;

    private Long weight;

    public FromPlaceToTransitionArc(@NonNull Place fromEndpoint, @NonNull Transition toEndpoint) {
        this(fromEndpoint, toEndpoint, 1);
    }

    public FromPlaceToTransitionArc(@NonNull Place fromEndpoint, @NonNull Transition toEndpoint, long weight) {
        if (weight < 1)
            throw new IllegalArgumentException("Weight cannot be less than 1!");

        this.fromEndpoint = fromEndpoint;
        this.toEndpoint = toEndpoint;
        this.weight = weight;
    }
    

    @Override
    public void getTokensFrom(Long tokens) {
        fromEndpoint.removeMarks(tokens);
    }

    @Override
    public void putTokensTo(Long tokens) {
    }

    @Override
    public FromPlaceToTransitionArc clone() {
        try {
            return (FromPlaceToTransitionArc) super.clone();
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
        if (!(o instanceof FromPlaceToTransitionArc)) return false;
        FromPlaceToTransitionArc that = (FromPlaceToTransitionArc) o;
        return Objects.equals(fromEndpoint, that.fromEndpoint) &&
                Objects.equals(toEndpoint, that.toEndpoint);
    }
}
