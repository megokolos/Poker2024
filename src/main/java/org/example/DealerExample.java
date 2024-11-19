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
    public PokerResult decideWinner(int[] ranks1, String[] suits1, int[] ranks2, String[] suits2) {

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
        } else if (Flash(ranks1, suits1).length == Flash(ranks2, suits2).length &&
                Flash(ranks1, suits1).length != 0 && Flash(ranks2, suits2).length != 0) {

            for (int counter = 0; counter < 5; counter++) {
                if (Flash(ranks1, suits1)[Flash(ranks1, suits1).length - 1 - counter] > Flash(ranks2, suits2)[Flash(ranks2, suits2).length - 1 - counter])
                    return PokerResult.PLAYER_ONE_WIN;
                if (Flash(ranks1, suits1)[Flash(ranks1, suits1).length - 1 - counter] < Flash(ranks2, suits2)[Flash(ranks2, suits2).length - 1 - counter])
                    return PokerResult.PLAYER_TWO_WIN;
            }
            return PokerResult.DRAW;
        } else if (Flash(ranks1, suits1).length != 0 && Flash(ranks2, suits2).length == 0) {
            return PokerResult.PLAYER_ONE_WIN;
        } else if (Flash(ranks1, suits1).length == 0 && Flash(ranks2, suits2).length != 0) {
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

                if (determineKicker(withoutkiker1, Set(ranks1)) == determineKicker(withoutkiker2, Set(ranks2)))
                    return PokerResult.DRAW;
                else if (determineKicker(withoutkiker1, Set(ranks1)) > determineKicker(withoutkiker2, Set(ranks2))) {
                    return PokerResult.PLAYER_ONE_WIN;
                } else
                    return PokerResult.PLAYER_TWO_WIN;


            }
            else if (kiker1 > kiker2) {
                return PokerResult.PLAYER_ONE_WIN;
            }
            else {
                return PokerResult.PLAYER_TWO_WIN;
            }

        } else if (Set(ranks1) > Set(ranks2)) {
            return PokerResult.PLAYER_ONE_WIN;
        } else if (Set(ranks1) < Set(ranks2)) {
            return PokerResult.PLAYER_TWO_WIN;
        } else if (TwoPairs(ranks1)[0] > TwoPairs(ranks2)[0] &&
                TwoPairs(ranks1)[0] != 0 && TwoPairs(ranks2)[0] != 0 &&
                TwoPairs(ranks1)[1] != 0 && TwoPairs(ranks2)[1] != 0) {
            return PokerResult.PLAYER_ONE_WIN;
        } else if (TwoPairs(ranks1)[0] < TwoPairs(ranks2)[0] &&
                TwoPairs(ranks1)[0] != 0 && TwoPairs(ranks2)[0] != 0 &&
                TwoPairs(ranks1)[1] != 0 && TwoPairs(ranks2)[1] != 0) {
            return PokerResult.PLAYER_TWO_WIN;
        } else if (TwoPairs(ranks1)[0] == TwoPairs(ranks2)[0] &&
                TwoPairs(ranks1)[1] == TwoPairs(ranks2)[1] &&
                TwoPairs(ranks1)[0] != 0 && TwoPairs(ranks2)[0] != 0 &&
                TwoPairs(ranks1)[1] != 0 && TwoPairs(ranks2)[1] != 0) {


            List<Integer> list1 = new ArrayList<>();
            for (int i = 0; i < ranks1.length; i++) {
                if (ranks1[i] != TwoPairs(ranks1)[0] && ranks1[i] != TwoPairs(ranks1)[1])
                    list1.add(ranks1[i]);
            }

            int[] withoutpairs1 = list1.stream()
                    .mapToInt(Integer::intValue)
                    .toArray();


            List<Integer> list2 = new ArrayList<>();
            for (int i = 0; i < ranks2.length; i++) {
                if (ranks2[i] != TwoPairs(ranks2)[0] && ranks2[i] != TwoPairs(ranks2)[1])
                    list2.add(ranks2[i]);
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
        } else if (TwoPairs(ranks1)[0] == TwoPairs(ranks2)[0] &&
                TwoPairs(ranks1)[1] > TwoPairs(ranks2)[1] &&
                TwoPairs(ranks1)[0] != 0 && TwoPairs(ranks2)[0] != 0 &&
                TwoPairs(ranks1)[1] != 0 && TwoPairs(ranks2)[1] != 0) {
            return PokerResult.PLAYER_ONE_WIN;
        } else if (TwoPairs(ranks1)[0] == TwoPairs(ranks2)[0] &&
                TwoPairs(ranks1)[1] < TwoPairs(ranks2)[1] &&
                TwoPairs(ranks1)[0] != 0 && TwoPairs(ranks2)[0] != 0 &&
                TwoPairs(ranks1)[1] != 0 && TwoPairs(ranks2)[1] != 0) {
            return PokerResult.PLAYER_TWO_WIN;
        } else if ((TwoPairs(ranks2)[0] == 0 || TwoPairs(ranks2)[1] == 0) && TwoPairs(ranks1)[0] != 0 && TwoPairs(ranks1)[1] != 0) {
            return PokerResult.PLAYER_ONE_WIN;
        } else if ((TwoPairs(ranks1)[0] == 0 || TwoPairs(ranks1)[1] == 0) && TwoPairs(ranks2)[0] != 0 && TwoPairs(ranks2)[1] != 0) {
            return PokerResult.PLAYER_TWO_WIN;
        } else if (Pair(ranks1) == Pair(ranks2) && Pair(ranks1) != 0 && Pair(ranks2) != 0) {


            int kiker1 = determineKicker(ranks1, Pair(ranks1));
            int kiker2 = determineKicker(ranks2, Pair(ranks2));

            if (kiker1 == kiker2) {

                List<Integer> list11 = new ArrayList<>();
                for (int i = 0; i < ranks1.length; i++) {
                    if (ranks1[i] != kiker1)
                        list11.add(ranks1[i]);
                }


                List<Integer> list22 = new ArrayList<>();
                for (int i = 0; i < ranks2.length; i++) {
                    if (ranks2[i] != kiker2)
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

                        if (determineKicker(withoutkiker21, Pair(ranks1)) == determineKicker(withoutkiker22, Pair(ranks2))) {
                            return PokerResult.DRAW;
                        } else if (determineKicker(withoutkiker21, Pair(ranks1)) > determineKicker(withoutkiker22, Pair(ranks2)))
                            return PokerResult.PLAYER_ONE_WIN;
                        else
                            return PokerResult.PLAYER_TWO_WIN;

                    }
                    if (kiker21 > kiker22)
                        return PokerResult.PLAYER_ONE_WIN;
                    else
                        return PokerResult.PLAYER_TWO_WIN;


                } else if (determineKicker(withoutkiker1, Pair(ranks1)) > determineKicker(withoutkiker2, Pair(ranks2))) {
                    return PokerResult.PLAYER_ONE_WIN;
                } else
                    return PokerResult.PLAYER_TWO_WIN;


            } else if (kiker1 > kiker2) {
                return PokerResult.PLAYER_ONE_WIN;
            } else
                return PokerResult.PLAYER_TWO_WIN;

        } else if (Pair(ranks1) > Pair(ranks2)) {
            return PokerResult.PLAYER_ONE_WIN;
        } else if (Pair(ranks1) < Pair(ranks2)) {
            return PokerResult.PLAYER_TWO_WIN;
        } else {
            int[] sortedarray1 = ElderRank(ranks1);
            int[] sortedarray2 = ElderRank(ranks2);

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
}
