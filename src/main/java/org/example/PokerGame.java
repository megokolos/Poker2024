package org.example;

import java.util.*;


public class PokerGame {
    public static void main(String[] args) {



        Dealer dealer = new DealerExample();

        Board board = dealer.dealCardsToPlayers();
        board = dealer.dealFlop(board);
        board = dealer.dealTurn(board);
        board = dealer.dealRiver(board);


        try {
            System.out.println(dealer.decideWinner(board));
        }catch (InvalidPokerBoardException e) {
            System.out.println(e.getMessage());

        }



    }

}
