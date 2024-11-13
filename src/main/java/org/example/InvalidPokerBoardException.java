package org.example;

public class InvalidPokerBoardException extends RuntimeException {

    private String message;

    public InvalidPokerBoardException(String message) {
        this.message = message;
    }

    public InvalidPokerBoardException(String message, String message1) {
        super(message);
        this.message = message1;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
