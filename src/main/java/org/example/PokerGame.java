package org.example;

import java.util.*;


public class PokerGame {
    public static void main(String[] args) {

        try {


            Deck deck = new Deck();


            Dealer dealer = new DealerExample(deck);
            Board board = dealer.dealCardsToPlayers();
            board = dealer.dealFlop(board);
            board = dealer.dealTurn(board);
            board = dealer.dealRiver(board);

            String firstPlayer1Card = "";
            String firstPlayer2Card = "";
            String secondPlayer1Card = "";
            String secondPlayer2Card = "";
            String flop1 = "";
            String flop2 = "";
            String flop3 = "";
            String turn = board.getTurn();
            String river = board.getRiver();


            firstPlayer1Card = SeparatePlayerCards(board.getPlayerOne(), firstPlayer1Card, firstPlayer2Card)[0];
            firstPlayer2Card = SeparatePlayerCards(board.getPlayerOne(), firstPlayer1Card, firstPlayer2Card)[1];

            secondPlayer1Card = SeparatePlayerCards(board.getPlayerTwo(), secondPlayer1Card, secondPlayer2Card)[0];
            secondPlayer2Card = SeparatePlayerCards(board.getPlayerTwo(), secondPlayer1Card, secondPlayer2Card)[1];


            flop1 = SeparateFlop(board.getFlop(), flop1, flop2, flop3)[0];
            flop2 = SeparateFlop(board.getFlop(), flop1, flop2, flop3)[1];
            flop3 = SeparateFlop(board.getFlop(), flop1, flop2, flop3)[2];


            List<String> allCardsInGame = new ArrayList<>();
            allCardsInGame.add(firstPlayer1Card);
            allCardsInGame.add(firstPlayer2Card);
            allCardsInGame.add(secondPlayer1Card);
            allCardsInGame.add(secondPlayer2Card);
            allCardsInGame.add(flop1);
            allCardsInGame.add(flop2);
            allCardsInGame.add(flop3);
            allCardsInGame.add(turn);
            allCardsInGame.add(river);


//            System.out.println(firstPlayer1Card);
//            System.out.println(firstPlayer2Card);
//            System.out.println(secondPlayer1Card);
//            System.out.println(secondPlayer2Card);
//            System.out.println(flop1);
//            System.out.println(flop2);
//            System.out.println(flop3);
//            System.out.println(board.getTurn());
//            System.out.println(board.getRiver());

            System.out.println();
            System.out.println(board.toString());
            System.out.println();

            for (int i = 0; i < allCardsInGame.size() - 1; i++) {
                for (int j = i + 1; j < allCardsInGame.size(); j++) {
                    if (allCardsInGame.get(i).equals(allCardsInGame.get(j)))
                        throw new InvalidPokerBoardException(
                                "- Почему карты одинаковые в колоде?\n" +
                                        "- Ты кто такой, сука, чтоб это сделать?\n" +
                                        "– Я всегда это делал, когда..\n" +
                                        "– ВЫ ЧЁ, ДЕБИЛЫ? Вы чё, ебанутые, что ли? Действи.. вы в натуре ебанутые? Эта сидит там, чешет колоду, блядь. Этот стоит, грит: \"Я те щас тут тоже раздам\"..\n" +
                                        "– Ну посмотрите..\n" +
                                        "– ЁБ ТВОЮ МАТЬ! У вас дилер есть, чтобы это делать на моих глазах, мудак ёбаный!\n" +
                                        "– Хорошо, будет делать дилер. Раньше это делал всегда я..\n" +
                                        "– ДЕГЕНЕРАТ ЕБУЧИЙ! Вот пока ты это делал, дебил, ебаная сука, БЛЯДЬ, так все и происходило!");
                }
            }


            //Надо реализвать проверку комбинаций, идем от старшей к нижней


            int[] firstPlayerRanks = new int[7];
            String[] firstPlayerSuits = new String[7];


            int[] secondPlayerRanks = new int[7];
            String[] secondPlayerSuits = new String[7];


            //Номиналы карт

            PlayersCardsAutofill(firstPlayer1Card, firstPlayerRanks, 0);
            PlayersCardsAutofill(firstPlayer2Card, firstPlayerRanks, 1);

            PlayersCardsAutofill(secondPlayer1Card, secondPlayerRanks, 0);
            PlayersCardsAutofill(secondPlayer2Card, secondPlayerRanks, 1);

            PlayersCardsAndTableAutofill(flop1, firstPlayerRanks, secondPlayerRanks, 2);
            PlayersCardsAndTableAutofill(flop2, firstPlayerRanks, secondPlayerRanks, 3);
            PlayersCardsAndTableAutofill(flop3, firstPlayerRanks, secondPlayerRanks, 4);
            PlayersCardsAndTableAutofill(board.getTurn(), firstPlayerRanks, secondPlayerRanks, 5);
            PlayersCardsAndTableAutofill(board.getRiver(), firstPlayerRanks, secondPlayerRanks, 6);


            // Масти карт

            SuitsAutofill(firstPlayerSuits, firstPlayer1Card, firstPlayer2Card, flop1, flop2, flop3,
                    turn, river);


            SuitsAutofill(secondPlayerSuits, secondPlayer1Card, secondPlayer2Card, flop1, flop2, flop3,
                    turn, river);


            // Проверка на победу


            System.out.println(dealer.decideWinner(firstPlayerRanks, firstPlayerSuits, secondPlayerRanks, secondPlayerSuits));


        } catch (InvalidPokerBoardException e) {
            System.out.println(e.getMessage());
        }
    }

