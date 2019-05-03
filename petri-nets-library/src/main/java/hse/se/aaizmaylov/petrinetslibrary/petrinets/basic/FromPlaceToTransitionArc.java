package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic;

import hse.se.aaizmaylov.petrinetslibrary.petrinets.Arc;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

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
    public Long weight() {
        return weight;
    }
}
