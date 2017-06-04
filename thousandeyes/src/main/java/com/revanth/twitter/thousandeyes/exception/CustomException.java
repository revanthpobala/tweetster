package com.revanth.twitter.thousandeyes.exception;

/**
 * Class for global exception handler.
 * Created by Revanth on 5/28/2017.
 */
public class CustomException extends Exception {
    public CustomException() {
    }

    /**
     * Constructs an exception with the message.
     *
     * @param message Message from the exception.
     */
    public CustomException(String message) {
        super(message);
    }

}
