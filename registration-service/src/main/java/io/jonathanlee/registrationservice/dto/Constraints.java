package io.jonathanlee.registrationservice.dto;

public abstract class Constraints {

    public static final int MIN_NAME_LENGTH = 1;
    public static final int MAX_NAME_LENGTH = 32;
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_PASSWORD_LENGTH = 64;

    private Constraints() {
        // Private constructor to hide the implicit public one
    }

}
