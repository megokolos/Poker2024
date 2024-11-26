package org.example;


import java.util.*;

import static org.example.CombinationsCheck.*;

/**
 * Пример плохого дилера - он раздает повторяющиеся карты и всегда определяет исход как ничью
 */
public class DealerExample implements Dealer {


    private final Deck deck;

    public DealerExample() {
        this.deck = new Deck();
    }


        Card player1FirstCard;
        Card player1SecondCard;
        Card player2FirstCard;
        Card player2SecondCard;
        Card flop1;
        Card flop2;
        Card flop3;
        Card turn;
        Card river;


    @Override
    public Board dealCardsToPlayers() {
        player1FirstCard = deck.getDeck().get((int) (Math.random()*52));
        deck.drawCard(player1FirstCard);
        player1SecondCard = deck.getDeck().get((int) (Math.random()*51));
        deck.drawCard(player1SecondCard);

        player2FirstCard = deck.getDeck().get((int) (Math.random()*50));
        deck.drawCard(player2FirstCard);
        player2SecondCard = deck.getDeck().get((int) (Math.random()*49));
        deck.drawCard(player2SecondCard);

        String player1 = player1FirstCard.toString()+player1SecondCard.toString();
        String player2 = player2FirstCard.toString()+player2SecondCard.toString();


        return new Board(player1, player2, null, null, null);
    }

    @Override
    public Board dealFlop(Board board) {

        flop1 = deck.getDeck().get((int) (Math.random()*48));
        deck.drawCard(flop1);
        flop2 = deck.getDeck().get((int) (Math.random()*47));
        deck.drawCard(flop2);
        flop3 = deck.getDeck().get((int) (Math.random()*46));
        deck.drawCard(flop3);

        return new Board(board.getPlayerOne(),
                board.getPlayerTwo(),
                flop1.toString()
                        + flop2.toString()
                        + flop3.toString(), null, null);
    }

    @Override
    public Board dealTurn(Board board) {

        turn = deck.getDeck().get((int) (Math.random()*45));
        deck.drawCard(turn);

        return new Board(board.getPlayerOne(),
                board.getPlayerTwo(),
                board.getFlop(), turn.toString(), null);
    }

    @Override
    public Board dealRiver(Board board) {

        river = deck.getDeck().get((int) (Math.random()*44));
        deck.drawCard(river);

        return new Board(board.getPlayerOne(),
                board.getPlayerTwo(),
                board.getFlop(), board.getTurn(),
                river.toString());
    }

