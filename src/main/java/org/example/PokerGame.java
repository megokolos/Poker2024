package org.example;

import java.util.*;


public class PokerGame {
    public static void main(String[] args) {

        try {


            Deck deck = new Deck();


            Dealer dealer = new BadDealerExample(deck);
            Board board = dealer.dealCardsToPlayers();
            board = dealer.dealFlop(board);
            board = dealer.dealTurn(board);
            board = dealer.dealRiver(board);

            String firstPlayer1Card;
            String firstPlayer2Card;
            String secondPlayer1Card;
            String secondPlayer2Card;
            String flop1;
            String flop2;
            String flop3;

            if (board.getPlayerOne().startsWith("10")) {
                firstPlayer1Card = board.getPlayerOne().substring(0, 3);
                firstPlayer2Card = board.getPlayerOne().substring(3);
            } else {
                firstPlayer1Card = board.getPlayerOne().substring(0, 2);
                firstPlayer2Card = board.getPlayerOne().substring(2);
            }

            if (board.getPlayerTwo().startsWith("10")) {
                secondPlayer1Card = board.getPlayerTwo().substring(0, 3);
                secondPlayer2Card = board.getPlayerTwo().substring(3);
            } else {
                secondPlayer1Card = board.getPlayerTwo().substring(0, 2);
                secondPlayer2Card = board.getPlayerTwo().substring(2);
            }

            if (board.getFlop().length() == 6) {
                flop1 = board.getFlop().substring(0, 2);
                flop2 = board.getFlop().substring(2, 4);
                flop3 = board.getFlop().substring(4);
            } else if (board.getFlop().length() == 9) {
                flop1 = board.getFlop().substring(0, 3);
                flop2 = board.getFlop().substring(3, 6);
                flop3 = board.getFlop().substring(6);
            } else {
                if (board.getFlop().startsWith("10")) {
                    flop1 = board.getFlop().substring(0, 3);
                    String check2and3 = board.getFlop().substring(3);
                    if (check2and3.startsWith("10")) {
                        flop2 = check2and3.substring(0, 3);
                        flop3 = check2and3.substring(3);
                    } else {
                        flop2 = check2and3.substring(0, 2);
                        flop3 = check2and3.substring(2);
                    }
                } else {
                    flop1 = board.getFlop().substring(0, 2);
                    String check2and3 = board.getFlop().substring(2);
                    if (check2and3.startsWith("10")) {
                        flop2 = check2and3.substring(0, 3);
                        flop3 = check2and3.substring(3);
                    } else {
                        flop2 = check2and3.substring(0, 2);
                        flop3 = check2and3.substring(2);
                    }
                }


            }

            List<String> allCardsInGame = new ArrayList<>();
            allCardsInGame.add(firstPlayer1Card);
            allCardsInGame.add(firstPlayer2Card);
            allCardsInGame.add(secondPlayer1Card);
            allCardsInGame.add(secondPlayer2Card);
            allCardsInGame.add(flop1);
            allCardsInGame.add(flop2);
            allCardsInGame.add(flop3);
            allCardsInGame.add(board.getTurn());
            allCardsInGame.add(board.getRiver());


            System.out.println(firstPlayer1Card);
            System.out.println(firstPlayer2Card);
            System.out.println(secondPlayer1Card);
            System.out.println(secondPlayer2Card);
            System.out.println(flop1);
            System.out.println(flop2);
            System.out.println(flop3);
            System.out.println(board.getTurn());
            System.out.println(board.getRiver());

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


            System.out.println(board.toString());


            //Надо реализвать проверку комбинаций, идем от старшей к нижней


            int[] firstPlayerRanks = new int[7];
            String[] firstPlayerSuits = new String[7];


            int[] secondPlayerRanks = new int[7];
            String[] secondPlayerSuits = new String[7];


            //Номиналы карт

            if (firstPlayer1Card.startsWith("10"))
                firstPlayerRanks[0] = 10;
            else {
                switch (firstPlayer1Card.substring(0, 1)) {
                    case "2":
                        firstPlayerRanks[0] = 2;
                        break;
                    case "3":
                        firstPlayerRanks[0] = 3;
                        break;
                    case "4":
                        firstPlayerRanks[0] = 4;
                        break;
                    case "5":
                        firstPlayerRanks[0] = 5;
                        break;
                    case "6":
                        firstPlayerRanks[0] = 6;
                        break;
                    case "7":
                        firstPlayerRanks[0] = 7;
                        break;
                    case "8":
                        firstPlayerRanks[0] = 8;
                        break;
                    case "9":
                        firstPlayerRanks[0] = 9;
                        break;
                    case "J":
                        firstPlayerRanks[0] = 11;
                        break;
                    case "Q":
                        firstPlayerRanks[0] = 12;
                        break;
                    case "K":
                        firstPlayerRanks[0] = 13;
                        break;
                    case "A":
                        firstPlayerRanks[0] = 14;
                        break;

                }
            }

            if (firstPlayer2Card.startsWith("10"))
                firstPlayerRanks[1] = 10;
            else {
                switch (firstPlayer2Card.substring(0, 1)) {
                    case "2":
                        firstPlayerRanks[1] = 2;
                        break;
                    case "3":
                        firstPlayerRanks[1] = 3;
                        break;
                    case "4":
                        firstPlayerRanks[1] = 4;
                        break;
                    case "5":
                        firstPlayerRanks[1] = 5;
                        break;
                    case "6":
                        firstPlayerRanks[1] = 6;
                        break;
                    case "7":
                        firstPlayerRanks[1] = 7;
                        break;
                    case "8":
                        firstPlayerRanks[1] = 8;
                        break;
                    case "9":
                        firstPlayerRanks[1] = 9;
                        break;
                    case "J":
                        firstPlayerRanks[1] = 11;
                        break;
                    case "Q":
                        firstPlayerRanks[1] = 12;
                        break;
                    case "K":
                        firstPlayerRanks[1] = 13;
                        break;
                    case "A":
                        firstPlayerRanks[1] = 14;
                        break;

                }
            }

            if (secondPlayer1Card.startsWith("10"))
                secondPlayerRanks[0] = 10;
            else {
                switch (secondPlayer1Card.substring(0, 1)) {
                    case "2":
                        secondPlayerRanks[0] = 2;
                        break;
                    case "3":
                        secondPlayerRanks[0] = 3;
                        break;
                    case "4":
                        secondPlayerRanks[0] = 4;
                        break;
                    case "5":
                        secondPlayerRanks[0] = 5;
                        break;
                    case "6":
                        secondPlayerRanks[0] = 6;
                        break;
                    case "7":
                        secondPlayerRanks[0] = 7;
                        break;
                    case "8":
                        secondPlayerRanks[0] = 8;
                        break;
                    case "9":
                        secondPlayerRanks[0] = 9;
                        break;
                    case "J":
                        secondPlayerRanks[0] = 11;
                        break;
                    case "Q":
                        secondPlayerRanks[0] = 12;
                        break;
                    case "K":
                        secondPlayerRanks[0] = 13;
                        break;
                    case "A":
                        secondPlayerRanks[0] = 14;
                        break;

                }
            }

            if (secondPlayer2Card.startsWith("10"))
                secondPlayerRanks[1] = 10;
            else {
                switch (secondPlayer2Card.substring(0, 1)) {
                    case "2":
                        secondPlayerRanks[1] = 2;
                        break;
                    case "3":
                        secondPlayerRanks[1] = 3;
                        break;
                    case "4":
                        secondPlayerRanks[1] = 4;
                        break;
                    case "5":
                        secondPlayerRanks[1] = 5;
                        break;
                    case "6":
                        secondPlayerRanks[1] = 6;
                        break;
                    case "7":
                        secondPlayerRanks[1] = 7;
                        break;
                    case "8":
                        secondPlayerRanks[1] = 8;
                        break;
                    case "9":
                        secondPlayerRanks[1] = 9;
                        break;
                    case "J":
                        secondPlayerRanks[1] = 11;
                        break;
                    case "Q":
                        secondPlayerRanks[1] = 12;
                        break;
                    case "K":
                        secondPlayerRanks[1] = 13;
                        break;
                    case "A":
                        secondPlayerRanks[1] = 14;
                        break;

                }
            }

            if (flop1.startsWith("10")) {
                firstPlayerRanks[2] = 10;
                secondPlayerRanks[2] = 10;
            } else {
                switch (flop1.substring(0, 1)) {
                    case "2": {
                        firstPlayerRanks[2] = 2;
                        secondPlayerRanks[2] = 2;
                        break;
                    }
                    case "3": {
                        firstPlayerRanks[2] = 3;
                        secondPlayerRanks[2] = 3;
                        break;
                    }
                    case "4": {
                        firstPlayerRanks[2] = 4;
                        secondPlayerRanks[2] = 4;
                        break;
                    }
                    case "5": {
                        firstPlayerRanks[2] = 5;
                        secondPlayerRanks[2] = 5;
                        break;
                    }
                    case "6": {
                        firstPlayerRanks[2] = 6;
                        secondPlayerRanks[2] = 6;
                        break;
                    }
                    case "7": {
                        firstPlayerRanks[2] = 7;
                        secondPlayerRanks[2] = 7;
                        break;
                    }
                    case "8": {
                        firstPlayerRanks[2] = 8;
                        secondPlayerRanks[2] = 8;
                        break;
                    }
                    case "9": {
                        firstPlayerRanks[2] = 9;
                        secondPlayerRanks[2] = 9;
                        break;
                    }
                    case "J": {
                        firstPlayerRanks[2] = 11;
                        secondPlayerRanks[2] = 11;
                        break;
                    }
                    case "Q": {
                        firstPlayerRanks[2] = 12;
                        secondPlayerRanks[2] = 12;
                        break;
                    }
                    case "K": {
                        firstPlayerRanks[2] = 13;
                        secondPlayerRanks[2] = 13;
                        break;
                    }
                    case "A": {
                        firstPlayerRanks[2] = 14;
                        secondPlayerRanks[2] = 14;
                        break;
                    }

                }
            }


            if (flop2.startsWith("10")) {
                firstPlayerRanks[3] = 10;
                secondPlayerRanks[3] = 10;
            } else {
                switch (flop2.substring(0, 1)) {
                    case "2": {
                        firstPlayerRanks[3] = 2;
                        secondPlayerRanks[3] = 2;
                        break;
                    }
                    case "3": {
                        firstPlayerRanks[3] = 3;
                        secondPlayerRanks[3] = 3;
                        break;
                    }
                    case "4": {
                        firstPlayerRanks[3] = 4;
                        secondPlayerRanks[3] = 4;
                        break;
                    }
                    case "5": {
                        firstPlayerRanks[3] = 5;
                        secondPlayerRanks[3] = 5;
                        break;
                    }
                    case "6": {
                        firstPlayerRanks[3] = 6;
                        secondPlayerRanks[3] = 6;
                        break;
                    }
                    case "7": {
                        firstPlayerRanks[3] = 7;
                        secondPlayerRanks[3] = 7;
                        break;
                    }
                    case "8": {
                        firstPlayerRanks[3] = 8;
                        secondPlayerRanks[3] = 8;
                        break;
                    }
                    case "9": {
                        firstPlayerRanks[3] = 9;
                        secondPlayerRanks[3] = 9;
                        break;
                    }
                    case "J": {
                        firstPlayerRanks[3] = 11;
                        secondPlayerRanks[3] = 11;
                        break;
                    }
                    case "Q": {
                        firstPlayerRanks[3] = 12;
                        secondPlayerRanks[3] = 12;
                        break;
                    }
                    case "K": {
                        firstPlayerRanks[3] = 13;
                        secondPlayerRanks[3] = 13;
                        break;
                    }
                    case "A": {
                        firstPlayerRanks[3] = 14;
                        secondPlayerRanks[3] = 14;
                        break;
                    }

                }
            }

            if (flop3.startsWith("10")) {
                firstPlayerRanks[4] = 10;
                secondPlayerRanks[4] = 10;
            } else {
                switch (flop3.substring(0, 1)) {
                    case "2": {
                        firstPlayerRanks[4] = 2;
                        secondPlayerRanks[4] = 2;
                        break;
                    }
                    case "3": {
                        firstPlayerRanks[4] = 3;
                        secondPlayerRanks[4] = 3;
                        break;
                    }
                    case "4": {
                        firstPlayerRanks[4] = 4;
                        secondPlayerRanks[4] = 4;
                        break;
                    }
                    case "5": {
                        firstPlayerRanks[4] = 5;
                        secondPlayerRanks[4] = 5;
                        break;
                    }
                    case "6": {
                        firstPlayerRanks[4] = 6;
                        secondPlayerRanks[4] = 6;
                        break;
                    }
                    case "7": {
                        firstPlayerRanks[4] = 7;
                        secondPlayerRanks[4] = 7;
                        break;
                    }
                    case "8": {
                        firstPlayerRanks[4] = 8;
                        secondPlayerRanks[4] = 8;
                        break;
                    }
                    case "9": {
                        firstPlayerRanks[4] = 9;
                        secondPlayerRanks[4] = 9;
                        break;
                    }
                    case "J": {
                        firstPlayerRanks[4] = 11;
                        secondPlayerRanks[4] = 11;
                        break;
                    }
                    case "Q": {
                        firstPlayerRanks[4] = 12;
                        secondPlayerRanks[4] = 12;
                        break;
                    }
                    case "K": {
                        firstPlayerRanks[4] = 13;
                        secondPlayerRanks[4] = 13;
                        break;
                    }
                    case "A": {
                        firstPlayerRanks[4] = 14;
                        secondPlayerRanks[4] = 14;
                        break;
                    }

                }
            }


            if (board.getTurn().startsWith("10")) {
                firstPlayerRanks[5] = 10;
                secondPlayerRanks[5] = 10;
            } else {
                switch (board.getTurn().substring(0, 1)) {
                    case "2": {
                        firstPlayerRanks[5] = 2;
                        secondPlayerRanks[5] = 2;
                        break;
                    }
                    case "3": {
                        firstPlayerRanks[5] = 3;
                        secondPlayerRanks[5] = 3;
                        break;
                    }
                    case "4": {
                        firstPlayerRanks[5] = 4;
                        secondPlayerRanks[5] = 4;
                        break;
                    }
                    case "5": {
                        firstPlayerRanks[5] = 5;
                        secondPlayerRanks[5] = 5;
                        break;
                    }
                    case "6": {
                        firstPlayerRanks[5] = 6;
                        secondPlayerRanks[5] = 6;
                        break;
                    }
                    case "7": {
                        firstPlayerRanks[5] = 7;
                        secondPlayerRanks[5] = 7;
                        break;
                    }
                    case "8": {
                        firstPlayerRanks[5] = 8;
                        secondPlayerRanks[5] = 8;
                        break;
                    }
                    case "9": {
                        firstPlayerRanks[5] = 9;
                        secondPlayerRanks[5] = 9;
                        break;
                    }
                    case "J": {
                        firstPlayerRanks[5] = 11;
                        secondPlayerRanks[5] = 11;
                        break;
                    }
                    case "Q": {
                        firstPlayerRanks[5] = 12;
                        secondPlayerRanks[5] = 12;
                        break;
                    }
                    case "K": {
                        firstPlayerRanks[5] = 13;
                        secondPlayerRanks[5] = 13;
                        break;
                    }
                    case "A": {
                        firstPlayerRanks[5] = 14;
                        secondPlayerRanks[5] = 14;
                        break;
                    }

                }
            }


            if (board.getRiver().startsWith("10")) {
                firstPlayerRanks[6] = 10;
                secondPlayerRanks[6] = 10;
            } else {
                switch (board.getRiver().substring(0, 1)) {
                    case "2": {
                        firstPlayerRanks[6] = 2;
                        secondPlayerRanks[6] = 2;
                        break;
                    }
                    case "3": {
                        firstPlayerRanks[6] = 3;
                        secondPlayerRanks[6] = 3;
                        break;
                    }
                    case "4": {
                        firstPlayerRanks[6] = 4;
                        secondPlayerRanks[6] = 4;
                        break;
                    }
                    case "5": {
                        firstPlayerRanks[6] = 5;
                        secondPlayerRanks[6] = 5;
                        break;
                    }
                    case "6": {
                        firstPlayerRanks[6] = 6;
                        secondPlayerRanks[6] = 6;
                        break;
                    }
                    case "7": {
                        firstPlayerRanks[6] = 7;
                        secondPlayerRanks[6] = 7;
                        break;
                    }
                    case "8": {
                        firstPlayerRanks[6] = 8;
                        secondPlayerRanks[6] = 8;
                        break;
                    }
                    case "9": {
                        firstPlayerRanks[6] = 9;
                        secondPlayerRanks[6] = 9;
                        break;
                    }
                    case "J": {
                        firstPlayerRanks[6] = 11;
                        secondPlayerRanks[6] = 11;
                        break;
                    }
                    case "Q": {
                        firstPlayerRanks[6] = 12;
                        secondPlayerRanks[6] = 12;
                        break;
                    }
                    case "K": {
                        firstPlayerRanks[6] = 13;
                        secondPlayerRanks[6] = 13;
                        break;
                    }
                    case "A": {
                        firstPlayerRanks[6] = 14;
                        secondPlayerRanks[6] = 14;
                        break;
                    }

                }
            }


            // Масти карт


            firstPlayerSuits[0] = firstPlayer1Card.substring(firstPlayer1Card.length() - 1);
            firstPlayerSuits[1] = firstPlayer2Card.substring(firstPlayer2Card.length() - 1);
            firstPlayerSuits[2] = flop1.substring(flop1.length() - 1);
            firstPlayerSuits[3] = flop2.substring(flop2.length() - 1);
            firstPlayerSuits[4] = flop3.substring(flop3.length() - 1);
            firstPlayerSuits[5] = board.getTurn().substring(board.getTurn().length() - 1);
            firstPlayerSuits[6] = board.getRiver().substring(board.getRiver().length() - 1);


            secondPlayerSuits[0] = secondPlayer1Card.substring(secondPlayer1Card.length() - 1);
            secondPlayerSuits[1] = secondPlayer2Card.substring(secondPlayer2Card.length() - 1);
            secondPlayerSuits[2] = flop1.substring(flop1.length() - 1);
            secondPlayerSuits[3] = flop2.substring(flop2.length() - 1);
            secondPlayerSuits[4] = flop3.substring(flop3.length() - 1);
            secondPlayerSuits[5] = board.getTurn().substring(board.getTurn().length() - 1);
            secondPlayerSuits[6] = board.getRiver().substring(board.getRiver().length() - 1);


            // Проверка на победу


            System.out.println(decideWinner(firstPlayerRanks, firstPlayerSuits, secondPlayerRanks, secondPlayerSuits));


        } catch (InvalidPokerBoardException e) {
            System.out.println(e.getMessage());
        }
    }


