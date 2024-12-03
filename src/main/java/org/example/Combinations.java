package org.example;

import java.util.Arrays;
import java.util.List;

import static org.example.CombinationsCheck.*;
import static org.example.CombinationsCheck.checkElderRank;

public enum Combinations {
    ROYAL_FLASH(10),
    STREET_FLASH(9),
    QUAD(8),
    FULL_HOUSE(7),
    FLASH(6),
    STREET(5),
    SET(4),
    TWO_PAIRS(3),
    PAIR(2),
    ELDER_CARD(1);

    final int value;

    Combinations(int value) {
        this.value = value;
    }

    public static PokerResult checkTheInnerDifference(Combinations combination, List<Card> player1AndTable, List<Card> player2AndTable) {

        switch (combination) {
            case ROYAL_FLASH -> {
                return checkTheDifferenceFlashRoyal();
            }
            case STREET_FLASH -> {
                return checkTheDifferenceStreetFlash(player1AndTable, player2AndTable);
            }
            case QUAD -> {
                return checkTheDifferenceQuad(player1AndTable, player2AndTable);
            }
            case FULL_HOUSE -> {
                return checkTheDifferenceFullHouse(player1AndTable, player2AndTable);
            }
            case FLASH -> {
                return checkTheDifferenceFlash(player1AndTable, player2AndTable);
            }
            case STREET -> {
                return checkTheDifferenceStreet(player1AndTable, player2AndTable);
            }
            case SET -> {
                return checkTheDifferenceSet(player1AndTable, player2AndTable);
            }
            case TWO_PAIRS -> {
                return checkTheDifferenceTwoPairs(player1AndTable, player2AndTable);
            }
            case PAIR -> {
                return checkTheDifferencePair(player1AndTable, player2AndTable);
            }
            case ELDER_CARD -> {
                return checkTheDifferenceElderCard(player1AndTable,player2AndTable);
            }
        }
        return PokerResult.DRAW;
    }

    public static PokerResult checkTheDifferenceFlashRoyal(){
        return PokerResult.DRAW;
    }

    public static PokerResult checkTheDifferenceStreetFlash(List<Card> player1AndTable, List<Card> player2AndTable){
        if (checkStreetFlash(player1AndTable) == checkStreetFlash(player2AndTable))
            return PokerResult.DRAW;
        else if (checkStreetFlash(player1AndTable) > checkStreetFlash(player2AndTable))
            return PokerResult.PLAYER_ONE_WIN;
        else
            return PokerResult.PLAYER_TWO_WIN;
    }

    public static PokerResult checkTheDifferenceQuad(List<Card> player1AndTable, List<Card> player2AndTable){
        if (checkFourOfAKind(player1AndTable) == checkFourOfAKind(player2AndTable)) {
            int kiker1 = determineKicker(player1AndTable, checkFourOfAKind(player1AndTable));
            int kiker2 = determineKicker(player2AndTable, checkFourOfAKind(player2AndTable));

            if (kiker1 == kiker2)
                return PokerResult.DRAW;
            else if (kiker1 > kiker2)
                return PokerResult.PLAYER_ONE_WIN;
            else
                return PokerResult.PLAYER_TWO_WIN;
        } else if (checkFourOfAKind(player1AndTable) > checkFourOfAKind(player2AndTable))
            return PokerResult.PLAYER_ONE_WIN;
        else
            return PokerResult.PLAYER_TWO_WIN;
    }

    public static PokerResult checkTheDifferenceFullHouse(List<Card> player1AndTable, List<Card> player2AndTable){
        if (checkFullHouse(player1AndTable) == checkFullHouse(player2AndTable))
            return PokerResult.DRAW;
        else if (checkFullHouse(player1AndTable) > checkFullHouse(player2AndTable))
            return PokerResult.PLAYER_ONE_WIN;
        else
            return PokerResult.PLAYER_TWO_WIN;
    }

    public static PokerResult checkTheDifferenceFlash(List<Card> player1AndTable, List<Card> player2AndTable){
        for (int i = 0; i < 5; i++) {
            if (checkFlash(player1AndTable)[checkFlash(player1AndTable).length - 1 - i] > checkFlash(player2AndTable)[checkFlash(player2AndTable).length - 1 - i])
                return PokerResult.PLAYER_ONE_WIN;
            if (checkFlash(player1AndTable)[checkFlash(player1AndTable).length - 1 - i] < checkFlash(player2AndTable)[checkFlash(player2AndTable).length - 1 - i])
                return PokerResult.PLAYER_TWO_WIN;
        }
        return PokerResult.DRAW;
    }

    public static PokerResult checkTheDifferenceStreet(List<Card> player1AndTable, List<Card> player2AndTable){
        if (checkStreet(player1AndTable) == checkStreet(player2AndTable))
            return PokerResult.DRAW;
        else if (checkStreet(player1AndTable) > checkStreet(player2AndTable))
            return PokerResult.PLAYER_ONE_WIN;
        else
            return PokerResult.PLAYER_TWO_WIN;
    }

    public static PokerResult checkTheDifferenceSet(List<Card> player1AndTable, List<Card> player2AndTable){
        if (checkSet(player1AndTable) == checkSet(player2AndTable)) {
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
        } else  {
            return PokerResult.PLAYER_TWO_WIN;
        }
    }

    public static PokerResult checkTheDifferenceTwoPairs(List<Card> player1AndTable, List<Card> player2AndTable){
        if (checkTwoPairs(player1AndTable)[0] > checkTwoPairs(player2AndTable)[0]) {
            return PokerResult.PLAYER_ONE_WIN;
        } else if (checkTwoPairs(player1AndTable)[0] < checkTwoPairs(player2AndTable)[0]) {
            return PokerResult.PLAYER_TWO_WIN;
        } else if (checkTwoPairs(player1AndTable)[0] == checkTwoPairs(player2AndTable)[0] &&
                checkTwoPairs(player1AndTable)[1] == checkTwoPairs(player2AndTable)[1]) {

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
                checkTwoPairs(player1AndTable)[1] > checkTwoPairs(player2AndTable)[1]) {
            return PokerResult.PLAYER_ONE_WIN;
        } else if (checkTwoPairs(player1AndTable)[0] == checkTwoPairs(player2AndTable)[0] &&
                checkTwoPairs(player1AndTable)[1] < checkTwoPairs(player2AndTable)[1]) {
            return PokerResult.PLAYER_TWO_WIN;
        }
        return PokerResult.DRAW;
    }

    public static PokerResult checkTheDifferencePair(List<Card> player1AndTable, List<Card> player2AndTable){
        if (checkPair(player1AndTable) == checkPair(player2AndTable)) {


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
        } else if (checkPair(player1AndTable) > checkPair(player2AndTable))
            return PokerResult.PLAYER_ONE_WIN;
        else
            return PokerResult.PLAYER_TWO_WIN;
    }

    public static PokerResult checkTheDifferenceElderCard(List<Card> player1AndTable, List<Card> player2AndTable) {
        int[] sortedArray1 = checkElderRank(player1AndTable);
        int[] sortedArray2 = checkElderRank(player2AndTable);

        for (int i = sortedArray1.length - 1; i > 1; i--) {
            if (sortedArray1[i] == sortedArray2[i])
                continue;
            else if (sortedArray1[i] > sortedArray2[i])
                return PokerResult.PLAYER_ONE_WIN;
            else
                return PokerResult.PLAYER_TWO_WIN;
        }
        return PokerResult.DRAW;
    }



}
