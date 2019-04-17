package hse.se.aaizmaylov.petrinetslibrary.petrinets.basic.importing;

public class PreprocessException extends Exception {
    PreprocessException(Throwable cause) {
        super("Cannot preprocess", cause);
    }

    PreprocessException(String message) {
        super(message);
    }
}
