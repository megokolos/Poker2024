package org.example;

import java.util.List;

public class CheckSomeExceptions {

    public static void noCardsExceptionCheck (Board board) {
        if (board.getPlayerOne() == null || board.getPlayerTwo() == null || board.getFlop() == null ||
                board.getTurn() == null || board.getRiver() == null) {
            throw new InvalidPokerBoardException(
                    "- А карты где?");
        }
    }

    public static void sameCardsExceptionCheck (List<Card> allCardsInGame){
        for (int i = 0; i < allCardsInGame.size() - 1; i++) {
            for (int j = i + 1; j < allCardsInGame.size(); j++) {
                if (allCardsInGame.get(i).equals(allCardsInGame.get(j)))
                    throw new InvalidPokerBoardException(
                            "- Почему карты одинаковые в колоде?");
            }
        }
    }
}
