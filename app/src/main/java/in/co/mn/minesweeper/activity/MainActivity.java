package in.co.mn.minesweeper.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import in.co.mn.minesweeper.R;
import in.co.mn.minesweeper.game.GameManager;
import in.co.mn.minesweeper.model.GameState;
import in.co.mn.minesweeper.view.MineSweeperView;


public class MainActivity extends AppCompatActivity implements MineSweeperView.DataSource {

    private MineSweeperView mMineSweeperView;
    private GameManager mGameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGameManager = new GameManager();
        mGameManager.initRandomGame();

        initViews();

    }

    private void initViews() {
        mMineSweeperView = (MineSweeperView) findViewById(R.id.view_minesweeper);
        mMineSweeperView.setDataSource(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public GameState getGameState() {
        return mGameManager.getGameState();
    }

    @Override
    public void click(int row, int column) {
        mGameManager.click(row, column);
        mMineSweeperView.invalidate();
    }
}
