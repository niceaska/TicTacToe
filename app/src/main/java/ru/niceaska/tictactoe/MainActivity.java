package ru.niceaska.tictactoe;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import static ru.niceaska.tictactoe.TicTacToeField.Figure.CIRCLE;
import static ru.niceaska.tictactoe.TicTacToeField.Figure.CROSS;
import static ru.niceaska.tictactoe.TicTacToeField.Figure.NONE;

public class MainActivity extends Activity {

    private GridLayout gridLayout;
    private TicTacToeField ticTacToeField;
    final static String PARCEL_FIELD = "TicTacToe Field";
    private LinearLayout winnerDialog;
    private TextView winnerCaption;
    private Button againButton;
    private Button exitButton;
    private TextView circleCaption;
    private TextView crossCaption;
    final static String PARCEL_GAME = "TicTacToe Game";
    final static int FIELD_SIZE = 3;
    private TicTacToeGame ticTacToeGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setGridListeners();
        initButtonListeners();
    }

    private void initButtonListeners() {
        againButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPoints();
                ticTacToeGame.setGameRun(true);
                clearField();
                winnerDialog.setVisibility(View.GONE);
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDestroy();
            }
        });
    }

    private void init() {
        gridLayout = findViewById(R.id.tic_tac_field);
        winnerDialog = findViewById(R.id.winner_dialog);
        winnerCaption = findViewById(R.id.winner_caption);
        againButton = findViewById(R.id.again_button);
        exitButton = findViewById(R.id.exit_button);
        circleCaption = findViewById(R.id.circle);
        crossCaption = findViewById(R.id.cross);
        ticTacToeField = new TicTacToeField(FIELD_SIZE);
        ticTacToeGame = new TicTacToeGame(false, true, 0, 0);
    }

    private void setPoints() {
        circleCaption.setText(String.valueOf(ticTacToeGame.getCirclePoints()));
        crossCaption.setText(String.valueOf(ticTacToeGame.getCrossPoints()));
    }

    private void clearField() {
        ticTacToeField = new TicTacToeField(3);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View v = gridLayout.getChildAt(i);
            ((ImageView) v).setImageDrawable(null);
        }
    }

    private void setGridListeners() {
        int contentWidth = getResources().getDisplayMetrics().widthPixels;
        int contentHeight = getResources().getDisplayMetrics().heightPixels;
        int min = Math.min(contentHeight, contentWidth);
        int size = min / FIELD_SIZE;
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View v = gridLayout.getChildAt(i);
            v.setTag(new Point(i % FIELD_SIZE, i / FIELD_SIZE));
            v.setMinimumWidth(size);
            v.setMinimumHeight(size);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ticTacToeGame.isGameRun()) {
                        Point point = (Point) v.getTag();
                        if (ticTacToeField.getFigure(point.x, point.y) == NONE) {
                            ((ImageView) v).setImageDrawable(getDrawable(
                                    ticTacToeGame.isGamer() ? R.drawable.circle : R.drawable.cross)
                            );
                            ticTacToeField.setFigure(point.x, point.y, ticTacToeGame.isGamer() ?
                                    CIRCLE : CROSS);
                            ticTacToeGame.setGamer(!ticTacToeGame.isGamer());
                        }
                        checkWinner(ticTacToeField.getWinner());
                    }
                }
            });
        }
    }


    private void checkWinner(TicTacToeField.Figure winner) {

        if (winner != NONE || ticTacToeField.isFull()) {
            switch (winner) {
                case CIRCLE:
                    winnerCaption.setText(getResources().getString(R.string.zero));
                    ticTacToeGame.setCirclePoints(ticTacToeGame.getCirclePoints() + 1);
                    break;
                case CROSS:
                    winnerCaption.setText(getResources().getString(R.string.cross));
                    ticTacToeGame.setCrossPoints(ticTacToeGame.getCrossPoints() + 1);
                    break;
                default:
                    winnerCaption.setText(getResources().getString(R.string.no_winner));
                    break;
            }
            ticTacToeGame.setGameRun(false);
            ticTacToeGame.setWinner(winner.ordinal());
            winnerDialog.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(PARCEL_FIELD, ticTacToeField);
        outState.putParcelable(PARCEL_GAME, ticTacToeGame);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ticTacToeGame = savedInstanceState.getParcelable(PARCEL_GAME);
        ticTacToeField = savedInstanceState.getParcelable(PARCEL_FIELD);
        if (!ticTacToeGame.isGameRun()) {
            winnerDialog.setVisibility(View.VISIBLE);
        }
        setPoints();
        restoreGameField();
    }

    private void restoreGameField() {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View v = gridLayout.getChildAt(i);
            Point point = (Point) v.getTag();
            TicTacToeField.Figure figure = ticTacToeField.getFigure(point.x, point.y);
            if (figure != NONE) {
                ((ImageView) v).setImageDrawable(getDrawable(
                        figure == CIRCLE ? R.drawable.circle : R.drawable.cross)
                );
            }
        }
    }
}