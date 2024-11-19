package org.example;


import java.util.*;

/**
 * Пример плохого дилера - он раздает повторяющиеся карты и всегда определяет исход как ничью
 */
public class DealerExample implements Dealer {


    private Deck deck;

    public DealerExample(Deck deck) {
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

        try {



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


            if (FlashRoyal(firstPlayerRanks, firstPlayerSuits) && FlashRoyal(secondPlayerRanks, secondPlayerSuits)) {
                return PokerResult.DRAW;
            } else if (FlashRoyal(firstPlayerRanks, firstPlayerSuits)) {
                return PokerResult.PLAYER_ONE_WIN;
            } else if (FlashRoyal(secondPlayerRanks, secondPlayerSuits)) {
                return PokerResult.PLAYER_TWO_WIN;
            } else if (StreetFlash(firstPlayerRanks, firstPlayerSuits) != 0 && StreetFlash(secondPlayerRanks, secondPlayerSuits) != 0 && StreetFlash(firstPlayerRanks, firstPlayerSuits) == StreetFlash(secondPlayerRanks, secondPlayerSuits)) {
                return PokerResult.DRAW;
            } else if (StreetFlash(firstPlayerRanks, firstPlayerSuits) != 0 && StreetFlash(secondPlayerRanks, secondPlayerSuits) == 0) {
                return PokerResult.PLAYER_ONE_WIN;
            } else if (StreetFlash(firstPlayerRanks, firstPlayerSuits) == 0 && StreetFlash(secondPlayerRanks, secondPlayerSuits) != 0) {
                return PokerResult.PLAYER_TWO_WIN;
            } else if (FourOfAKind(firstPlayerRanks) != 0 && FourOfAKind(secondPlayerRanks) != 0 && FourOfAKind(firstPlayerRanks) == FourOfAKind(secondPlayerRanks)) {


                int kiker1 = determineKicker(firstPlayerRanks, FourOfAKind(firstPlayerRanks));
                int kiker2 = determineKicker(secondPlayerRanks, FourOfAKind(secondPlayerRanks));

                if (kiker1 == kiker2)
                    return PokerResult.DRAW;
                else if (kiker1 > kiker2)
                    return PokerResult.PLAYER_ONE_WIN;
                else
                    return PokerResult.PLAYER_TWO_WIN;
            } else if (FourOfAKind(firstPlayerRanks) != 0 && FourOfAKind(secondPlayerRanks) == 0) {
                return PokerResult.PLAYER_ONE_WIN;
            } else if (FourOfAKind(firstPlayerRanks) == 0 && FourOfAKind(secondPlayerRanks) != 0) {
                return PokerResult.PLAYER_TWO_WIN;
            } else if (FullHouse(firstPlayerRanks) == FullHouse(secondPlayerRanks) && FullHouse(firstPlayerRanks) != 0 && FullHouse(secondPlayerRanks) != 0) {
                return PokerResult.DRAW;
            } else if (FullHouse(firstPlayerRanks) > FullHouse(secondPlayerRanks)) {
                return PokerResult.PLAYER_ONE_WIN;
            } else if (FullHouse(firstPlayerRanks) < FullHouse(secondPlayerRanks)) {
                return PokerResult.PLAYER_TWO_WIN;
            } else if (Flash(firstPlayerRanks, firstPlayerSuits).length == Flash(secondPlayerRanks, secondPlayerSuits).length &&
                    Flash(firstPlayerRanks, firstPlayerSuits).length != 0 && Flash(secondPlayerRanks, secondPlayerSuits).length != 0) {

                for (int counter = 0; counter < 5; counter++) {
                    if (Flash(firstPlayerRanks, firstPlayerSuits)[Flash(firstPlayerRanks, firstPlayerSuits).length - 1 - counter] > Flash(secondPlayerRanks, secondPlayerSuits)[Flash(secondPlayerRanks, secondPlayerSuits).length - 1 - counter])
                        return PokerResult.PLAYER_ONE_WIN;
                    if (Flash(firstPlayerRanks, firstPlayerSuits)[Flash(firstPlayerRanks, firstPlayerSuits).length - 1 - counter] < Flash(secondPlayerRanks, secondPlayerSuits)[Flash(secondPlayerRanks, secondPlayerSuits).length - 1 - counter])
                        return PokerResult.PLAYER_TWO_WIN;
                }
                return PokerResult.DRAW;
            } else if (Flash(firstPlayerRanks, firstPlayerSuits).length != 0 && Flash(secondPlayerRanks, secondPlayerSuits).length == 0) {
                return PokerResult.PLAYER_ONE_WIN;
            } else if (Flash(firstPlayerRanks, firstPlayerSuits).length == 0 && Flash(secondPlayerRanks, secondPlayerSuits).length != 0) {
                return PokerResult.PLAYER_TWO_WIN;
            } else if (Street(firstPlayerRanks) == Street(secondPlayerRanks) && Street(firstPlayerRanks) != 0 && Street(secondPlayerRanks) != 0) {
                return PokerResult.DRAW;
            } else if (Street(firstPlayerRanks) > Street(secondPlayerRanks)) {
                return PokerResult.PLAYER_ONE_WIN;
            } else if (Street(firstPlayerRanks) < Street(secondPlayerRanks)) {
                return PokerResult.PLAYER_TWO_WIN;
            } else if (Set(firstPlayerRanks) == Set(secondPlayerRanks) && Set(firstPlayerRanks) != 0 && Set(secondPlayerRanks) != 0) {

                int kiker1 = determineKicker(firstPlayerRanks, Set(firstPlayerRanks));
                int kiker2 = determineKicker(secondPlayerRanks, Set(secondPlayerRanks));

                if (kiker1 == kiker2) {

                    List<Integer> list1 = new ArrayList<>();
                    for (int i = 0; i < firstPlayerRanks.length; i++) {
                        if (firstPlayerRanks[i] != kiker1)
                            list1.add(firstPlayerRanks[i]);
                    }


                    List<Integer> list2 = new ArrayList<>();
                    for (int i = 0; i < secondPlayerRanks.length; i++) {
                        if (secondPlayerRanks[i] != kiker2)
                            list2.add(secondPlayerRanks[i]);
                    }


                    int[] withoutkiker1 = list1.stream()
                            .mapToInt(Integer::intValue)
                            .toArray();

                    int[] withoutkiker2 = list2.stream()
                            .mapToInt(Integer::intValue)
                            .toArray();

                    if (determineKicker(withoutkiker1, Set(firstPlayerRanks)) == determineKicker(withoutkiker2, Set(secondPlayerRanks)))
                        return PokerResult.DRAW;
                    else if (determineKicker(withoutkiker1, Set(firstPlayerRanks)) > determineKicker(withoutkiker2, Set(secondPlayerRanks))) {
                        return PokerResult.PLAYER_ONE_WIN;
                    } else
                        return PokerResult.PLAYER_TWO_WIN;


                } else if (kiker1 > kiker2) {
                    return PokerResult.PLAYER_ONE_WIN;
                } else {
                    return PokerResult.PLAYER_TWO_WIN;
                }

            } else if (Set(firstPlayerRanks) > Set(secondPlayerRanks)) {
                return PokerResult.PLAYER_ONE_WIN;
            } else if (Set(firstPlayerRanks) < Set(secondPlayerRanks)) {
                return PokerResult.PLAYER_TWO_WIN;
            } else if (TwoPairs(firstPlayerRanks)[0] > TwoPairs(secondPlayerRanks)[0] &&
                    TwoPairs(firstPlayerRanks)[0] != 0 && TwoPairs(secondPlayerRanks)[0] != 0 &&
                    TwoPairs(firstPlayerRanks)[1] != 0 && TwoPairs(secondPlayerRanks)[1] != 0) {
                return PokerResult.PLAYER_ONE_WIN;
            } else if (TwoPairs(firstPlayerRanks)[0] < TwoPairs(secondPlayerRanks)[0] &&
                    TwoPairs(firstPlayerRanks)[0] != 0 && TwoPairs(secondPlayerRanks)[0] != 0 &&
                    TwoPairs(firstPlayerRanks)[1] != 0 && TwoPairs(secondPlayerRanks)[1] != 0) {
                return PokerResult.PLAYER_TWO_WIN;
            } else if (TwoPairs(firstPlayerRanks)[0] == TwoPairs(secondPlayerRanks)[0] &&
                    TwoPairs(firstPlayerRanks)[1] == TwoPairs(secondPlayerRanks)[1] &&
                    TwoPairs(firstPlayerRanks)[0] != 0 && TwoPairs(secondPlayerRanks)[0] != 0 &&
                    TwoPairs(firstPlayerRanks)[1] != 0 && TwoPairs(secondPlayerRanks)[1] != 0) {


                List<Integer> list1 = new ArrayList<>();
                for (int i = 0; i < firstPlayerRanks.length; i++) {
                    if (firstPlayerRanks[i] != TwoPairs(firstPlayerRanks)[0] && firstPlayerRanks[i] != TwoPairs(firstPlayerRanks)[1])
                        list1.add(firstPlayerRanks[i]);
                }

                int[] withoutpairs1 = list1.stream()
                        .mapToInt(Integer::intValue)
                        .toArray();


                List<Integer> list2 = new ArrayList<>();
                for (int i = 0; i < secondPlayerRanks.length; i++) {
                    if (secondPlayerRanks[i] != TwoPairs(secondPlayerRanks)[0] && secondPlayerRanks[i] != TwoPairs(secondPlayerRanks)[1])
                        list2.add(secondPlayerRanks[i]);
                }

                int[] withoutpairs2 = list2.stream()
                        .mapToInt(Integer::intValue)
                        .toArray();

                Arrays.sort(withoutpairs1);
                Arrays.sort(withoutpairs2);


                if (withoutpairs1[withoutpairs1.length - 1] == withoutpairs2[withoutpairs2.length - 1])
                    return PokerResult.DRAW;
                else if (withoutpairs1[withoutpairs1.length - 1] > withoutpairs2[withoutpairs2.length - 1])
                    return PokerResult.PLAYER_ONE_WIN;
                else
                    return PokerResult.PLAYER_TWO_WIN;
            } else if (TwoPairs(firstPlayerRanks)[0] == TwoPairs(secondPlayerRanks)[0] &&
                    TwoPairs(firstPlayerRanks)[1] > TwoPairs(secondPlayerRanks)[1] &&
                    TwoPairs(firstPlayerRanks)[0] != 0 && TwoPairs(secondPlayerRanks)[0] != 0 &&
                    TwoPairs(firstPlayerRanks)[1] != 0 && TwoPairs(secondPlayerRanks)[1] != 0) {
                return PokerResult.PLAYER_ONE_WIN;
            } else if (TwoPairs(firstPlayerRanks)[0] == TwoPairs(secondPlayerRanks)[0] &&
                    TwoPairs(firstPlayerRanks)[1] < TwoPairs(secondPlayerRanks)[1] &&
                    TwoPairs(firstPlayerRanks)[0] != 0 && TwoPairs(secondPlayerRanks)[0] != 0 &&
                    TwoPairs(firstPlayerRanks)[1] != 0 && TwoPairs(secondPlayerRanks)[1] != 0) {
                return PokerResult.PLAYER_TWO_WIN;
            } else if ((TwoPairs(secondPlayerRanks)[0] == 0 || TwoPairs(secondPlayerRanks)[1] == 0) && TwoPairs(firstPlayerRanks)[0] != 0 && TwoPairs(firstPlayerRanks)[1] != 0) {
                return PokerResult.PLAYER_ONE_WIN;
            } else if ((TwoPairs(firstPlayerRanks)[0] == 0 || TwoPairs(firstPlayerRanks)[1] == 0) && TwoPairs(secondPlayerRanks)[0] != 0 && TwoPairs(secondPlayerRanks)[1] != 0) {
                return PokerResult.PLAYER_TWO_WIN;
            } else if (Pair(firstPlayerRanks) == Pair(secondPlayerRanks) && Pair(firstPlayerRanks) != 0 && Pair(secondPlayerRanks) != 0) {


                int kiker1 = determineKicker(firstPlayerRanks, Pair(firstPlayerRanks));
                int kiker2 = determineKicker(secondPlayerRanks, Pair(secondPlayerRanks));

                if (kiker1 == kiker2) {

                    List<Integer> list11 = new ArrayList<>();
                    for (int i = 0; i < firstPlayerRanks.length; i++) {
                        if (firstPlayerRanks[i] != kiker1)
                            list11.add(firstPlayerRanks[i]);
                    }


                    List<Integer> list22 = new ArrayList<>();
                    for (int i = 0; i < secondPlayerRanks.length; i++) {
                        if (secondPlayerRanks[i] != kiker2)
                            list22.add(secondPlayerRanks[i]);
                    }


                    int[] withoutkiker1 = list11.stream()
                            .mapToInt(Integer::intValue)
                            .toArray();

                    int[] withoutkiker2 = list22.stream()
                            .mapToInt(Integer::intValue)
                            .toArray();

                    if (determineKicker(withoutkiker1, Pair(firstPlayerRanks)) == determineKicker(withoutkiker2, Pair(secondPlayerRanks))) {


                        int kiker21 = determineKicker(withoutkiker1, Pair(firstPlayerRanks));
                        int kiker22 = determineKicker(withoutkiker2, Pair(secondPlayerRanks));


                        if (kiker21 == kiker22) {

                            List<Integer> listpair1 = new ArrayList<>();
                            for (int i = 0; i < withoutkiker1.length; i++) {
                                if (withoutkiker1[i] != kiker21)
                                    listpair1.add(withoutkiker1[i]);
                            }


                            List<Integer> listpair2 = new ArrayList<>();
                            for (int i = 0; i < withoutkiker2.length; i++) {
                                if (withoutkiker2[i] != kiker22)
                                    listpair2.add(withoutkiker2[i]);
                            }


                            int[] withoutkiker21 = listpair1.stream()
                                    .mapToInt(Integer::intValue)
                                    .toArray();

                            int[] withoutkiker22 = listpair2.stream()
                                    .mapToInt(Integer::intValue)
                                    .toArray();

                            if (determineKicker(withoutkiker21, Pair(firstPlayerRanks)) == determineKicker(withoutkiker22, Pair(secondPlayerRanks))) {
                                return PokerResult.DRAW;
                            } else if (determineKicker(withoutkiker21, Pair(firstPlayerRanks)) > determineKicker(withoutkiker22, Pair(secondPlayerRanks)))
                                return PokerResult.PLAYER_ONE_WIN;
                            else
                                return PokerResult.PLAYER_TWO_WIN;

                        }
                        if (kiker21 > kiker22)
                            return PokerResult.PLAYER_ONE_WIN;
                        else
                            return PokerResult.PLAYER_TWO_WIN;


                    } else if (determineKicker(withoutkiker1, Pair(firstPlayerRanks)) > determineKicker(withoutkiker2, Pair(secondPlayerRanks))) {
                        return PokerResult.PLAYER_ONE_WIN;
                    } else
                        return PokerResult.PLAYER_TWO_WIN;


                } else if (kiker1 > kiker2) {
                    return PokerResult.PLAYER_ONE_WIN;
                } else
                    return PokerResult.PLAYER_TWO_WIN;

            } else if (Pair(firstPlayerRanks) > Pair(secondPlayerRanks)) {
                return PokerResult.PLAYER_ONE_WIN;
            } else if (Pair(firstPlayerRanks) < Pair(secondPlayerRanks)) {
                return PokerResult.PLAYER_TWO_WIN;
            } else {
                int[] sortedarray1 = ElderRank(firstPlayerRanks);
                int[] sortedarray2 = ElderRank(secondPlayerRanks);

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
        }catch (InvalidPokerBoardException e) {
            System.out.println(e.getMessage());
        }
        return PokerResult.DRAW;
    }
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
    public static int[] Flash(int[] ranks, String[] suits) {


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
                if (i == 0) {
                    int[] arrayC = listC.stream()
                            .mapToInt(Integer::intValue)
                            .toArray();
                    Arrays.sort(arrayC);
                    return arrayC;
                }
                if (i == 1) {
                    int[] arrayD = listD.stream()
                            .mapToInt(Integer::intValue)
                            .toArray();
                    Arrays.sort(arrayD);
                    return arrayD;
                }
                if (i == 2) {
                    int[] arrayH = listH.stream()
                            .mapToInt(Integer::intValue)
                            .toArray();
                    Arrays.sort(arrayH);
                    return arrayH;
                }
                if (i == 3) {
                    int[] arrayS = listS.stream()
                            .mapToInt(Integer::intValue)
                            .toArray();
                    Arrays.sort(arrayS);
                    return arrayS;
                }
            }
        }

        return new int[0];
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
    public static int[] TwoPairs(int[] ranks) {


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

    // Переделал, теперь просто сортируем, так как у нас 5 кикеров
    public static int[] ElderRank(int[] ranks) {
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
