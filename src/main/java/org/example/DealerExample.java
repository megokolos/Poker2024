package org.example;


import java.util.*;

import static org.example.Combinations.checkTheInnerDifference;
import static org.example.CombinationsCheck.*;
import static org.example.BoardValidator.*;



/**
 * Пример плохого дилера - он раздает повторяющиеся карты и всегда определяет исход как ничью
 */
public class DealerExample implements Dealer {


    private final Deck deck;

    public DealerExample() {
        this.deck = new Deck();
    }


    private static Card player1FirstCard;
    private static Card player1SecondCard;
    private static Card player2FirstCard;
    private static Card player2SecondCard;
    private static Card flop1;
    private static Card flop2;
    private static Card flop3;
    private static Card turn;
    private static Card river;


    @Override
    public Board dealCardsToPlayers() {
        player1FirstCard = deck.getDeck().get((int) (Math.random() * 52));
        deck.drawCard(player1FirstCard);
        player1SecondCard = deck.getDeck().get((int) (Math.random() * 51));
        deck.drawCard(player1SecondCard);

        player2FirstCard = deck.getDeck().get((int) (Math.random() * 50));
        deck.drawCard(player2FirstCard);
        player2SecondCard = deck.getDeck().get((int) (Math.random() * 49));
        deck.drawCard(player2SecondCard);

        String player1 = player1FirstCard.toString() + player1SecondCard.toString();
        String player2 = player2FirstCard.toString() + player2SecondCard.toString();


        return new Board(player1, player2, null, null, null);
    }

    @Override
    public Board dealFlop(Board board) {

        flop1 = deck.getDeck().get((int) (Math.random() * 48));
        deck.drawCard(flop1);
        flop2 = deck.getDeck().get((int) (Math.random() * 47));
        deck.drawCard(flop2);
        flop3 = deck.getDeck().get((int) (Math.random() * 46));
        deck.drawCard(flop3);

        return new Board(board.getPlayerOne(),
                board.getPlayerTwo(),
                flop1.toString()
                        + flop2.toString()
                        + flop3.toString(), null, null);
    }

    @Override
    public Board dealTurn(Board board) {

        turn = deck.getDeck().get((int) (Math.random() * 45));
        deck.drawCard(turn);

        return new Board(board.getPlayerOne(),
                board.getPlayerTwo(),
                board.getFlop(), turn.toString(), null);
    }

    @Override
    public Board dealRiver(Board board) {

        river = deck.getDeck().get((int) (Math.random() * 44));
        deck.drawCard(river);

        return new Board(board.getPlayerOne(),
                board.getPlayerTwo(),
                board.getFlop(), board.getTurn(),
                river.toString());
    }

    @Override
    public PokerResult decideWinner(Board board) {

        noCardsExceptionCheck(board);

        //Надо закомментить для тестов
//        if(player1FirstCard ==null || player1SecondCard == null || player2FirstCard ==null || player2SecondCard == null ||
//                flop1 == null || flop2 == null || flop3 == null || turn == null || river == null){
        doIfTheBoardISAlreadyReadySetCards(board);
//        }


        List<Card> allCardsInGame = addAllCardsToList();
        sameCardsExceptionCheck(allCardsInGame);


//            System.out.println(player1FirstCard.toString());
//            System.out.println(player1SecondCard.toString());
//            System.out.println(player2FirstCard.toString());
//            System.out.println(player2SecondCard.toString());
//            System.out.println(flop1.toString());
//            System.out.println(flop2.toString());
//            System.out.println(flop3.toString());
//            System.out.println(turn.toString());
//            System.out.println(river.toString());

        System.out.println();
        System.out.println(board.toString());
        System.out.println();


        List<Card> player1AndTable = addAllTOFirstPlayer();
        List<Card> player2AndTable = addAllTOSecondPlayer();

        Combinations firstPlayerCombination = returnCombination(player1AndTable);
        Combinations secondPlayerCombination = returnCombination(player2AndTable);

        if (firstPlayerCombination.value > secondPlayerCombination.value)
            return PokerResult.PLAYER_ONE_WIN;
        else if (firstPlayerCombination.value < secondPlayerCombination.value)
            return PokerResult.PLAYER_TWO_WIN;
        else
            return checkTheInnerDifference(firstPlayerCombination, player1AndTable, player2AndTable);
    }

    public static List<Card> addAllCardsToList() {
        List<Card> allCardsInGame = new ArrayList<>();
        allCardsInGame.add(player1FirstCard);
        allCardsInGame.add(player1SecondCard);
        allCardsInGame.add(player2FirstCard);
        allCardsInGame.add(player2SecondCard);
        allCardsInGame.add(flop1);
        allCardsInGame.add(flop2);
        allCardsInGame.add(flop3);
        allCardsInGame.add(turn);
        allCardsInGame.add(river);

        return allCardsInGame;
    }

    public static List<Card> addAllTOFirstPlayer() {
        List<Card> firstPlayerAndTable = addAllCardsToList();
        firstPlayerAndTable.remove(player2FirstCard);
        firstPlayerAndTable.remove(player2SecondCard);


        return firstPlayerAndTable;
    }

    public static List<Card> addAllTOSecondPlayer() {
        List<Card> secondPlayerAndTable = addAllCardsToList();
        secondPlayerAndTable.remove(player1FirstCard);
        secondPlayerAndTable.remove(player1SecondCard);


        return secondPlayerAndTable;
    }

    public static void doIfTheBoardISAlreadyReadySetCards(Board board) {

        List<Card> allCards = Board.addAllCards(board);
        player1FirstCard = allCards.get(0);
        player1SecondCard = allCards.get(1);
        player2FirstCard = allCards.get(2);
        player2SecondCard = allCards.get(3);
        flop1 = allCards.get(4);
        flop2 = allCards.get(5);
        flop3 = allCards.get(6);
        turn = allCards.get(7);
        river = allCards.get(8);

    }

    public static Combinations returnCombination(List<Card> playerCards) {

        if (checkFlashRoyal(playerCards))
            return Combinations.ROYAL_FLASH;
        else if (checkStreetFlash(playerCards) != 0)
            return Combinations.STREET_FLASH;
        else if (checkFourOfAKind(playerCards) != 0)
            return Combinations.QUAD;
        else if (checkFullHouse(playerCards) != 0)
            return Combinations.FULL_HOUSE;
        else if (checkFlash(playerCards).length != 0)
            return Combinations.FLASH;
        else if (checkStreet(playerCards) != 0)
            return Combinations.STREET;
        else if (checkSet(playerCards) != 0)
            return Combinations.SET;
        else if (checkTwoPairs(playerCards)[0] != 0)
            return Combinations.TWO_PAIRS;
        else if (checkPair(playerCards) != 0)
            return Combinations.PAIR;
        else
            return Combinations.ELDER_CARD;
    }
}





