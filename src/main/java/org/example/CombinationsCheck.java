package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class CombinationsCheck {

    //Проверка на кикер не нужна
    public static boolean checkFlashRoyal(List<Card> player) {

        List<Integer> royalRanks = Arrays.asList(10, 11, 12, 13, 14);

        Map<String, List<Card>> groupedBySuit = player.stream()
                .collect(Collectors.groupingBy(Card::getSuit));


        for (Map.Entry<String, List<Card>> entry : groupedBySuit.entrySet()) {
            List<Card> sameSuitCards = entry.getValue();

            List<Integer> ranks = sameSuitCards.stream()
                    .map(Card::getRank)
                    .toList();
            if (ranks.containsAll(royalRanks)) {
                return true;
            }
        }
        return false;
    }

    //Проверка на кикер не нужна
    public static int checkStreetFlash(List<Card> player) {

        Map<String, List<Integer>> suitToRanks = new HashMap<>();
        final int MIN_NUMBER_OF_CARDS_FOR_FLASH = 5;

        for (int i = 0; i < player.size(); i++) {
            String suit = player.get(i).getSuit();
            int rank = player.get(i).getRank();


            suitToRanks.computeIfAbsent(suit, k -> new ArrayList<>()).add(rank);

        }


        for (List<Integer> suitRanks : suitToRanks.values()) {
            suitRanks.sort(Integer::compareTo);


            if (suitRanks.size() >= MIN_NUMBER_OF_CARDS_FOR_FLASH && (checkStreet(player) == 14))
                return 14;
            if (suitRanks.size() >= MIN_NUMBER_OF_CARDS_FOR_FLASH && (checkStreet(player) == 13))
                return 13;
            if (suitRanks.size() >= MIN_NUMBER_OF_CARDS_FOR_FLASH && (checkStreet(player) == 12))
                return 12;
            if (suitRanks.size() >= MIN_NUMBER_OF_CARDS_FOR_FLASH && (checkStreet(player) == 11))
                return 11;
            if (suitRanks.size() >= MIN_NUMBER_OF_CARDS_FOR_FLASH && (checkStreet(player) == 10))
                return 10;
            if (suitRanks.size() >= MIN_NUMBER_OF_CARDS_FOR_FLASH && (checkStreet(player) == 9))
                return 9;
            if (suitRanks.size() >= MIN_NUMBER_OF_CARDS_FOR_FLASH && (checkStreet(player) == 8))
                return 8;
            if (suitRanks.size() >= MIN_NUMBER_OF_CARDS_FOR_FLASH && (checkStreet(player) == 7))
                return 7;
            if (suitRanks.size() >= MIN_NUMBER_OF_CARDS_FOR_FLASH && (checkStreet(player) == 6))
                return 6;


        }
        return 0;
    }

    //Проверка на кикер нужна
    public static int checkFourOfAKind(List<Card> player) {

        int[] countRanks = new int[15];

        for (int i = 0; i < player.size(); i++) {
            countRanks[player.get(i).getRank()] += 1;
        }

        for (int i = 0; i < countRanks.length; i++) {
            if (countRanks[i] == 4)
                return i;
        }

        return 0;
    }

    //Проверка на кикер не нужна
    public static int checkFullHouse(List<Card> player) {

        int[] countRanks = new int[15];
        int response = 0;

        for (int i = 0; i < player.size(); i++) {
            countRanks[player.get(i).getRank()] += 1;
        }
        int two = 0;
        int three = 0;
        List<Integer> listOfPairs = new ArrayList<>();
        List<Integer> listOfSets = new ArrayList<>();
        for (int i = 0; i < countRanks.length; i++) {
            if (countRanks[i] == 2) {
                two++;
                listOfPairs.add(i);
            }

            if (countRanks[i] == 3) {
                three++;
                listOfSets.add(i);
            }
        }
        if (!listOfPairs.isEmpty())
        response += listOfPairs.get(listOfPairs.size()-1)*2;

        if(listOfSets.size()==2){
            response+= listOfSets.get(1)*3;
            response+= listOfSets.get(0)*2;
            two++;
        } else if (listOfSets.size()==1)
            response+= listOfSets.get(0)*3;

        if (three != 0 && two != 0)
            return response;


        return 0;
    }

    //Проверка на кикер не нужна
    public static int[] checkFlash(List<Card> player) {


        List<Integer> listC = new ArrayList<>();
        List<Integer> listD = new ArrayList<>();
        List<Integer> listH = new ArrayList<>();
        List<Integer> listS = new ArrayList<>();

        final int MIN_NUMBER_OF_CARDS_FOR_FLASH = 5;
        final int LIST_C = 0;
        final int LIST_D = 1;
        final int LIST_H = 2;
        final int LIST_S = 3;

        int[] counter = new int[4];

        for (int i = 0; i < player.size(); i++) {
            if (player.get(i).getSuit().equals("C")) {
                counter[LIST_C] += 1;
                listC.add(player.get(i).getRank());
            } else if (player.get(i).getSuit().equals("D")) {
                counter[LIST_D] += 1;
                listD.add(player.get(i).getRank());
            } else if (player.get(i).getSuit().equals("H")) {
                counter[LIST_H] += 1;
                listH.add(player.get(i).getRank());
            } else if (player.get(i).getSuit().equals("S")) {
                counter[LIST_S] += 1;
                listS.add(player.get(i).getRank());
            }
        }



        for (int i = 0; i < counter.length; i++) {
            if (counter[i] >= MIN_NUMBER_OF_CARDS_FOR_FLASH) {
                switch (i) {
                    case LIST_C -> {
                        int[] arrayC = listC.stream()
                                .mapToInt(Integer::intValue)
                                .toArray();
                        Arrays.sort(arrayC);
                        return arrayC;
                    }
                    case LIST_D -> {
                        int[] arrayD = listD.stream()
                                .mapToInt(Integer::intValue)
                                .toArray();
                        Arrays.sort(arrayD);
                        return arrayD;
                    }
                    case LIST_H -> {
                        int[] arrayH = listH.stream()
                                .mapToInt(Integer::intValue)
                                .toArray();
                        Arrays.sort(arrayH);
                        return arrayH;
                    }
                    case LIST_S -> {
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
    public static int checkStreet(List<Card> player) {


        List<Integer> listOfRanks = new ArrayList<>();

        for (int i = 0; i < player.size(); i++) {
            listOfRanks.add(player.get(i).getRank());
        }
        Collections.sort(listOfRanks);


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
    public static int checkSet(List<Card> player) {


        int[] countRanks = new int[15];

        for (int i = 0; i < player.size(); i++) {
            countRanks[player.get(i).getRank()] += 1;
        }

        for (int i = 0; i < countRanks.length; i++) {
            if (countRanks[i] == 3)
                return i;
        }

        return 0;
    }

    //Проверка на кикер нужна
    public static int[] checkTwoPairs(List<Card> player) {


        int[] countRanks = new int[15];

        int[] response = new int[2];

        for (int i = 0; i < player.size(); i++) {
            countRanks[player.get(i).getRank()] += 1;
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
    public static int checkPair(List<Card> player) {


        int[] countRanks = new int[15];

        for (int i = 0; i < player.size(); i++) {
            countRanks[player.get(i).getRank()] += 1;
        }

        for (int i = 0; i < countRanks.length; i++) {
            if (countRanks[i] == 2)
                return i;
        }

        return 0;
    }

    // Переделал, теперь просто сортируем, так как у нас 5 кикеров
    public static int[] checkElderRank(List<Card> player) {

        int[] array = new int[7];

        for (int i = 0; i < player.size(); i++) {
            array[i] = player.get(i).getRank();
        }

        Arrays.sort(array);

        return array;
    }

    //Проверка на кикер
    public static int determineKicker(List<Card> player, int excludedRank) {

        List<Integer> remainingCards = new ArrayList<>();
        for (int i = 0; i < player.size(); i++) {
            if (player.get(i).getRank() != excludedRank) {
                remainingCards.add(player.get(i).getRank());
            }
        }

        remainingCards.sort(Collections.reverseOrder());

        return remainingCards.isEmpty() ? 0 : remainingCards.get(0);
    }

    public static List<List<Card>> removeKicker(List<Card> player1, int kiker1, List<Card> player2, int kiker2) {
        List<Card> list1 = new ArrayList<>();
        for (Card card : player1) {
            if (card.getRank() != kiker1)
                list1.add(card);
        }


        List<Card> list2 = new ArrayList<>();
        for (Card card : player2) {
            if (card.getRank() != kiker2)
                list2.add(card);
        }

        List<List<Card>> bothPlayersCards = new ArrayList<>();

        bothPlayersCards.add(list1);
        bothPlayersCards.add(list2);


        return bothPlayersCards;
    }

    public static int[] removeTwoPairs(List<Card> player) {
        List<Integer> list1 = new ArrayList<>();
        for (int i = 0; i < player.size(); i++) {
            if (player.get(i).getRank() != checkTwoPairs(player)[0] && player.get(i).getRank() != checkTwoPairs(player)[1])
                list1.add(player.get(i).getRank());
        }

        return list1.stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }
}