    @Override
    public PokerResult decideWinner(Board board) {



        if (board.getPlayerOne() == null || board.getPlayerTwo() == null || board.getFlop() == null ||
                board.getTurn() == null || board.getRiver() == null) {
            throw new InvalidPokerBoardException(
                    "- А карты где?");
        }








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


        for (int i = 0; i < allCardsInGame.size() - 1; i++) {
            for (int j = i + 1; j < allCardsInGame.size(); j++) {
                if (allCardsInGame.get(i).equals(allCardsInGame.get(j)))
                    throw new InvalidPokerBoardException(
                            "- Почему карты одинаковые в колоде?");
            }
        }


        List<Card> player1AndTable = new ArrayList<>();
        List<Card> player2AndTable = new ArrayList<>();

        player1AndTable.add(player1FirstCard);
        player1AndTable.add(player1SecondCard);
        player1AndTable.add(flop1);
        player1AndTable.add(flop2);
        player1AndTable.add(flop3);
        player1AndTable.add(turn);
        player1AndTable.add(river);

        player2AndTable.add(player2FirstCard);
        player2AndTable.add(player2SecondCard);
        player2AndTable.add(flop1);
        player2AndTable.add(flop2);
        player2AndTable.add(flop3);
        player2AndTable.add(turn);
        player2AndTable.add(river);





        if (checkFlashRoyal(player1AndTable) && checkFlashRoyal(player2AndTable)) {
            return PokerResult.DRAW;
        } else if (checkFlashRoyal(player1AndTable)) {
            return PokerResult.PLAYER_ONE_WIN;
        } else if (checkFlashRoyal(player2AndTable)) {
            return PokerResult.PLAYER_TWO_WIN;
        } else if (checkStreetFlash(player1AndTable) != 0 &&
                checkStreetFlash(player2AndTable) != 0 &&
                checkStreetFlash(player1AndTable) == checkStreetFlash(player2AndTable)) {
            return PokerResult.DRAW;
        } else if (checkStreetFlash(player1AndTable) != 0 &&
                checkStreetFlash(player2AndTable) == 0) {
            return PokerResult.PLAYER_ONE_WIN;
        } else if (checkStreetFlash(player1AndTable) == 0 &&
                checkStreetFlash(player2AndTable) != 0) {
            return PokerResult.PLAYER_TWO_WIN;
        } else if (checkFourOfAKind(player1AndTable) != 0 &&
                checkFourOfAKind(player2AndTable) != 0 &&
                checkFourOfAKind(player1AndTable) == checkFourOfAKind(player2AndTable)) {


            int kiker1 = determineKicker(player1AndTable, checkFourOfAKind(player1AndTable));
            int kiker2 = determineKicker(player2AndTable, checkFourOfAKind(player2AndTable));

            if (kiker1 == kiker2)
                return PokerResult.DRAW;
            else if (kiker1 > kiker2)
                return PokerResult.PLAYER_ONE_WIN;
            else
                return PokerResult.PLAYER_TWO_WIN;
        } else if (checkFourOfAKind(player1AndTable) != 0 && checkFourOfAKind(player2AndTable) == 0) {
            return PokerResult.PLAYER_ONE_WIN;
        } else if (checkFourOfAKind(player1AndTable) == 0 && checkFourOfAKind(player2AndTable) != 0) {
            return PokerResult.PLAYER_TWO_WIN;
        } else if (checkFullHouse(player1AndTable) == checkFullHouse(player2AndTable) && checkFullHouse(player1AndTable) != 0 && checkFullHouse(player2AndTable) != 0) {
            return PokerResult.DRAW;
        } else if (checkFullHouse(player1AndTable) > checkFullHouse(player2AndTable)) {
            return PokerResult.PLAYER_ONE_WIN;
        } else if (checkFullHouse(player1AndTable) < checkFullHouse(player2AndTable)) {
            return PokerResult.PLAYER_TWO_WIN;
        } else if (checkFlash(player1AndTable).length == checkFlash(player2AndTable).length &&
                checkFlash(player1AndTable).length != 0 && checkFlash(player2AndTable).length != 0) {

            for (int counter = 0; counter < 5; counter++) {
                if (checkFlash(player1AndTable)[checkFlash(player1AndTable).length - 1 - counter] > checkFlash(player2AndTable)[checkFlash(player2AndTable).length - 1 - counter])
                    return PokerResult.PLAYER_ONE_WIN;
                if (checkFlash(player1AndTable)[checkFlash(player1AndTable).length - 1 - counter] < checkFlash(player2AndTable)[checkFlash(player2AndTable).length - 1 - counter])
                    return PokerResult.PLAYER_TWO_WIN;
            }
            return PokerResult.DRAW;
        } else if (checkFlash(player1AndTable).length != 0 &&
                checkFlash(player2AndTable).length == 0) {
            return PokerResult.PLAYER_ONE_WIN;
        } else if (checkFlash(player1AndTable).length == 0 &&
                checkFlash(player2AndTable).length != 0) {
            return PokerResult.PLAYER_TWO_WIN;
        } else if (checkStreet(player1AndTable) == checkStreet(player2AndTable) &&
                checkStreet(player1AndTable) != 0 && checkStreet(player2AndTable) != 0) {
            return PokerResult.DRAW;
        } else if (checkStreet(player1AndTable) > checkStreet(player2AndTable)) {
            return PokerResult.PLAYER_ONE_WIN;
        } else if (checkStreet(player1AndTable) < checkStreet(player2AndTable)) {
            return PokerResult.PLAYER_TWO_WIN;
        } else if (checkSet(player1AndTable) == checkSet(player2AndTable) &&
                checkSet(player1AndTable) != 0 && checkSet(player2AndTable) != 0) {

            int kiker1 = determineKicker(player1AndTable, checkSet(player1AndTable));
            int kiker2 = determineKicker(player2AndTable, checkSet(player2AndTable));

            if (kiker1 == kiker2) {

                if (determineKicker(removeKicker(player1AndTable, kiker1, player2AndTable, kiker2).get(0), checkSet(player1AndTable)) == determineKicker(removeKicker(player1AndTable, kiker1, player2AndTable, kiker2).get(1), checkSet(player2AndTable)))
                    return PokerResult.DRAW;
                else if (determineKicker(removeKicker(player1AndTable, kiker1, player2AndTable, kiker2).get(0), checkSet(player1AndTable)) > determineKicker(removeKicker(player1AndTable, kiker1, player2AndTable, kiker2).get(1), checkSet(player2AndTable))) {
                    return PokerResult.PLAYER_ONE_WIN;
                } else
                    return PokerResult.PLAYER_TWO_WIN;


            } else if (kiker1 > kiker2) {
                return PokerResult.PLAYER_ONE_WIN;
            } else {
                return PokerResult.PLAYER_TWO_WIN;
            }

        } else if (checkSet(player1AndTable) > checkSet(player2AndTable)) {
            return PokerResult.PLAYER_ONE_WIN;
        } else if (checkSet(player1AndTable) < checkSet(player2AndTable)) {
            return PokerResult.PLAYER_TWO_WIN;
        } else if (checkTwoPairs(player1AndTable)[0] > checkTwoPairs(player2AndTable)[0] &&
                checkTwoPairs(player1AndTable)[0] != 0 && checkTwoPairs(player2AndTable)[0] != 0 &&
                checkTwoPairs(player1AndTable)[1] != 0 && checkTwoPairs(player2AndTable)[1] != 0) {
            return PokerResult.PLAYER_ONE_WIN;
        } else if (checkTwoPairs(player1AndTable)[0] < checkTwoPairs(player2AndTable)[0] &&
                checkTwoPairs(player1AndTable)[0] != 0 && checkTwoPairs(player2AndTable)[0] != 0 &&
                checkTwoPairs(player1AndTable)[1] != 0 && checkTwoPairs(player2AndTable)[1] != 0) {
            return PokerResult.PLAYER_TWO_WIN;
        } else if (checkTwoPairs(player1AndTable)[0] == checkTwoPairs(player2AndTable)[0] &&
                checkTwoPairs(player1AndTable)[1] == checkTwoPairs(player2AndTable)[1] &&
                checkTwoPairs(player1AndTable)[0] != 0 && checkTwoPairs(player2AndTable)[0] != 0 &&
                checkTwoPairs(player1AndTable)[1] != 0 && checkTwoPairs(player2AndTable)[1] != 0) {


            int[] withoutPairs1 = removeTwoPairs(player1AndTable);

            int[] withoutPairs2 = removeTwoPairs(player2AndTable);

            Arrays.sort(withoutPairs1);
            Arrays.sort(withoutPairs2);


            if (withoutPairs1[withoutPairs1.length - 1] == withoutPairs2[withoutPairs2.length - 1])
                return PokerResult.DRAW;
            else if (withoutPairs1[withoutPairs1.length - 1] > withoutPairs2[withoutPairs2.length - 1])
                return PokerResult.PLAYER_ONE_WIN;
            else
                return PokerResult.PLAYER_TWO_WIN;
        } else if (checkTwoPairs(player1AndTable)[0] == checkTwoPairs(player2AndTable)[0] &&
                checkTwoPairs(player1AndTable)[1] > checkTwoPairs(player2AndTable)[1] &&
                checkTwoPairs(player1AndTable)[0] != 0 && checkTwoPairs(player2AndTable)[0] != 0 &&
                checkTwoPairs(player1AndTable)[1] != 0 && checkTwoPairs(player2AndTable)[1] != 0) {
            return PokerResult.PLAYER_ONE_WIN;
        } else if (checkTwoPairs(player1AndTable)[0] == checkTwoPairs(player2AndTable)[0] &&
                checkTwoPairs(player1AndTable)[1] < checkTwoPairs(player2AndTable)[1] &&
                checkTwoPairs(player1AndTable)[0] != 0 && checkTwoPairs(player2AndTable)[0] != 0 &&
                checkTwoPairs(player1AndTable)[1] != 0 && checkTwoPairs(player2AndTable)[1] != 0) {
            return PokerResult.PLAYER_TWO_WIN;
        } else if ((checkTwoPairs(player1AndTable)[0] == 0 || checkTwoPairs(player2AndTable)[1] == 0) &&
                checkTwoPairs(player1AndTable)[0] != 0 && checkTwoPairs(player1AndTable)[1] != 0) {
            return PokerResult.PLAYER_ONE_WIN;
        } else if ((checkTwoPairs(player1AndTable)[0] == 0 || checkTwoPairs(player1AndTable)[1] == 0) &&
                checkTwoPairs(player2AndTable)[0] != 0 && checkTwoPairs(player2AndTable)[1] != 0) {
            return PokerResult.PLAYER_TWO_WIN;
        } else if (checkPair(player1AndTable) == checkPair(player2AndTable) &&
                checkPair(player1AndTable) != 0 && checkPair(player2AndTable) != 0) {


            int kiker1 = determineKicker(player1AndTable, checkPair(player1AndTable));
            int kiker2 = determineKicker(player2AndTable, checkPair(player2AndTable));

            if (kiker1 == kiker2) {

                List<Card> withoutkiker1 = removeKicker(player1AndTable, kiker1, player2AndTable, kiker2).get(0);

                List<Card> withoutkiker2 = removeKicker(player1AndTable, kiker1, player2AndTable, kiker2).get(1);

                if (determineKicker(withoutkiker1, checkPair(player1AndTable)) == determineKicker(withoutkiker2, checkPair(player2AndTable))) {


                    int kiker21 = determineKicker(withoutkiker1, checkPair(player1AndTable));
                    int kiker22 = determineKicker(withoutkiker2, checkPair(player2AndTable));


                    if (kiker21 == kiker22) {


                        List<Card> withoutkiker21 = removeKicker(withoutkiker1, kiker21, withoutkiker2, kiker22).get(0);

                        List<Card> withoutkiker22 = removeKicker(withoutkiker1, kiker21, withoutkiker2, kiker22).get(1);

                        if (determineKicker(withoutkiker21, checkPair(player1AndTable)) == determineKicker(withoutkiker22, checkPair(player2AndTable))) {
                            return PokerResult.DRAW;
                        } else if (determineKicker(withoutkiker21, checkPair(player1AndTable)) > determineKicker(withoutkiker22, checkPair(player2AndTable)))
                            return PokerResult.PLAYER_ONE_WIN;
                        else
                            return PokerResult.PLAYER_TWO_WIN;

                    }
                    if (kiker21 > kiker22)
                        return PokerResult.PLAYER_ONE_WIN;
                    else
                        return PokerResult.PLAYER_TWO_WIN;


                } else if (determineKicker(withoutkiker1, checkPair(player1AndTable)) > determineKicker(withoutkiker2, checkPair(player2AndTable))) {
                    return PokerResult.PLAYER_ONE_WIN;
                } else
                    return PokerResult.PLAYER_TWO_WIN;


            } else if (kiker1 > kiker2) {
                return PokerResult.PLAYER_ONE_WIN;
            } else
                return PokerResult.PLAYER_TWO_WIN;

        } else if (checkPair(player1AndTable) > checkPair(player2AndTable)) {
            return PokerResult.PLAYER_ONE_WIN;
        } else if (checkPair(player1AndTable) < checkPair(player2AndTable)) {
            return PokerResult.PLAYER_TWO_WIN;
        } else {
            int[] sortedarray1 = checkElderRank(player1AndTable);
            int[] sortedarray2 = checkElderRank(player2AndTable);

            for (int i = sortedarray1.length - 1; i > 1; i--) {
                if (sortedarray1[i] == sortedarray2[i])
                    continue;
                else if (sortedarray1[i] > sortedarray2[i])
                    return PokerResult.PLAYER_ONE_WIN;
                else
                    return PokerResult.PLAYER_TWO_WIN;
            }

        }

        return PokerResult.DRAW;
    }
}

