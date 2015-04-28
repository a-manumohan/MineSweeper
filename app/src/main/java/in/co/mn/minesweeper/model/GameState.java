package in.co.mn.minesweeper.model;

/**
 * Created by manuMohan on 15/04/28.
 */
public class GameState {
    private static final Integer ROWS = 8;
    private static final Integer COLUMNS = 8;
    private static final Integer NUMBER_OF_MINES = 10;

    private Cell grid[][];

    public GameState() {
        grid = new Cell[ROWS][COLUMNS];
    }
}
