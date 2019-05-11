package hse.se.aaizmaylov.petrinetscalculationserver.services;

public class AnalysisException extends RuntimeException {
    public AnalysisException(String message) {
        super(message);
    }

    public AnalysisException(String message, Throwable cause) {
        super(message, cause);
    }

    public AnalysisException(Throwable cause) {
        super(cause);
    }
}
