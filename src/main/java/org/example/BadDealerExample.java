package org.example;


/**
 * Пример плохого дилера - он раздает повторяющиеся карты и всегда определяет исход как ничью
 */
public class BadDealerExample implements Dealer {


    private Deck deck;

    public BadDealerExample(Deck deck) {
        this.deck=deck;
    }

    @Override
    public Board dealCardsToPlayers() {




        return new Board(deck.getDeck()[(int) (Math.random() * 52)]+deck.getDeck()[(int) (Math.random() * 52)],
                deck.getDeck()[(int) (Math.random() * 52)]+deck.getDeck()[(int) (Math.random() * 52)],
                null, null, null);
    }

    @Override
    public Board dealFlop(Board board) {

        return new Board(board.getPlayerOne(),
                board.getPlayerTwo(),
                deck.getDeck()[(int) (Math.random() * 52)]
                        + deck.getDeck()[(int) (Math.random() * 52)]
                        + deck.getDeck()[(int) (Math.random() * 52)], null, null);
    }

    @Override
    public Board dealTurn(Board board) {

        return new Board(board.getPlayerOne(),
                board.getPlayerTwo(),
                board.getFlop(), deck.getDeck()[(int) (Math.random() * 52)], null);
    }

    @Override
    public Board dealRiver(Board board) {

        return new Board(board.getPlayerOne(),
                board.getPlayerTwo(),
                board.getFlop(), board.getTurn(),
                deck.getDeck()[(int) (Math.random() * 52)]);
    }

    @Override
    public PokerResult decideWinner(Board board) {

        return PokerResult.DRAW;



    }
}
