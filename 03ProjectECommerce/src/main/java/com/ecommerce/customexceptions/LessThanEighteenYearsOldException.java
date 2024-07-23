package com.ecommerce.customexceptions;

public class LessThanEighteenYearsOldException extends RuntimeException {

	/**
     * Serialization version.
     */
	private static final long serialVersionUID = 2702327731970700393L;
	
	/**
     * Constructs a new "less than eighteen years old" exception with the specified message.
     *
     * @param message  the message to use for this exception, may be null
     */
    public LessThanEighteenYearsOldException(String message) {
        super(message);
    }

    /**
     * Constructs a new "less than eighteen years old" exception with the specified message and cause.
     *
     * @param message  the message to use for this exception, may be null
     * @param cause  the cause of the exception, may be null
     */
    public LessThanEighteenYearsOldException(String message, Throwable cause) {
        super(message, cause);
    }

}
