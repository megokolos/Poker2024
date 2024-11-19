package org.example;


import org.junit.Assert;

public class Test {



    Dealer dealer = new DealerExample();

    @org.junit.Test
    public void royalFlashVSPair(){
        Board board = new Board("ASKS", "3D3H", "QSJS10S", "2H", "4S");

        PokerResult result = dealer.decideWinner(board);
        Assert.assertEquals(PokerResult.PLAYER_ONE_WIN, result);
    }

    @org.junit.Test
    public void royalFlashBoth(){
        Board board = new Board("2D3S", "3D3H", "QSJSKS", "10S", "AS");

        PokerResult result = dealer.decideWinner(board);
        Assert.assertEquals(PokerResult.DRAW, result);
    }

    @org.junit.Test
    public void ElderStreet(){
        Board board = new Board("ASKS", "9D8H", "QDJH10S", "2H", "4S");

        PokerResult result = dealer.decideWinner(board);
        Assert.assertEquals(PokerResult.PLAYER_ONE_WIN, result);
    }

    @org.junit.Test
    public void ElderFlash(){
        Board board = new Board("ASKS", "3S7S", "3D3H10S", "2S", "4S");

        PokerResult result = dealer.decideWinner(board);
        Assert.assertEquals(PokerResult.PLAYER_ONE_WIN, result);
    }
    @org.junit.Test
    public void ElderPair(){
        Board board = new Board("AS2S", "3D3H", "AHJS10S", "10H", "2D");

        PokerResult result = dealer.decideWinner(board);
        Assert.assertEquals(PokerResult.PLAYER_ONE_WIN, result);
    }

    @org.junit.Test
    public void ElderSet(){
        Board board = new Board("3D3H", "KHKS", "AHKD3C", "2H", "4S");

        PokerResult result = dealer.decideWinner(board);
        Assert.assertEquals(PokerResult.PLAYER_TWO_WIN, result);
    }
    @org.junit.Test
    public void TwoTwoPairs(){
        Board board = new Board("AS2S", "3D3H", "ADJS10S", "10H", "2H");

        PokerResult result = dealer.decideWinner(board);
        Assert.assertEquals(PokerResult.PLAYER_ONE_WIN, result);
    }

    @org.junit.Test
    public void ElderCard(){
        Board board = new Board("2S3S", "10D6H", "QSJD5H", "7H", "4S");

        PokerResult result = dealer.decideWinner(board);
        Assert.assertEquals(PokerResult.PLAYER_TWO_WIN, result);
    }

    @org.junit.Test
    public void SamePair(){
        Board board = new Board("ASKS", "AD3H", "AHJC10C", "2H", "4S");

        PokerResult result = dealer.decideWinner(board);
        Assert.assertEquals(PokerResult.PLAYER_ONE_WIN, result);
    }

    @org.junit.Test
    public void SamePairDraw(){
        Board board = new Board("3S2S", "2D3H", "AHJC10C", "2H", "4S");

        PokerResult result = dealer.decideWinner(board);
        Assert.assertEquals(PokerResult.DRAW, result);
    }

    @org.junit.Test
    public void SameTwoTwoPairs(){
        Board board = new Board("3S2S", "2D3H", "ADJS10S", "3D", "2H");

        PokerResult result = dealer.decideWinner(board);
        Assert.assertEquals(PokerResult.DRAW, result);
    }

    @org.junit.Test
    public void KareVSFullHouse(){
        Board board = new Board("KDKS", "3D3H", "KH3S3C", "2H", "4S");

        PokerResult result = dealer.decideWinner(board);
        Assert.assertEquals(PokerResult.PLAYER_TWO_WIN, result);
    }

    @org.junit.Test
    public void StreetVSSet(){
        Board board = new Board("KDKS", "3D5H", "KH3S6C", "2H", "4S");

        PokerResult result = dealer.decideWinner(board);
        Assert.assertEquals(PokerResult.PLAYER_TWO_WIN, result);
    }

    @org.junit.Test
    public void ExceptionSameCards(){
        Board board = new Board("KDKS", "3D5H", "KH3S6C", "KS", "4S");


        Assert.assertThrows(InvalidPokerBoardException.class, () -> dealer.decideWinner(board));
    }

    @org.junit.Test
    public void ExceptionNoCards(){
        Board board = new Board("KDKS", "3D5H", "KH3S6C", null, "4S");


        Assert.assertThrows(InvalidPokerBoardException.class, () -> dealer.decideWinner(board));
    }
}
