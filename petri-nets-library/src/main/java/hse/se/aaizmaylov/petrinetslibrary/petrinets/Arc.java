package hse.se.aaizmaylov.petrinetslibrary.petrinets;

public interface Arc<TTokenContainer, TWeight, Input, Output> {
    Input getFromEndpoint();

    Output getToEndpoint();

    TWeight weight();

    void setFromEndpoint(Input from);

    void setToEndpoint(Output to);

    void getTokensFrom(TTokenContainer tokens);

    void putTokensTo(TTokenContainer tokens);
}
