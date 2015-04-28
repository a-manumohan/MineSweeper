package in.co.mn.minesweeper.model;

/**
 * Created by manuMohan on 15/04/28.
 */
public abstract class Cell {
    private boolean marked;

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }
}
