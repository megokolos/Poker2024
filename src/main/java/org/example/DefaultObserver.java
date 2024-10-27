package org.example;

public class DefaultObserver implements PokerObserver {
    public Chanse calculateWinChanses(Board board) {
        return new Chanse(50, 50);
    }
}
