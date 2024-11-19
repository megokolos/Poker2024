package org.example;

public class InvalidPokerBoardException extends RuntimeException {

    private String message;

    public InvalidPokerBoardException(String message) {
        this.message = message;
    }


    @Override
    public String getMessage() {
        return message;
    }
}
