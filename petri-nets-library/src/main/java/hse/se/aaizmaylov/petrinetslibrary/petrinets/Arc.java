package hse.se.aaizmaylov.petrinetslibrary.petrinets;

public interface Arc<TTokenContainer, Input, Output> {
    Input getFromEndpoint();

    Output getToEndpoint();

    void setFromEndpoint(Input from);

    void setToEndpoint(Output to);

    void getTokensFrom(TTokenContainer tokens);

    void putTokensTo(TTokenContainer tokens);
}
