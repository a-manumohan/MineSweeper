package in.co.mn.minesweeper.model;

/**
 * Created by manuMohan on 15/04/28.
 */
public abstract class Cell {
    private boolean marked = false;

    private boolean visible = false;

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
