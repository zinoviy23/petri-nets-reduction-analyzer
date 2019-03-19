package hse.se.aaizmaylov.petrinetscalculationserver.petrinets;

public interface Edge<TTokenContainer, Input, Output> {
    Input getInput();

    Output getOutput();

    void getTokensFrom(TTokenContainer tokens);

    void putTokensTo(TTokenContainer tokens);
}
