package in.co.mn.minesweeper.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import in.co.mn.minesweeper.R;
import in.co.mn.minesweeper.game.GameManager;
import in.co.mn.minesweeper.model.GameState;
import in.co.mn.minesweeper.view.MineSweeperView;


public class MainActivity extends AppCompatActivity implements MineSweeperView.DataSource {

    private MineSweeperView mMineSweeperView;
    private GameManager mGameManager;

    private static final String TAG_GAME_MANAGER = "game_manager";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            mGameManager = (GameManager) savedInstanceState.getSerializable(TAG_GAME_MANAGER);
        } else
            startNewGame();
        initViews();

    }

    private void initViews() {
        mMineSweeperView = (MineSweeperView) findViewById(R.id.view_minesweeper);
        mMineSweeperView.setDataSource(this);

        Button newGameButton = (Button) findViewById(R.id.action_new);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewGame();
                mMineSweeperView.invalidate();
            }
        });
        Button validateButton = (Button) findViewById(R.id.action_validate);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGameManager.performValidation();
                checkGameState();
                mMineSweeperView.invalidate();
            }
        });
        Button cheatButton = (Button) findViewById(R.id.action_cheat);
        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGameManager.setAllVisible();
                mMineSweeperView.invalidate();
            }
        });
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
        checkGameState();
        mMineSweeperView.invalidate();
    }

    private void startNewGame() {
        mGameManager = new GameManager();
        mGameManager.initRandomGame();
    }

    private void checkGameState() {
        switch (mGameManager.getGameState().getCurrentState()) {
            case FAIL:
                showFailureAlert();
                break;
            case WIN:
                showSuccessAlert();
                break;
        }
    }

    private void showFailureAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Failure")
                .setMessage("You stepped on a Mine! :(")
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startNewGame();
                        mMineSweeperView.invalidate();
                    }
                });
        builder.show();
    }

    private void showSuccessAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Success")
                .setMessage("You cleared the Mine Field!")
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        builder.show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(TAG_GAME_MANAGER, mGameManager);
    }
}
