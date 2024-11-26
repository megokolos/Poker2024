package org.example;


import java.util.ArrayList;
import java.util.List;

public class Deck {

    private final List<Card> fullDeck;

    public Deck() {
        fullDeck = generateFullDeck();
    }

    private List<Card> generateFullDeck() {
        List<Card> fullDeck = new ArrayList<>();
        for(int i=2; i<15;i++){
            fullDeck.add(new Card(i,"H"));
            fullDeck.add(new Card(i,"D"));
            fullDeck.add(new Card(i,"C"));
            fullDeck.add(new Card(i,"S"));
        }
        return fullDeck;
    }

    public void drawCard(Card card) {
        fullDeck.remove(card);
    }


    public List<Card> getDeck() {
        return fullDeck;
    }
}



