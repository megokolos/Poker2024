package org.example;

import java.util.Objects;

public class Card {

    private final int rank;

    private final String suit;

    public Card(int rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public int getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    @Override
    public String toString() {

        if(rank<=10)
            return rank+suit;
        else {
            String rankByLetters = "";

            switch (rank) {
                case (11) -> {
                    rankByLetters = "J";
                    break;
                }
                case (12) -> {
                    rankByLetters = "Q";
                    break;
                }
                case (13) -> {
                    rankByLetters = "K";
                    break;
                }
                case (14) -> {
                    rankByLetters = "A";
                    break;
                }
            }

            return rankByLetters + suit;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return rank == card.rank && Objects.equals(suit, card.suit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }
}