    //Проверка на кикер не нужна
    public static boolean FlashRoyal(int[] ranks, String[] suits) {

        Map<String, List<Integer>> suitToRanks = new HashMap<>();

        for (int i = 0; i < ranks.length; i++) {
            String suit = suits[i];
            int rank = ranks[i];


            if (rank == 10 || rank == 11 || rank == 12 || rank == 13 || rank == 14) {
                suitToRanks.computeIfAbsent(suit, k -> new ArrayList<>()).add(rank);
            }
        }


        for (List<Integer> suitRanks : suitToRanks.values()) {
            suitRanks.sort(Integer::compareTo);


            if (suitRanks.size() >= 5 && suitRanks.containsAll(Arrays.asList(10, 11, 12, 13, 14))) {
                return true;
            }
        }
        return false;
    }

    //Проверка на кикер не нужна
    public static int StreetFlash(int[] ranks, String[] suits) {

        Map<String, List<Integer>> suitToRanks = new HashMap<>();

        for (int i = 0; i < ranks.length; i++) {
            String suit = suits[i];
            int rank = ranks[i];


            suitToRanks.computeIfAbsent(suit, k -> new ArrayList<>()).add(rank);

        }


        for (List<Integer> suitRanks : suitToRanks.values()) {
            suitRanks.sort(Integer::compareTo);


            if (suitRanks.size() >= 5 && (suitRanks.containsAll(Arrays.asList(10, 11, 12, 13, 14))))
                return 14;

            if (suitRanks.size() >= 5 && (suitRanks.containsAll(Arrays.asList(9, 10, 11, 12, 13))))
                return 13;
            if (suitRanks.size() >= 5 && (suitRanks.containsAll(Arrays.asList(8, 9, 10, 11, 12))))
                return 12;
            if (suitRanks.size() >= 5 && (suitRanks.containsAll(Arrays.asList(7, 8, 9, 10, 11))))
                return 11;
            if (suitRanks.size() >= 5 && (suitRanks.containsAll(Arrays.asList(6, 7, 8, 9, 10))))
                return 10;
            if (suitRanks.size() >= 5 && (suitRanks.containsAll(Arrays.asList(5, 6, 7, 8, 9))))
                return 9;
            if (suitRanks.size() >= 5 && (suitRanks.containsAll(Arrays.asList(4, 5, 6, 7, 8))))
                return 8;
            if (suitRanks.size() >= 5 && (suitRanks.containsAll(Arrays.asList(3, 4, 5, 6, 7))))
                return 7;
            if (suitRanks.size() >= 5 && (suitRanks.containsAll(Arrays.asList(2, 3, 4, 5, 6))))
                return 6;


        }
        return 0;
    }

