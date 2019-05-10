package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.exporting;

public class UnexpectedVertexException extends RuntimeException {
    public UnexpectedVertexException(String message) {
        super(message);
    }

    public UnexpectedVertexException(Throwable cause) {
        super(cause);
    }
}
