package in.co.mn.minesweeper.model;

import java.io.Serializable;

/**
 * Created by manuMohan on 15/04/29.
 */
public class GameState implements Serializable{
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

    public GameState() {
        grid = new Cell[ROWS][COLUMNS];
        currentState = State.ON;
    }

    public int getColumns() {
        return COLUMNS;
    }

    public int getRows() {
        return ROWS;
    }

    public int getMines() {
        return NUMBER_OF_MINES;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public void setCell(Cell cell, int row, int column) {
        if (row > grid.length - 1 || column > grid[row].length)
            throw new ArrayIndexOutOfBoundsException("Grid Overflow");
        grid[row][column] = cell;
    }

    public boolean isMineCell(int row, int column) {
        return grid[row][column] != null && grid[row][column] instanceof MineCell;
    }

    public boolean isLandCell(int row, int column) {
        return grid[row][column] != null && grid[row][column] instanceof LandCell;
    }
    public void setMarked(boolean marked, int row, int column) {
        grid[row][column].setMarked(marked);
    }
}