    //Проверка на кикер нужна
    public static int FourOfAKind(int[] ranks) {

        int[] countRanks = new int[15];

        for (int i = 0; i < ranks.length; i++) {
            countRanks[ranks[i]] += 1;
        }

        for (int i = 0; i < countRanks.length; i++) {
            if (countRanks[i] == 4)
                return i;
        }

        return 0;
    }

    //Проверка на кикер не нужна
    public static int FullHouse(int[] ranks) {

        int[] countRanks = new int[15];
        int response = 0;

        for (int i = 0; i < ranks.length; i++) {
            countRanks[ranks[i]] += 1;
        }
        int two = 0;
        int three = 0;
        for (int i = 0; i < countRanks.length; i++) {
            if (countRanks[i] == 2) {
                two++;
                response += i;
            }

            if (countRanks[i] == 3) {
                three++;
                response += i;
            }
        }
        if (three != 0 && two != 0)
            return response;


        return 0;
    }

    //Проверка на кикер не нужна
    public static int Flash(int[] ranks, String[] suits) {

        Arrays.sort(ranks);

        int[] counter = new int[4];

        for (int i = 0; i < suits.length; i++) {
            if (suits[i] == "C")
                counter[0] += 1;
            else if (suits[i] == "D")
                counter[1] += 1;
            else if (suits[i] == "H")
                counter[2] += 1;
            else
                counter[3] = +1;
        }
        for (int i : counter) {
            if (i == 5)
                return ranks[ranks.length - 1];
        }

        return 0;
    }

