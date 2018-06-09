package fr.depthdarkcity.sponge_utilities.exepetion;

public class InvalidUserNameException extends Exception {

    public InvalidUserNameException() {
        super("The player name is not referenced on this server");
    }

    public InvalidUserNameException(String message) {
        super(message);
    }

    public InvalidUserNameException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }

    @Override
    public synchronized Throwable getCause() {
        return super.getCause();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
