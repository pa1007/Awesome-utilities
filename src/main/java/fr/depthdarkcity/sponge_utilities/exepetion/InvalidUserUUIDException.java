package fr.depthdarkcity.sponge_utilities.exepetion;

public class InvalidUserUUIDException extends Exception {

    public InvalidUserUUIDException() {
        super("The User UUID is not referenced on this server");
    }

    public InvalidUserUUIDException(String message) {
        super(message);
    }

    public InvalidUserUUIDException(String message, Throwable cause) {
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