    //Проверка на кикер не нужна
    public static int Street(int[] ranks) {

        Arrays.sort(ranks);

        List<Integer> listOfRanks = new ArrayList<>();

        for (int i : ranks) {
            listOfRanks.add(i);
        }


        if (listOfRanks.containsAll(Arrays.asList(10, 11, 12, 13, 14)))
            return 14;

        if (listOfRanks.containsAll(Arrays.asList(9, 10, 11, 12, 13)))
            return 13;
        if (listOfRanks.containsAll(Arrays.asList(8, 9, 10, 11, 12)))
            return 12;
        if (listOfRanks.containsAll(Arrays.asList(7, 8, 9, 10, 11)))
            return 11;
        if (listOfRanks.containsAll(Arrays.asList(6, 7, 8, 9, 10)))
            return 10;
        if (listOfRanks.containsAll(Arrays.asList(5, 6, 7, 8, 9)))
            return 9;
        if (listOfRanks.containsAll(Arrays.asList(4, 5, 6, 7, 8)))
            return 8;
        if (listOfRanks.containsAll(Arrays.asList(3, 4, 5, 6, 7)))
            return 7;
        if (listOfRanks.containsAll(Arrays.asList(2, 3, 4, 5, 6)))
            return 6;

        return 0;
    }

    //Проверка на кикер нужна
    public static int Set(int[] ranks) {


        int[] countRanks = new int[15];

        for (int i = 0; i < ranks.length; i++) {
            countRanks[ranks[i]] += 1;
        }

        for (int i = 0; i < countRanks.length; i++) {
            if (countRanks[i] == 3)
                return i;
        }

        return 0;
    }

