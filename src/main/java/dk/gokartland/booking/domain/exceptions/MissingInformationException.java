package dk.gokartland.booking.domain.exceptions;

public class MissingInformationException extends Exception {
    public MissingInformationException(String message) {
        super(message);
    }
}
