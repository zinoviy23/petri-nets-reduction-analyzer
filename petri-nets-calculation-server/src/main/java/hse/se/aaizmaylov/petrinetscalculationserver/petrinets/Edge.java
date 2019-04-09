package hse.se.aaizmaylov.petrinetscalculationserver.petrinets;

public interface Edge<TTokenContainer, Input, Output> {
    Input getFrom();

    Output getTo();

    void setFrom(Input from);

    void setTo(Output to);

    void getTokensFrom(TTokenContainer tokens);

    void putTokensTo(TTokenContainer tokens);
}
