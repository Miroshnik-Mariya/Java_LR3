package Exception;

public class InvalidDataException extends RuntimeException {//необъявляемое, для ввода данных
    public InvalidDataException(String message) {
        super(message);
    }
}
