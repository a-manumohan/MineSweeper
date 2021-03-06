package in.co.mn.minesweeper.game;

import android.test.AndroidTestCase;

import in.co.mn.minesweeper.model.Cell;
import in.co.mn.minesweeper.model.LandCell;
import in.co.mn.minesweeper.model.MineCell;

/**
 * Created by manuMohan on 15/04/28.
 */
public class GameManagerTest extends AndroidTestCase {
    GameManager gameManager;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        gameManager = new GameManager();
    }


    public void testCellPresence() {
        gameManager.getGameState().setCell(new LandCell(), 3, 4);
        assertEquals("Not LandCell", true, gameManager.getGameState().isLandCell(3, 4));

        gameManager.getGameState().setCell(new MineCell(), 5, 6);
        assertEquals("Not Minecell", true, gameManager.getGameState().isMineCell(5, 6));
    }

    public void testRandomGameInit() {
        gameManager.initRandomGame();
        Cell[][] grid = gameManager.getGameState().getGrid();
        int mineCount = 0, landCount = 0;
        for (int i = 0; i < gameManager.getGameState().getRows(); ++i)
            for (int j = 0; j < gameManager.getGameState().getColumns(); ++j) {
                assertNotNull(grid[i][j]);
                if (grid[i][j] instanceof MineCell) mineCount++;
                else if (grid[i][j] instanceof LandCell) landCount++;
            }
        assertEquals("Inconsistency with grid", gameManager.getGameState().getColumns() * gameManager.getGameState().getRows(), mineCount + landCount);
    }

    public void testMineCount() {
        gameManager.getGameState().setCell(new MineCell(), 2, 3);
        gameManager.getGameState().setCell(new MineCell(), 2, 4);
        gameManager.getGameState().setCell(new MineCell(), 4, 5);
        assertEquals("Minecount not equal", 3, gameManager.getMineCount(3, 4));

        gameManager.getGameState().setCell(new MineCell(), 4, 3);
        assertEquals("Minecount not equal", 4, gameManager.getMineCount(3, 4));
    }

    public void testValidation() {
        gameManager.initRandomGame();
        gameManager.setAllVisible();
        assertEquals("not valid", true, gameManager.validate());
    }
}
