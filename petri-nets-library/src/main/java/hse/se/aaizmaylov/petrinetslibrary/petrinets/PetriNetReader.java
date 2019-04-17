package hse.se.aaizmaylov.petrinetslibrary.petrinets;

public interface PetriNetReader<PlaceT extends LabeledVertex, TransitionT extends LabeledVertex> {
    PetriNet<PlaceT, TransitionT> read(String path) throws PetriNetReadingException;

    class PetriNetReadingException extends Exception {
        public PetriNetReadingException(String message) {
            super(message);
        }

        public PetriNetReadingException(Throwable cause) {
            super(cause);
        }
    }
}
