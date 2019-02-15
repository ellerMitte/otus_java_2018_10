package dbservice.Exceptions;

/**
 * @author Igor on 15.02.19.
 */
public class IdFieldException extends ReflectiveOperationException {
    public IdFieldException(String message) {
        super(message);
    }
}
