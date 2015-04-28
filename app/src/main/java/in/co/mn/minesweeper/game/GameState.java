package in.co.mn.minesweeper.game;

import in.co.mn.minesweeper.model.Cell;
import in.co.mn.minesweeper.model.LandCell;
import in.co.mn.minesweeper.model.MineCell;

/**
 * Created by manuMohan on 15/04/28.
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

    public State getCurrentState() {
        return currentState;
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

    public void initRandomGame() {
        int i = NUMBER_OF_MINES;
        while (i > 0) {
            int row = (int) ((Math.random() * 1000) % (getRows() - 1));
            int column = (int) ((Math.random() * 1000) % (getColumns() - 1));
            if (!isMineCell(row, column)) {
                setCell(new MineCell(), row, column);
                --i;
            }
        }
        initLandCells();
    }

    public void initLandCells() {
        for (int i = 0; i < getRows(); ++i)
            for (int j = 0; j < getColumns(); ++j) {
                LandCell landCell = new LandCell();
                landCell.setCount(getMineCount(i, j));
                grid[i][j] = landCell;
            }
    }

    public int getMineCount(int row, int column) {
        if (row < 0 || column < 0 || row > getRows() - 1 || column > getColumns() - 1) return 0;
        if (grid[row][column] instanceof MineCell) return 0;

        int count = 0;
        if (row - 1 >= 0) {
            count += grid[row - 1][column] instanceof MineCell ? 1 : 0;
            if (column - 1 >= 0) {
                count += grid[row - 1][column - 1] instanceof MineCell ? 1 : 0;
            }
            if (column + 1 <= getColumns() - 1) {
                count += grid[row - 1][column + 1] instanceof MineCell ? 1 : 0;
            }
        }
        if (row + 1 <= getRows() - 1) {
            count += grid[row + 1][column] instanceof MineCell ? 1 : 0;
            if (column - 1 >= 0) {
                count += grid[row + 1][column - 1] instanceof MineCell ? 1 : 0;
            }
            if (column + 1 <= getColumns() - 1) {
                count += grid[row + 1][column + 1] instanceof MineCell ? 1 : 0;
            }
        }
        if (column - 1 >= 0) {
            count += grid[row][column - 1] instanceof MineCell ? 1 : 0;
        }
        if (column + 1 <= getColumns() - 1) {
            count += grid[row][column + 1] instanceof MineCell ? 1 : 0;
        }

        return count;
    }

    public void setMarked(boolean marked, int row, int column) {
        grid[row][column].setMarked(marked);
    }

    public void performValidation() {
        boolean valid = validate();
        if (valid) currentState = State.WIN;
        else
            currentState = State.FAIL;
    }

    private void click(int column, int row) {
        if (row < 0 || row >= getRows() || column < 0 || column >= getColumns()) return;
        if (isMineCell(row, column)) {
            currentState = State.FAIL;
            return;
        }

        grid[row][column].setVisible(true);
        if (((LandCell) (grid[row][column])).getCount() == 0) {
            click(row - 1, column);
            click(row - 1, column - 1);
            click(row - 1, column + 1);
            click(row + 1, column);
            click(row + 1, column - 1);
            click(row + 1, column + 1);
            click(row, column + 1);
            click(row, column - 1);
        }

    }

    public void setAllVisible() {
        for (int i = 0; i < getRows(); ++i)
            for (int j = 0; j < getColumns(); ++i)
                grid[i][j].setVisible(true);
    }

    public boolean validate() {
        boolean valid = true;
        for (int i = 0; i < getRows(); ++i)
            for (int j = 0; j < getColumns(); ++i)
                if (grid[i][j] instanceof LandCell)
                    if (!grid[i][j].isVisible())
                        valid = false;
        return valid;
    }
}
