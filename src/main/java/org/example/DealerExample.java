package org.example;


import java.util.*;

/**
 * Пример плохого дилера - он раздает повторяющиеся карты и всегда определяет исход как ничью
 */
public class DealerExample implements Dealer {


    private final Deck deck;

    public DealerExample() {
        this.deck= new Deck();
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

            String firstPlayer1Card = "";
            String firstPlayer2Card = "";
            String secondPlayer1Card = "";
            String secondPlayer2Card = "";
            String flop1 = "";
            String flop2 = "";
            String flop3 = "";
            String turn = board.getTurn();
            String river = board.getRiver();

            if (board.getPlayerOne()==null || board.getPlayerTwo() == null || board.getFlop()==null ||
            board.getTurn()==null || board.getRiver()==null){
                throw new InvalidPokerBoardException(
                        "- А карты где?");
            }


            firstPlayer1Card = separatePlayerCards(board.getPlayerOne(), firstPlayer1Card, firstPlayer2Card)[0];
            firstPlayer2Card = separatePlayerCards(board.getPlayerOne(), firstPlayer1Card, firstPlayer2Card)[1];

            secondPlayer1Card = separatePlayerCards(board.getPlayerTwo(), secondPlayer1Card, secondPlayer2Card)[0];
            secondPlayer2Card = separatePlayerCards(board.getPlayerTwo(), secondPlayer1Card, secondPlayer2Card)[1];


            flop1 = separateFlop(board.getFlop(), flop1, flop2, flop3)[0];
            flop2 = separateFlop(board.getFlop(), flop1, flop2, flop3)[1];
            flop3 = separateFlop(board.getFlop(), flop1, flop2, flop3)[2];


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
                                "- Почему карты одинаковые в колоде?");
                }
            }



            int[] firstPlayerRanks = new int[7];
            String[] firstPlayerSuits = new String[7];


            int[] secondPlayerRanks = new int[7];
            String[] secondPlayerSuits = new String[7];


            //Номиналы карт

            playersCardsAutofill(firstPlayer1Card, firstPlayerRanks, 0);
            playersCardsAutofill(firstPlayer2Card, firstPlayerRanks, 1);

            playersCardsAutofill(secondPlayer1Card, secondPlayerRanks, 0);
            playersCardsAutofill(secondPlayer2Card, secondPlayerRanks, 1);

            playersCardsAndTableAutofill(flop1, firstPlayerRanks, secondPlayerRanks, 2);
            playersCardsAndTableAutofill(flop2, firstPlayerRanks, secondPlayerRanks, 3);
            playersCardsAndTableAutofill(flop3, firstPlayerRanks, secondPlayerRanks, 4);
            playersCardsAndTableAutofill(board.getTurn(), firstPlayerRanks, secondPlayerRanks, 5);
            playersCardsAndTableAutofill(board.getRiver(), firstPlayerRanks, secondPlayerRanks, 6);


            // Масти карт

            suitsAutofill(firstPlayerSuits, firstPlayer1Card, firstPlayer2Card, flop1, flop2, flop3,
                    turn, river);


            suitsAutofill(secondPlayerSuits, secondPlayer1Card, secondPlayer2Card, flop1, flop2, flop3,
                    turn, river);


            if (checkFlashRoyal(firstPlayerRanks, firstPlayerSuits) && checkFlashRoyal(secondPlayerRanks, secondPlayerSuits)) {
                return PokerResult.DRAW;
            } else if (checkFlashRoyal(firstPlayerRanks, firstPlayerSuits)) {
                return PokerResult.PLAYER_ONE_WIN;
            } else if (checkFlashRoyal(secondPlayerRanks, secondPlayerSuits)) {
                return PokerResult.PLAYER_TWO_WIN;
            } else if (checkStreetFlash(firstPlayerRanks, firstPlayerSuits) != 0 &&
                        checkStreetFlash(secondPlayerRanks, secondPlayerSuits) != 0 &&
                        checkStreetFlash(firstPlayerRanks, firstPlayerSuits) == checkStreetFlash(secondPlayerRanks, secondPlayerSuits)) {
                return PokerResult.DRAW;
            } else if (checkStreetFlash(firstPlayerRanks, firstPlayerSuits) != 0 &&
                        checkStreetFlash(secondPlayerRanks, secondPlayerSuits) == 0) {
                return PokerResult.PLAYER_ONE_WIN;
            } else if (checkStreetFlash(firstPlayerRanks, firstPlayerSuits) == 0 &&
                        checkStreetFlash(secondPlayerRanks, secondPlayerSuits) != 0) {
                return PokerResult.PLAYER_TWO_WIN;
            } else if (checkFourOfAKind(firstPlayerRanks) != 0 &&
                        checkFourOfAKind(secondPlayerRanks) != 0 &&
                        checkFourOfAKind(firstPlayerRanks) == checkFourOfAKind(secondPlayerRanks)) {


                int kiker1 = determineKicker(firstPlayerRanks, checkFourOfAKind(firstPlayerRanks));
                int kiker2 = determineKicker(secondPlayerRanks, checkFourOfAKind(secondPlayerRanks));

                if (kiker1 == kiker2)
                    return PokerResult.DRAW;
                else if (kiker1 > kiker2)
                    return PokerResult.PLAYER_ONE_WIN;
                else
                    return PokerResult.PLAYER_TWO_WIN;
            } else if (checkFourOfAKind(firstPlayerRanks) != 0 && checkFourOfAKind(secondPlayerRanks) == 0) {
                return PokerResult.PLAYER_ONE_WIN;
            } else if (checkFourOfAKind(firstPlayerRanks) == 0 && checkFourOfAKind(secondPlayerRanks) != 0) {
                return PokerResult.PLAYER_TWO_WIN;
            } else if (checkFullHouse(firstPlayerRanks) == checkFullHouse(secondPlayerRanks) && checkFullHouse(firstPlayerRanks) != 0 && checkFullHouse(secondPlayerRanks) != 0) {
                return PokerResult.DRAW;
            } else if (checkFullHouse(firstPlayerRanks) > checkFullHouse(secondPlayerRanks)) {
                return PokerResult.PLAYER_ONE_WIN;
            } else if (checkFullHouse(firstPlayerRanks) < checkFullHouse(secondPlayerRanks)) {
                return PokerResult.PLAYER_TWO_WIN;
            } else if (checkFlash(firstPlayerRanks, firstPlayerSuits).length == checkFlash(secondPlayerRanks, secondPlayerSuits).length &&
                    checkFlash(firstPlayerRanks, firstPlayerSuits).length != 0 && checkFlash(secondPlayerRanks, secondPlayerSuits).length != 0) {

                for (int counter = 0; counter < 5; counter++) {
                    if (checkFlash(firstPlayerRanks, firstPlayerSuits)[checkFlash(firstPlayerRanks, firstPlayerSuits).length - 1 - counter] > checkFlash(secondPlayerRanks, secondPlayerSuits)[checkFlash(secondPlayerRanks, secondPlayerSuits).length - 1 - counter])
                        return PokerResult.PLAYER_ONE_WIN;
                    if (checkFlash(firstPlayerRanks, firstPlayerSuits)[checkFlash(firstPlayerRanks, firstPlayerSuits).length - 1 - counter] < checkFlash(secondPlayerRanks, secondPlayerSuits)[checkFlash(secondPlayerRanks, secondPlayerSuits).length - 1 - counter])
                        return PokerResult.PLAYER_TWO_WIN;
                }
                return PokerResult.DRAW;
            } else if (checkFlash(firstPlayerRanks, firstPlayerSuits).length != 0 &&
                        checkFlash(secondPlayerRanks, secondPlayerSuits).length == 0) {
                return PokerResult.PLAYER_ONE_WIN;
            } else if (checkFlash(firstPlayerRanks, firstPlayerSuits).length == 0 &&
                        checkFlash(secondPlayerRanks, secondPlayerSuits).length != 0) {
                return PokerResult.PLAYER_TWO_WIN;
            } else if (checkStreet(firstPlayerRanks) == checkStreet(secondPlayerRanks) &&
                        checkStreet(firstPlayerRanks) != 0 && checkStreet(secondPlayerRanks) != 0) {
                return PokerResult.DRAW;
            } else if (checkStreet(firstPlayerRanks) > checkStreet(secondPlayerRanks)) {
                return PokerResult.PLAYER_ONE_WIN;
            } else if (checkStreet(firstPlayerRanks) < checkStreet(secondPlayerRanks)) {
                return PokerResult.PLAYER_TWO_WIN;
            } else if (checkSet(firstPlayerRanks) == checkSet(secondPlayerRanks) &&
                    checkSet(firstPlayerRanks) != 0 && checkSet(secondPlayerRanks) != 0) {

                int kiker1 = determineKicker(firstPlayerRanks, checkSet(firstPlayerRanks));
                int kiker2 = determineKicker(secondPlayerRanks, checkSet(secondPlayerRanks));

                if (kiker1 == kiker2) {

                    if (determineKicker(removeKicker(firstPlayerRanks,kiker1,secondPlayerRanks,kiker2)[0], checkSet(firstPlayerRanks)) == determineKicker(removeKicker(firstPlayerRanks,kiker1,secondPlayerRanks,kiker2)[1], checkSet(secondPlayerRanks)))
                        return PokerResult.DRAW;
                    else if (determineKicker(removeKicker(firstPlayerRanks,kiker1,secondPlayerRanks,kiker2)[0], checkSet(firstPlayerRanks)) > determineKicker(removeKicker(firstPlayerRanks,kiker1,secondPlayerRanks,kiker2)[1], checkSet(secondPlayerRanks))) {
                        return PokerResult.PLAYER_ONE_WIN;
                    } else
                        return PokerResult.PLAYER_TWO_WIN;


                } else if (kiker1 > kiker2) {
                    return PokerResult.PLAYER_ONE_WIN;
                } else {
                    return PokerResult.PLAYER_TWO_WIN;
                }

            } else if (checkSet(firstPlayerRanks) > checkSet(secondPlayerRanks)) {
                return PokerResult.PLAYER_ONE_WIN;
            } else if (checkSet(firstPlayerRanks) < checkSet(secondPlayerRanks)) {
                return PokerResult.PLAYER_TWO_WIN;
            } else if (checkTwoPairs(firstPlayerRanks)[0] > checkTwoPairs(secondPlayerRanks)[0] &&
                    checkTwoPairs(firstPlayerRanks)[0] != 0 && checkTwoPairs(secondPlayerRanks)[0] != 0 &&
                    checkTwoPairs(firstPlayerRanks)[1] != 0 && checkTwoPairs(secondPlayerRanks)[1] != 0) {
                return PokerResult.PLAYER_ONE_WIN;
            } else if (checkTwoPairs(firstPlayerRanks)[0] < checkTwoPairs(secondPlayerRanks)[0] &&
                    checkTwoPairs(firstPlayerRanks)[0] != 0 && checkTwoPairs(secondPlayerRanks)[0] != 0 &&
                    checkTwoPairs(firstPlayerRanks)[1] != 0 && checkTwoPairs(secondPlayerRanks)[1] != 0) {
                return PokerResult.PLAYER_TWO_WIN;
            } else if (checkTwoPairs(firstPlayerRanks)[0] == checkTwoPairs(secondPlayerRanks)[0] &&
                    checkTwoPairs(firstPlayerRanks)[1] == checkTwoPairs(secondPlayerRanks)[1] &&
                    checkTwoPairs(firstPlayerRanks)[0] != 0 && checkTwoPairs(secondPlayerRanks)[0] != 0 &&
                    checkTwoPairs(firstPlayerRanks)[1] != 0 && checkTwoPairs(secondPlayerRanks)[1] != 0) {




                int[] withoutPairs1 = removeTwoPairs(firstPlayerRanks);

                int[] withoutPairs2 = removeTwoPairs(secondPlayerRanks);

                Arrays.sort(withoutPairs1);
                Arrays.sort(withoutPairs2);


                if (withoutPairs1[withoutPairs1.length - 1] == withoutPairs2[withoutPairs2.length - 1])
                    return PokerResult.DRAW;
                else if (withoutPairs1[withoutPairs1.length - 1] > withoutPairs2[withoutPairs2.length - 1])
                    return PokerResult.PLAYER_ONE_WIN;
                else
                    return PokerResult.PLAYER_TWO_WIN;
            } else if (checkTwoPairs(firstPlayerRanks)[0] == checkTwoPairs(secondPlayerRanks)[0] &&
                    checkTwoPairs(firstPlayerRanks)[1] > checkTwoPairs(secondPlayerRanks)[1] &&
                    checkTwoPairs(firstPlayerRanks)[0] != 0 && checkTwoPairs(secondPlayerRanks)[0] != 0 &&
                    checkTwoPairs(firstPlayerRanks)[1] != 0 && checkTwoPairs(secondPlayerRanks)[1] != 0) {
                return PokerResult.PLAYER_ONE_WIN;
            } else if (checkTwoPairs(firstPlayerRanks)[0] == checkTwoPairs(secondPlayerRanks)[0] &&
                    checkTwoPairs(firstPlayerRanks)[1] < checkTwoPairs(secondPlayerRanks)[1] &&
                    checkTwoPairs(firstPlayerRanks)[0] != 0 && checkTwoPairs(secondPlayerRanks)[0] != 0 &&
                    checkTwoPairs(firstPlayerRanks)[1] != 0 && checkTwoPairs(secondPlayerRanks)[1] != 0) {
                return PokerResult.PLAYER_TWO_WIN;
            } else if ((checkTwoPairs(secondPlayerRanks)[0] == 0 || checkTwoPairs(secondPlayerRanks)[1] == 0) &&
                        checkTwoPairs(firstPlayerRanks)[0] != 0 && checkTwoPairs(firstPlayerRanks)[1] != 0) {
                return PokerResult.PLAYER_ONE_WIN;
            } else if ((checkTwoPairs(firstPlayerRanks)[0] == 0 || checkTwoPairs(firstPlayerRanks)[1] == 0) &&
                        checkTwoPairs(secondPlayerRanks)[0] != 0 && checkTwoPairs(secondPlayerRanks)[1] != 0) {
                return PokerResult.PLAYER_TWO_WIN;
            } else if (checkPair(firstPlayerRanks) == checkPair(secondPlayerRanks) &&
                        checkPair(firstPlayerRanks) != 0 && checkPair(secondPlayerRanks) != 0) {


                int kiker1 = determineKicker(firstPlayerRanks, checkPair(firstPlayerRanks));
                int kiker2 = determineKicker(secondPlayerRanks, checkPair(secondPlayerRanks));

                if (kiker1 == kiker2) {

                    int[] withoutkiker1 = removeKicker(firstPlayerRanks,kiker1,secondPlayerRanks,kiker2)[0];

                    int[] withoutkiker2 = removeKicker(firstPlayerRanks,kiker1,secondPlayerRanks,kiker2)[1];

                    if (determineKicker(withoutkiker1, checkPair(firstPlayerRanks)) == determineKicker(withoutkiker2, checkPair(secondPlayerRanks))) {


                        int kiker21 = determineKicker(withoutkiker1, checkPair(firstPlayerRanks));
                        int kiker22 = determineKicker(withoutkiker2, checkPair(secondPlayerRanks));


                        if (kiker21 == kiker22) {



                            int[] withoutkiker21 = removeKicker(withoutkiker1,kiker21,withoutkiker2,kiker22)[0];

                            int[] withoutkiker22 = removeKicker(withoutkiker1,kiker21,withoutkiker2,kiker22)[1];

                            if (determineKicker(withoutkiker21, checkPair(firstPlayerRanks)) == determineKicker(withoutkiker22, checkPair(secondPlayerRanks))) {
                                return PokerResult.DRAW;
                            } else if (determineKicker(withoutkiker21, checkPair(firstPlayerRanks)) > determineKicker(withoutkiker22, checkPair(secondPlayerRanks)))
                                return PokerResult.PLAYER_ONE_WIN;
                            else
                                return PokerResult.PLAYER_TWO_WIN;

                        }
                        if (kiker21 > kiker22)
                            return PokerResult.PLAYER_ONE_WIN;
                        else
                            return PokerResult.PLAYER_TWO_WIN;


                    } else if (determineKicker(withoutkiker1, checkPair(firstPlayerRanks)) > determineKicker(withoutkiker2, checkPair(secondPlayerRanks))) {
                        return PokerResult.PLAYER_ONE_WIN;
                    } else
                        return PokerResult.PLAYER_TWO_WIN;


                } else if (kiker1 > kiker2) {
                    return PokerResult.PLAYER_ONE_WIN;
                } else
                    return PokerResult.PLAYER_TWO_WIN;

            } else if (checkPair(firstPlayerRanks) > checkPair(secondPlayerRanks)) {
                return PokerResult.PLAYER_ONE_WIN;
            } else if (checkPair(firstPlayerRanks) < checkPair(secondPlayerRanks)) {
                return PokerResult.PLAYER_TWO_WIN;
            } else {
                int[] sortedarray1 = checkElderRank(firstPlayerRanks);
                int[] sortedarray2 = checkElderRank(secondPlayerRanks);

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
    public static boolean checkFlashRoyal(int[] ranks, String[] suits) {

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
    public static int checkStreetFlash(int[] ranks, String[] suits) {

        Map<String, List<Integer>> suitToRanks = new HashMap<>();

        for (int i = 0; i < ranks.length; i++) {
            String suit = suits[i];
            int rank = ranks[i];


            suitToRanks.computeIfAbsent(suit, k -> new ArrayList<>()).add(rank);

        }


        for (List<Integer> suitRanks : suitToRanks.values()) {
            suitRanks.sort(Integer::compareTo);


            if (suitRanks.size() >= 5 && (checkStreet(suitRanks.stream()
                    .mapToInt(Integer::intValue)
                    .toArray())==14))
                return 14;

            if (suitRanks.size() >= 5 && (checkStreet(suitRanks.stream()
                    .mapToInt(Integer::intValue)
                    .toArray())==13))
                return 13;
            if (suitRanks.size() >= 5 && (checkStreet(suitRanks.stream()
                    .mapToInt(Integer::intValue)
                    .toArray())==12))
                return 12;
            if (suitRanks.size() >= 5 && (checkStreet(suitRanks.stream()
                    .mapToInt(Integer::intValue)
                    .toArray())==11))
                return 11;
            if (suitRanks.size() >= 5 && (checkStreet(suitRanks.stream()
                    .mapToInt(Integer::intValue)
                    .toArray())==10))
                return 10;
            if (suitRanks.size() >= 5 && (checkStreet(suitRanks.stream()
                    .mapToInt(Integer::intValue)
                    .toArray())==9))
                return 9;
            if (suitRanks.size() >= 5 && (checkStreet(suitRanks.stream()
                    .mapToInt(Integer::intValue)
                    .toArray())==8))
                return 8;
            if (suitRanks.size() >= 5 && (checkStreet(suitRanks.stream()
                    .mapToInt(Integer::intValue)
                    .toArray())==7))
                return 7;
            if (suitRanks.size() >= 5 && (checkStreet(suitRanks.stream()
                    .mapToInt(Integer::intValue)
                    .toArray())==6))
                return 6;


        }
        return 0;
    }

    //Проверка на кикер нужна
    public static int checkFourOfAKind(int[] ranks) {

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
    public static int checkFullHouse(int[] ranks) {

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
                response += i * 2;
            }

            if (countRanks[i] == 3) {
                three++;
                response += i * 3;
            }
        }
        if (three != 0 && two != 0)
            return response;


        return 0;
    }

    //Проверка на кикер не нужна
    public static int[] checkFlash(int[] ranks, String[] suits) {


        List<Integer> listC = new ArrayList<>();
        List<Integer> listD = new ArrayList<>();
        List<Integer> listH = new ArrayList<>();
        List<Integer> listS = new ArrayList<>();

        int[] counter = new int[4];

        for (int i = 0; i < suits.length; i++) {
            if (suits[i].equals("C")) {
                counter[0] += 1;
                listC.add(ranks[i]);
            } else if (suits[i].equals("D")) {
                counter[1] += 1;
                listD.add(ranks[i]);
            } else if (suits[i].equals("H")) {
                counter[2] += 1;
                listH.add(ranks[i]);
            } else if (suits[i].equals("S")) {
                counter[3] += 1;
                listS.add(ranks[i]);
            }
        }

        for (int i = 0; i < counter.length; i++) {
            if (counter[i] >= 5) {
                switch (i){
                    case 0 -> {
                        int[] arrayC = listC.stream()
                                .mapToInt(Integer::intValue)
                                .toArray();
                        Arrays.sort(arrayC);
                        return arrayC;
                    }
                    case  1 -> {
                        int[] arrayD = listD.stream()
                                .mapToInt(Integer::intValue)
                                .toArray();
                        Arrays.sort(arrayD);
                        return arrayD;
                    }
                    case 2 -> {
                        int[] arrayH = listH.stream()
                                .mapToInt(Integer::intValue)
                                .toArray();
                        Arrays.sort(arrayH);
                        return arrayH;
                    }
                    case 3 -> {
                        int[] arrayS = listS.stream()
                                .mapToInt(Integer::intValue)
                                .toArray();
                        Arrays.sort(arrayS);
                        return arrayS;
                    }
                }
            }
        }

        return new int[0];
    }

    //Проверка на кикер не нужна
    public static int checkStreet(int[] ranks) {

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
    public static int checkSet(int[] ranks) {


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
    public static int[] checkTwoPairs(int[] ranks) {


        int[] countRanks = new int[15];

        int[] response = new int[2];

        for (int i = 0; i < ranks.length; i++) {
            countRanks[ranks[i]] += 1;
        }

        List<Integer> list = new ArrayList<>();

        for (int i = countRanks.length - 1; i > 0; i--) {
            if (countRanks[i] == 2) {
                list.add(i);
            }
        }
        Collections.sort(list);
        if (list.size() >= 2) {
            response[0] = list.get(list.size() - 1);
            response[1] = list.get(list.size() - 2);
        }
        return response;
    }

    //Проверка на кикер нужна
    public static int checkPair(int[] ranks) {


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

    // Переделал, теперь просто сортируем, так как у нас 5 кикеров
    public static int[] checkElderRank(int[] ranks) {
        Arrays.sort(ranks);

        return ranks;
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
    static void playersCardsAutofill(String card, int[] player1Cards, int position) {
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

    static void playersCardsAndTableAutofill(String card, int[] player1Cards, int[] player2Cards, int position) {
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

    public static void suitsAutofill(String[] suitsArray, String firstCard, String secondCard, String flop1,
                                     String flop2, String flop3, String turn, String river) {
        suitsArray[0] = firstCard.substring(firstCard.length() - 1);
        suitsArray[1] = secondCard.substring(secondCard.length() - 1);
        suitsArray[2] = flop1.substring(flop1.length() - 1);
        suitsArray[3] = flop2.substring(flop2.length() - 1);
        suitsArray[4] = flop3.substring(flop3.length() - 1);
        suitsArray[5] = turn.substring(turn.length() - 1);
        suitsArray[6] = river.substring(river.length() - 1);
    }

    public static String[] separatePlayerCards(String cards, String firstCard, String secondCard) {
        if (cards.startsWith("10")) {
            firstCard = cards.substring(0, 3);
            secondCard = cards.substring(3);
        } else {
            firstCard = cards.substring(0, 2);
            secondCard = cards.substring(2);
        }
        return new String[]{firstCard, secondCard};
    }

    public static String[] separateFlop(String flop, String flop1Card, String flop2Card, String flop3Card) {
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
    public int[][] removeKicker(int[] ranks1, int kiker1, int[] ranks2, int kiker2){
        List<Integer> list1 = new ArrayList<>();
        for (int i = 0; i < ranks1.length; i++) {
            if (ranks1[i] != kiker1)
                list1.add(ranks1[i]);
        }


        List<Integer> list2 = new ArrayList<>();
        for (int i = 0; i < ranks2.length; i++) {
            if (ranks2[i] != kiker2)
                list2.add(ranks2[i]);
        }


        int[] withoutkiker1 = list1.stream()
                .mapToInt(Integer::intValue)
                .toArray();

        int[] withoutkiker2 = list2.stream()
                .mapToInt(Integer::intValue)
                .toArray();

        return new int[][]{withoutkiker1, withoutkiker2};
    }

    public static int[] removeTwoPairs (int[] ranks){
        List<Integer> list1 = new ArrayList<>();
        for (int i = 0; i < ranks.length; i++) {
            if (ranks[i] != checkTwoPairs(ranks)[0] && ranks[i] != checkTwoPairs(ranks)[1])
                list1.add(ranks[i]);
        }

        return  list1.stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }
}
