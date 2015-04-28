package in.co.mn.minesweeper.model;

/**
 * Created by manuMohan on 15/04/29.
 */
public class GameState {
    private static final Integer ROWS = 8;
    private static final Integer COLUMNS = 8;
    private static final Integer NUMBER_OF_MINES = 10;
    private Cell grid[][];

    public enum State {
        ON,
        FAIL,
        WIN
    }

    private State currentState;

    public GameState(){
        grid = new Cell[ROWS][COLUMNS];
        currentState = State.ON;
    }

    public int getColumns() {
        return COLUMNS;
    }

    public int getRows() {
        return ROWS;
    }

    public int getMines(){return NUMBER_OF_MINES;}

    public State getCurrentState() {
        return currentState;
    }

    public Cell[][] getGrid() {
        return grid;
    }
}
