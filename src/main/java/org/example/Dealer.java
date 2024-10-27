package org.example;

public interface Dealer {

    Board dealCardsToPlayers();
    Board dealFlop(Board board);
    Board dealTurn(Board board);
    Board dealRiver(Board board);

}
