package sanity.nil.medassurance.exceptions;

public class DuplicatedUserEntryException extends RuntimeException{

    public DuplicatedUserEntryException(String entry) {
        super("User with such " + entry + " already exists.");
    }
}