    //Проверка на кикер нужна
    public static int TwoPairs(int[] ranks) {

        int response = 0;
        int[] countRanks = new int[15];

        for (int i = 0; i < ranks.length; i++) {
            countRanks[ranks[i]] += 1;
        }

        int counter = 0;

        for (int i = 0; i < countRanks.length; i++) {
            if (countRanks[i] == 2) {
                counter++;
                response += i;
            }

            if (counter >= 2)
                return response;

        }

        return 0;
    }

    //Проверка на кикер нужна
    public static int Pair(int[] ranks) {


        int[] countRanks = new int[15];

        for (int i = 0; i < ranks.length; i++) {
            countRanks[ranks[i]] += 1;
        }

        for (int i = 0; i < countRanks.length; i++) {
            if (countRanks[i] == 2)
                return i;
        }

        return 0;
    }

    // Это и есть кикер
    public static int ElderRank(int[] ranks) {

        Arrays.sort(ranks);
        return ranks[ranks.length - 1];
    }

    //Проверка на кикер
    public static int determineKicker(int[] ranks, int excludedRank) {

        List<Integer> remainingCards = new ArrayList<>();
        for (int rank : ranks) {
            if (rank != excludedRank) {
                remainingCards.add(rank);
            }
        }

        remainingCards.sort(Collections.reverseOrder());

        return remainingCards.isEmpty() ? 0 : remainingCards.get(0);
    }


