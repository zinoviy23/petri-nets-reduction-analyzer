package hse.se.aaizmaylov.petrinetslibrary.petrinets.analysis;

class EmptyCallback implements TransformCallback {
    static final EmptyCallback EMPTY = new EmptyCallback();

    private EmptyCallback() {
    }

    @Override
    public void onDeleteTarget(Object o) {
    }

    @Override
    public void onDeleteNeighbour(Object o) {
    }

    @Override
    public void onAddTarget(Object o) {

    }

    @Override
    public void onAddNeighbour(Object o) {

    }
}
