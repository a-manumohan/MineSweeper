package in.co.mn.minesweeper.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import in.co.mn.minesweeper.R;
import in.co.mn.minesweeper.model.Cell;
import in.co.mn.minesweeper.model.GameState;
import in.co.mn.minesweeper.model.LandCell;

/**
 * Created by manuMohan on 15/04/28.
 */
public class MineSweeperView extends View {
    private DataSource mDataSource;

    private Paint mBackgroundPaint;
    private Paint mCellBorderPaint;
    private Paint mCellFillPaint;
    private Paint mCellVisiblePaint;
    private Paint mCountPaint;

    private int mSide;
    private float mCellWidth;
    private float mCellHeight;
    private final float CELL_PADDING = 2;

    private Bitmap mBombBitmap;
    private RectF mBombRectF;

    private GestureDetector mGestureDetector;

    public MineSweeperView(Context context) {
        super(context);
        setWillNotDraw(false);
        init();
    }

    public MineSweeperView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        init();
    }

    public MineSweeperView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        init();
    }

    private void init() {

        Resources res = getContext().getResources();
        mBackgroundPaint = new Paint(0);
        mBackgroundPaint.setColor(res.getColor(R.color.bg_game_view));

        mCellBorderPaint = new Paint(0);
        mCellBorderPaint.setColor(res.getColor(R.color.border_cell));
        mCellBorderPaint.setStyle(Paint.Style.STROKE);

        mCellFillPaint = new Paint(0);
        mCellFillPaint.setColor(res.getColor(R.color.fill_cell));
        mCellFillPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mCellVisiblePaint = new Paint(0);
        mCellVisiblePaint.setColor(res.getColor(R.color.fill_cell_visible));
        mCellVisiblePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mCountPaint = new Paint(0);
        mCountPaint.setColor(res.getColor(R.color.count));
        mCountPaint.setTextSize(25.0f);
        mCountPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        mBombRectF = new RectF();

        mBombBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_explosive_bomb);

        mGestureDetector = new GestureDetector(this.getContext(), new GestureListener());


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mSide = w > h ? h : w;
        setMeasuredDimension(mSide, mSide);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        canvas.drawRect(0, 0, mSide, mSide, mBackgroundPaint);
        if (mDataSource == null) return;
        if (mDataSource.getGameState() == null)
            throw new NullPointerException("GameState cannot be null");
        GameState gameState = mDataSource.getGameState();
        Cell[][] grid = gameState.getGrid();

        mCellWidth = mSide / gameState.getColumns();
        mCellHeight = mSide / gameState.getRows();
        float x, y = 0;
        for (int i = 0; i < gameState.getRows(); ++i) {
            x = 0;
            for (int j = 0; j < gameState.getColumns(); ++j) {
                canvas.drawRect(x, y, x + mCellWidth, y + mCellHeight, mCellBorderPaint);
                if (!grid[i][j].isVisible()) {
                    canvas.drawRect(x + CELL_PADDING, y + CELL_PADDING, x + mCellWidth - CELL_PADDING, y + mCellHeight - CELL_PADDING, mCellFillPaint);
                } else {
                    canvas.drawRect(x + CELL_PADDING, y + CELL_PADDING, x + mCellWidth - CELL_PADDING, y + mCellHeight - CELL_PADDING, mCellVisiblePaint);
                    if (grid[i][j] instanceof LandCell) {
                        int count = ((LandCell) (grid[i][j])).getCount();
                        if (count != 0) {
                            float countX = (x + x + mCellWidth) / 2 - (mCountPaint.getTextSize() / 2);
                            float countY = (y + y + mCellHeight) / 2 - ((mCountPaint.ascent() + mCountPaint.descent()) / 2);
                            canvas.drawText(count + "", countX, countY, mCountPaint);
                        }
                    } else {
                        mBombRectF.set((x + CELL_PADDING),
                                (y + CELL_PADDING),
                                (x + mCellWidth - CELL_PADDING),
                                (y + mCellHeight - CELL_PADDING));
                        canvas.drawBitmap(mBombBitmap, null, mBombRectF, null);
                    }
                }
                x += mCellWidth;
            }
            y += mCellHeight;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    private int getColumn(int x) {
        return (int) (x / mCellWidth);
    }

    private int getRow(int y) {
        return (int) (y / mCellHeight);
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            int x = (int) e.getX();
            int y = (int) e.getY();
            postInvalidate();
            mDataSource.click(getRow(y), getColumn(x));
            return super.onSingleTapUp(e);
        }
    }

    public DataSource getDataSource() {
        return mDataSource;
    }

    public void setDataSource(DataSource mDataSource) {
        this.mDataSource = mDataSource;
    }

    public interface DataSource {
        public GameState getGameState();

        public void click(int row, int column);
    }
}
