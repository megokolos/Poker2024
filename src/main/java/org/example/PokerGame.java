package org.example;

import java.util.*;


public class PokerGame {
    public static void main(String[] args) {


        Deck deck = new Deck();
        Dealer dealer = new DealerExample(deck);

        Board board = dealer.dealCardsToPlayers();
        board = dealer.dealFlop(board);
        board = dealer.dealTurn(board);
        board = dealer.dealRiver(board);



        System.out.println(dealer.decideWinner(board));


    }

}
