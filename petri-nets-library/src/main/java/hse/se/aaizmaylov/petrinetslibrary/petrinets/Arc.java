package hse.se.aaizmaylov.petrinetslibrary.petrinets;

import org.jetbrains.annotations.NotNull;

public interface Arc<TTokenContainer, TWeight, Input, Output> extends Cloneable {
    @NotNull
    Input getFromEndpoint();

    @NotNull
    Output getToEndpoint();

    @NotNull
    TWeight weight();

    void setFromEndpoint(Input from);

    void setToEndpoint(Output to);

    void getTokensFrom(TTokenContainer tokens);

    void putTokensTo(TTokenContainer tokens);

    Arc<TTokenContainer, TWeight, Input, Output> clone();

    default Arc<TTokenContainer, TWeight, Input, Output> withChangedInput(@NotNull Input newInput) {
        Arc<TTokenContainer, TWeight, Input, Output> clone = clone();

        clone.setFromEndpoint(newInput);

        return clone;
    }

    default Arc<TTokenContainer, TWeight, Input, Output> withChangedOutput(@NotNull Output newOutput) {
        Arc<TTokenContainer, TWeight, Input, Output> clone = clone();

        clone.setToEndpoint(newOutput);

        return clone;
    }
}