    static void PlayersCardsAutofill(String card, int[] player1Cards, int position) {
        if (card.startsWith("10"))
            player1Cards[position] = 10;
        else {
            switch (card.substring(0, 1)) {
                case "2":
                    player1Cards[position] = 2;
                    break;
                case "3":
                    player1Cards[position] = 3;
                    break;
                case "4":
                    player1Cards[position] = 4;
                    break;
                case "5":
                    player1Cards[position] = 5;
                    break;
                case "6":
                    player1Cards[position] = 6;
                    break;
                case "7":
                    player1Cards[position] = 7;
                    break;
                case "8":
                    player1Cards[position] = 8;
                    break;
                case "9":
                    player1Cards[position] = 9;
                    break;
                case "J":
                    player1Cards[position] = 11;
                    break;
                case "Q":
                    player1Cards[position] = 12;
                    break;
                case "K":
                    player1Cards[position] = 13;
                    break;
                case "A":
                    player1Cards[position] = 14;
                    break;

            }
        }
    }

    static void PlayersCardsAndTableAutofill(String card, int[] player1Cards, int[] player2Cards, int position) {
        if (card.startsWith("10")) {
            player1Cards[position] = 10;
            player2Cards[position] = 10;
        } else {
            switch (card.substring(0, 1)) {
                case "2": {
                    player1Cards[position] = 2;
                    player2Cards[position] = 2;
                    break;
                }
                case "3": {
                    player1Cards[position] = 3;
                    player2Cards[position] = 3;
                    break;
                }
                case "4": {
                    player1Cards[position] = 4;
                    player2Cards[position] = 4;
                    break;
                }
                case "5": {
                    player1Cards[position] = 5;
                    player2Cards[position] = 5;
                    break;
                }
                case "6": {
                    player1Cards[position] = 6;
                    player2Cards[position] = 6;
                    break;
                }
                case "7": {
                    player1Cards[position] = 7;
                    player2Cards[position] = 7;
                    break;
                }
                case "8": {
                    player1Cards[position] = 8;
                    player2Cards[position] = 8;
                    break;
                }
                case "9": {
                    player1Cards[position] = 9;
                    player2Cards[position] = 9;
                    break;
                }
                case "J": {
                    player1Cards[position] = 11;
                    player2Cards[position] = 11;
                    break;
                }
                case "Q": {
                    player1Cards[position] = 12;
                    player2Cards[position] = 12;
                    break;
                }
                case "K": {
                    player1Cards[position] = 13;
                    player2Cards[position] = 13;
                    break;
                }
                case "A": {
                    player1Cards[position] = 14;
                    player2Cards[position] = 14;
                    break;
                }

            }
        }
    }

    public static void SuitsAutofill(String[] suitsArray, String firstCard, String secondCard, String flop1,
                                     String flop2, String flop3, String turn, String river) {
        suitsArray[0] = firstCard.substring(firstCard.length() - 1);
        suitsArray[1] = secondCard.substring(secondCard.length() - 1);
        suitsArray[2] = flop1.substring(flop1.length() - 1);
        suitsArray[3] = flop2.substring(flop2.length() - 1);
        suitsArray[4] = flop3.substring(flop3.length() - 1);
        suitsArray[5] = turn.substring(turn.length() - 1);
        suitsArray[6] = river.substring(river.length() - 1);
    }

    public static String[] SeparatePlayerCards(String cards, String firstCard, String secondCard) {
        if (cards.startsWith("10")) {
            firstCard = cards.substring(0, 3);
            secondCard = cards.substring(3);
        } else {
            firstCard = cards.substring(0, 2);
            secondCard = cards.substring(2);
        }
        return new String[]{firstCard, secondCard};
    }

    public static String[] SeparateFlop(String flop, String flop1Card, String flop2Card, String flop3Card) {
        if (flop.length() == 6) {
            flop1Card = flop.substring(0, 2);
            flop2Card = flop.substring(2, 4);
            flop3Card = flop.substring(4);
        } else if (flop.length() == 9) {
            flop1Card = flop.substring(0, 3);
            flop2Card = flop.substring(3, 6);
            flop3Card = flop.substring(6);
        } else {
            if (flop.startsWith("10")) {
                flop1Card = flop.substring(0, 3);
                String check2and3 = flop.substring(3);
                if (check2and3.startsWith("10")) {
                    flop2Card = check2and3.substring(0, 3);
                    flop3Card = check2and3.substring(3);
                } else {
                    flop2Card = check2and3.substring(0, 2);
                    flop3Card = check2and3.substring(2);
                }
            } else {
                flop1Card = flop.substring(0, 2);
                String check2and3 = flop.substring(2);
                if (check2and3.startsWith("10")) {
                    flop2Card = check2and3.substring(0, 3);
                    flop3Card = check2and3.substring(3);
                } else {
                    flop2Card = check2and3.substring(0, 2);
                    flop3Card = check2and3.substring(2);
                }
            }


        }
        return new String[]{flop1Card, flop2Card, flop3Card};

    }


}
