package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Покерный стол
 */
public class Board {
    /**
     * Две карты первого игрока
     */
    private final String playerOne;
    /**
     * Две карты второго игрока
     */
    private final String playerTwo;
    /**
     * Три карты, открываются после раздачи карт игрокам
     */
    private final String flop;
    /**
     * Одна карта, открывается после flop
     */
    private final String turn;
    /**
     * Одна карта, открывается после turn
     */
    private final String river;


    public String getPlayerOne() {
        return playerOne;
    }

    public String getPlayerTwo() {
        return playerTwo;
    }

    public String getFlop() {
        return flop;
    }

    public String getTurn() {
        return turn;
    }

    public String getRiver() {
        return river;
    }

    public Board(String playerOne, String playerTwo, String flop, String turn, String river) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.flop = flop;
        this.turn = turn;
        this.river = river;
    }

    @Override
    public String toString() {
        return "Board{" +
                "playerOne=\"" + playerOne + '\"' +
                ", playerTwo=\"" + playerTwo + '\"' +
                ", flop=\"" + flop + '\"' +
                ", turn=\"" + turn + '\"' +
                ", river=\"" + river + '\"' +
                '}';
    }



    public static List<Card> addAllCards(Board board) {
        List<Card> allCards = new ArrayList<>();


        String[] cardStrings = {
                board.getPlayerOne(),
                board.getPlayerTwo(),
                board.getFlop(),
                board.getTurn(),
                board.getRiver()
        };


        for (String cardString : cardStrings) {
            if (cardString != null && !cardString.isEmpty()) {
                allCards.addAll(parseCards(cardString));
            }
        }

        return allCards;
    }


    private static List<Card> parseCards(String cards) {
        List<Card> cardList = new ArrayList<>();
        int index = 0;

        while (index < cards.length()) {
            String rankStr, suit;


            if (cards.charAt(index) == '1' && index + 2 < cards.length() && cards.charAt(index + 1) == '0') {
                rankStr = cards.substring(index, index + 2);
                suit = String.valueOf(cards.charAt(index + 2));
                index += 3;
            } else {

                rankStr = String.valueOf(cards.charAt(index));
                suit = String.valueOf(cards.charAt(index + 1));
                index += 2;
            }

            int rank = parseRank(rankStr);

            cardList.add(new Card(rank, suit));
        }

        return cardList;
    }

    private static int parseRank(String rankStr) {
        switch (rankStr) {
            case "A":
                return 14;
            case "K":
                return 13;
            case "Q":
                return 12;
            case "J":
                return 11;
            default:
                return Integer.parseInt(rankStr);
        }
    }
}
