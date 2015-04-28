package in.co.mn.minesweeper.game;

import android.test.AndroidTestCase;

import in.co.mn.minesweeper.model.Cell;
import in.co.mn.minesweeper.model.LandCell;
import in.co.mn.minesweeper.model.MineCell;

/**
 * Created by manuMohan on 15/04/28.
 */
public class GameStateTest extends AndroidTestCase {
    GameState gameState;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        gameState = new GameState();
    }


    public void testCellPresence() {
        gameState.setCell(new LandCell(), 3, 4);
        assertEquals("Not LandCell", true, gameState.isLandCell(3, 4));

        gameState.setCell(new MineCell(), 5, 6);
        assertEquals("Not Minecell", true, gameState.isMineCell(5, 6));
    }

    public void testRandomGameInit() {
        gameState.initRandomGame();
        Cell[][] grid = gameState.getGrid();
        int mineCount = 0, landCount = 0;
        for (int i = 0; i < gameState.getRows(); ++i)
            for (int j = 0; j < gameState.getColumns(); ++j) {
                assertNotNull(grid[i][j]);
                if (grid[i][j] instanceof MineCell) mineCount++;
                else if (grid[i][j] instanceof LandCell) landCount++;
            }
        assertEquals("Inconsistency with grid", gameState.getColumns() * gameState.getRows(), mineCount + landCount);
    }

    public void testMineCount() {
        gameState.setCell(new MineCell(), 2, 3);
        gameState.setCell(new MineCell(), 2, 4);
        gameState.setCell(new MineCell(), 4, 5);
        assertEquals("Minecount not equal", 3, gameState.getMineCount(3, 4));

        gameState.setCell(new MineCell(), 4, 3);
        assertEquals("Minecount not equal", 4, gameState.getMineCount(3, 4));
    }

    public void testValidation() {
        gameState.initRandomGame();
        gameState.setAllVisible();
        assertEquals("not valid", true, gameState.validate());
    }
}