    public static PokerResult decideWinner(int[] ranks1, String[] suits1, int[] ranks2, String[] suits2) {

        if (FlashRoyal(ranks1, suits1) && FlashRoyal(ranks2, suits2)) {
            return PokerResult.DRAW;
        } else if (FlashRoyal(ranks1, suits1)) {
            return PokerResult.PLAYER_ONE_WIN;
        } else if (FlashRoyal(ranks2, suits2)) {
            return PokerResult.PLAYER_TWO_WIN;
        } else if (StreetFlash(ranks1, suits1) != 0 && StreetFlash(ranks2, suits2) != 0 && StreetFlash(ranks1, suits1) == StreetFlash(ranks2, suits2)) {
            return PokerResult.DRAW;
        } else if (StreetFlash(ranks1, suits1) != 0 && StreetFlash(ranks2, suits2) == 0) {
            return PokerResult.PLAYER_ONE_WIN;
        } else if (StreetFlash(ranks1, suits1) == 0 && StreetFlash(ranks2, suits2) != 0) {
            return PokerResult.PLAYER_TWO_WIN;
        } else if (FourOfAKind(ranks1) != 0 && FourOfAKind(ranks2) != 0 && FourOfAKind(ranks1) == FourOfAKind(ranks2)) {


            int kiker1 = determineKicker(ranks1, FourOfAKind(ranks1));
            int kiker2 = determineKicker(ranks2, FourOfAKind(ranks2));

            if (kiker1 == kiker2)
                return PokerResult.DRAW;
            else if (kiker1 > kiker2)
                return PokerResult.PLAYER_ONE_WIN;
            else
                return PokerResult.PLAYER_TWO_WIN;
        } else if (FourOfAKind(ranks1) != 0 && FourOfAKind(ranks2) == 0) {
            return PokerResult.PLAYER_ONE_WIN;
        } else if (FourOfAKind(ranks1) == 0 && FourOfAKind(ranks2) != 0) {
            return PokerResult.PLAYER_TWO_WIN;
        } else if (FullHouse(ranks1) == FullHouse(ranks2) && FullHouse(ranks1) != 0 && FullHouse(ranks2) != 0) {
            return PokerResult.DRAW;
        } else if (FullHouse(ranks1) > FullHouse(ranks2)) {
            return PokerResult.PLAYER_ONE_WIN;
        } else if (FullHouse(ranks1) < FullHouse(ranks2)) {
            return PokerResult.PLAYER_TWO_WIN;
        } else if (Flash(ranks1, suits1) == Flash(ranks2, suits2) && Flash(ranks1, suits1) != 0 && Flash(ranks2, suits2) != 0) {
            return PokerResult.DRAW;
        } else if (Flash(ranks1, suits1) > Flash(ranks2, suits2)) {
            return PokerResult.PLAYER_ONE_WIN;
        } else if (Flash(ranks1, suits1) < Flash(ranks2, suits2)) {
            return PokerResult.PLAYER_TWO_WIN;
        } else if (Street(ranks1) == Street(ranks2) && Street(ranks1) != 0 && Street(ranks2) != 0) {
            return PokerResult.DRAW;
        } else if (Street(ranks1) > Street(ranks2)) {
            return PokerResult.PLAYER_ONE_WIN;
        } else if (Street(ranks1) < Street(ranks2)) {
            return PokerResult.PLAYER_TWO_WIN;
        } else if (Set(ranks1) == Set(ranks2) && Set(ranks1) != 0 && Set(ranks2) != 0) {

            int kiker1 = determineKicker(ranks1, Set(ranks1));
            int kiker2 = determineKicker(ranks2, Set(ranks2));

            if (kiker1 == kiker2) {

                List<Integer> list1 = new ArrayList<>();
                for(int i=0; i <ranks1.length; i++){
                    if(ranks1[i]!=kiker1)
                    list1.add(ranks1[i]);
                }



                List<Integer> list2 = new ArrayList<>();
                for(int i=0; i <ranks2.length; i++){
                    if(ranks2[i]!=kiker2)
                    list2.add(ranks2[i]);
                }


                int[] withoutkiker1 = list1.stream()
                        .mapToInt(Integer::intValue)
                        .toArray();

                int[] withoutkiker2 = list2.stream()
                        .mapToInt(Integer::intValue)
                        .toArray();

                if (determineKicker(withoutkiker1, Set(ranks1)) == determineKicker(withoutkiker2, Set(ranks2)))
                    return PokerResult.DRAW;
                else if (determineKicker(withoutkiker1, Set(ranks1)) > determineKicker(withoutkiker2, Set(ranks2))) {
                    return PokerResult.PLAYER_ONE_WIN;
                } else
                    return PokerResult.PLAYER_TWO_WIN;


            }

        } else if (Set(ranks1) > Set(ranks2)) {
            return PokerResult.PLAYER_ONE_WIN;
        } else if (Set(ranks1) < Set(ranks2)) {
            return PokerResult.PLAYER_TWO_WIN;
        } else if (TwoPairs(ranks1) == TwoPairs(ranks2) && TwoPairs(ranks1) != 0 && TwoPairs(ranks2) != 0) {


            List<Integer> list1 = new ArrayList<>();
            for(int i=0; i <ranks1.length; i++){
                list1.add(ranks1[i]);
            }

            for(int i =0; i<ranks1.length-1;i++){
                for(int k =i+1; k<ranks1.length;k++){
                    if(ranks1[i]+ranks1[k]==TwoPairs(ranks1)){
                        list1.remove((Integer) ranks1[i]);
                        list1.remove((Integer) ranks1[k]);

                        break;
                    }
                }
            }
            int[] withoutpairs1 = list1.stream()
                    .mapToInt(Integer::intValue)
                    .toArray();


            List<Integer> list2 = new ArrayList<>();
            for(int i=0; i <ranks2.length; i++){
                list2.add(ranks2[i]);
            }

            for(int i =0; i<ranks2.length-1;i++){
                for(int k =i+1; k<ranks2.length;k++){
                    if(ranks2[i]+ranks2[k]==TwoPairs(ranks2)){
                        list2.remove(ranks2[i]);
                        list2.remove(ranks2[k]);
                        break;
                    }
                }
            }
            int[] withoutpairs2 = list2.stream()
                    .mapToInt(Integer::intValue)
                    .toArray();

            Arrays.sort(withoutpairs1);
            Arrays.sort(withoutpairs2);


            if (withoutpairs1[withoutpairs1.length-1] == withoutpairs2[withoutpairs2.length-1])
                return PokerResult.DRAW;
            else if (withoutpairs1[withoutpairs1.length-1] > withoutpairs2[withoutpairs2.length-1])
                return PokerResult.PLAYER_ONE_WIN;
            else
                return PokerResult.PLAYER_TWO_WIN;
        } else if (TwoPairs(ranks1) > TwoPairs(ranks2)) {
            return PokerResult.PLAYER_ONE_WIN;
        } else if (TwoPairs(ranks1) < TwoPairs(ranks2)) {
            return PokerResult.PLAYER_TWO_WIN;
        } else if (Pair(ranks1) == Pair(ranks2) && Pair(ranks1) != 0 && Pair(ranks2) != 0) {


            int kiker1 = determineKicker(ranks1, Pair(ranks1));
            int kiker2 = determineKicker(ranks2, Pair(ranks2));

            if (kiker1 == kiker2) {

                List<Integer> list11 = new ArrayList<>();
                for(int i=0; i <ranks1.length; i++){
                    if(ranks1[i]!=kiker1)
                    list11.add(ranks1[i]);
                }


                List<Integer> list22 = new ArrayList<>();
                for(int i=0; i <ranks2.length; i++){
                    if(ranks2[i]!=kiker2)
                    list22.add(ranks2[i]);
                }


                int[] withoutkiker1 = list11.stream()
                        .mapToInt(Integer::intValue)
                        .toArray();

                int[] withoutkiker2 = list22.stream()
                        .mapToInt(Integer::intValue)
                        .toArray();

                if (determineKicker(withoutkiker1, Pair(ranks1)) == determineKicker(withoutkiker2, Pair(ranks2))) {



                    int kiker21 = determineKicker(withoutkiker1, Pair(ranks1));
                    int kiker22 = determineKicker(withoutkiker2, Pair(ranks2));




                    if (kiker21 == kiker22) {

                        List<Integer> listpair1 = new ArrayList<>();
                        for (int i = 0; i < withoutkiker1.length; i++) {
                            if(withoutkiker1[i]!=kiker21)
                            listpair1.add(withoutkiker1[i]);
                        }


                        List<Integer> listpair2 = new ArrayList<>();
                        for (int i = 0; i < withoutkiker2.length; i++) {
                            if(withoutkiker2[i]!=kiker22)
                            listpair2.add(withoutkiker2[i]);
                        }


                        int[] withoutkiker21 = list11.stream()
                                .mapToInt(Integer::intValue)
                                .toArray();

                        int[] withoutkiker22 = list22.stream()
                                .mapToInt(Integer::intValue)
                                .toArray();

                        if(determineKicker(withoutkiker21,Pair(ranks1))==determineKicker(withoutkiker22,Pair(ranks2))){
                            return PokerResult.DRAW;
                            }
                        else if (determineKicker(withoutkiker21,Pair(ranks1))>determineKicker(withoutkiker22,Pair(ranks2)))
                            return PokerResult.PLAYER_ONE_WIN;
                        else
                            return PokerResult.PLAYER_TWO_WIN;

                    }
                    if (kiker21 > kiker22)
                        return PokerResult.PLAYER_ONE_WIN;
                    else
                        return PokerResult.PLAYER_TWO_WIN;


                }
                else if (determineKicker(withoutkiker1, Pair(ranks1)) > determineKicker(withoutkiker2, Pair(ranks2))) {
                    return PokerResult.PLAYER_ONE_WIN;
                } else
                    return PokerResult.PLAYER_TWO_WIN;


            } else if (kiker1>kiker2) {
                return PokerResult.PLAYER_ONE_WIN;
            }
            else
                return PokerResult.PLAYER_TWO_WIN;

        } else if (Pair(ranks1) > Pair(ranks2)) {
            return PokerResult.PLAYER_ONE_WIN;
        } else if (Pair(ranks1) < Pair(ranks2)) {
            return PokerResult.PLAYER_TWO_WIN;
        } else if (ElderRank(ranks1) == ElderRank(ranks2)) {
            return PokerResult.DRAW;
        } else if (ElderRank(ranks1) > ElderRank(ranks2)) {
            return PokerResult.PLAYER_ONE_WIN;
        } else {
            return PokerResult.PLAYER_TWO_WIN;
        }


        return PokerResult.DRAW;
    }

}
