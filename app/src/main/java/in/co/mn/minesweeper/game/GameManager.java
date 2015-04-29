package in.co.mn.minesweeper.game;

import in.co.mn.minesweeper.model.GameState;
import in.co.mn.minesweeper.model.LandCell;
import in.co.mn.minesweeper.model.MineCell;

/**
 * Created by manuMohan on 15/04/28.
 */
public class GameManager {

    GameState gameState;

    public GameManager() {
        gameState = new GameState();
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void initRandomGame() {
        int i = gameState.getMines();
        while (i > 0) {
            int row = (int) ((Math.random() * 1000) % (gameState.getRows() - 1));
            int column = (int) ((Math.random() * 1000) % (gameState.getColumns() - 1));
            if (!gameState.isMineCell(row, column)) {
                gameState.setCell(new MineCell(), row, column);
                --i;
            }
        }
        initLandCells();
    }

    public void initLandCells() {
        for (int i = 0; i < gameState.getRows(); ++i)
            for (int j = 0; j < gameState.getColumns(); ++j) {
                if (gameState.isMineCell(i, j)) continue;
                LandCell landCell = new LandCell();
                landCell.setCount(getMineCount(i, j));
                gameState.setCell(landCell, i, j);
            }
    }

    public int getMineCount(int row, int column) {
        if (row < 0 || column < 0 || row > gameState.getRows() - 1 || column > gameState.getColumns() - 1)
            return 0;
        if (gameState.getGrid()[row][column] instanceof MineCell) return 0;

        int count = 0;
        if (row - 1 >= 0) {
            count += gameState.getGrid()[row - 1][column] instanceof MineCell ? 1 : 0;
            if (column - 1 >= 0) {
                count += gameState.getGrid()[row - 1][column - 1] instanceof MineCell ? 1 : 0;
            }
            if (column + 1 <= gameState.getColumns() - 1) {
                count += gameState.getGrid()[row - 1][column + 1] instanceof MineCell ? 1 : 0;
            }
        }
        if (row + 1 <= gameState.getRows() - 1) {
            count += gameState.getGrid()[row + 1][column] instanceof MineCell ? 1 : 0;
            if (column - 1 >= 0) {
                count += gameState.getGrid()[row + 1][column - 1] instanceof MineCell ? 1 : 0;
            }
            if (column + 1 <= gameState.getColumns() - 1) {
                count += gameState.getGrid()[row + 1][column + 1] instanceof MineCell ? 1 : 0;
            }
        }
        if (column - 1 >= 0) {
            count += gameState.getGrid()[row][column - 1] instanceof MineCell ? 1 : 0;
        }
        if (column + 1 <= gameState.getColumns() - 1) {
            count += gameState.getGrid()[row][column + 1] instanceof MineCell ? 1 : 0;
        }

        return count;
    }

    public void setMarked(boolean marked, int row, int column) {
        gameState.setMarked(marked, row, column);
    }

    public void performValidation() {
        boolean valid = validate();
        if (valid) gameState.setCurrentState(GameState.State.WIN);
        else
            gameState.setCurrentState(GameState.State.FAIL);
        setAllVisible();
    }

    public void click(int row, int column) {
        if(gameState.getCurrentState() != GameState.State.ON)return;
        if (row < 0 || row >= gameState.getRows() || column < 0 || column >= gameState.getColumns())
            return;
        if (gameState.isMineCell(row, column)) {
            gameState.setCurrentState(GameState.State.FAIL);
            return;
        }
        if (gameState.getGrid()[row][column].isVisible()) return;

        gameState.getGrid()[row][column].setVisible(true);

        if (((LandCell) (gameState.getGrid()[row][column])).getCount() == 0) {
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
        for (int i = 0; i < gameState.getRows(); ++i)
            for (int j = 0; j < gameState.getColumns(); ++j)
                gameState.getGrid()[i][j].setVisible(true);
    }

    public boolean validate() {
        boolean valid = true;
        for (int i = 0; i < gameState.getRows(); ++i)
            for (int j = 0; j < gameState.getColumns(); ++j)
                if (gameState.getGrid()[i][j] instanceof LandCell)
                    if (!gameState.getGrid()[i][j].isVisible())
                        valid = false;
        return valid;
    }
}
