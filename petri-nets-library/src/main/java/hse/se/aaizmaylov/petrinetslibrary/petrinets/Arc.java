package hse.se.aaizmaylov.petrinetslibrary.petrinets;

import org.jetbrains.annotations.NotNull;

public interface Arc<TTokenContainer, TWeight, Input, Output> {
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
}
