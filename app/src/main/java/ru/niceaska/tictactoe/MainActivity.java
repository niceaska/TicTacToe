package ru.niceaska.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private GridLayout gridLayout;
    private TicTacToeField ticTacToeField;
    private LinearLayout winnerDialog;
    private TextView winnerCaption;
    private Button againButton;
    private Button exitButton;
    private TextView circleCaption;
    private TextView crossCaption;
    private boolean gamer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout = findViewById(R.id.tic_tac_field);
        winnerDialog = findViewById(R.id.winner_dialog);
        winnerCaption = findViewById(R.id.winner_caption);
        againButton = findViewById(R.id.again_button);
        exitButton = findViewById(R.id.exit_button);
        circleCaption = findViewById(R.id.circle_caption);
        crossCaption = findViewById(R.id.cross_caption);
        ticTacToeField = new TicTacToeField(3);

        setGridListeners();

        againButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void setGridListeners() {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View v = gridLayout.getChildAt(i);
            v.setTag(new Point(i % 3, i / 3));
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Point point = (Point) v.getTag();
                    if (ticTacToeField.getFigure(point.x, point.y) == TicTacToeField.Figure.NONE) {
                        ((ImageView) v).setImageDrawable(getDrawable(gamer ? R.drawable.circle : R.drawable.cross));
                        ticTacToeField.setFigure(point.x, point.y, gamer ?
                                TicTacToeField.Figure.CIRCLE : TicTacToeField.Figure.CROSS);
                        gamer = !gamer;
                    }
                    checkWinner(ticTacToeField.getWinner());
                }
            });
        }
    }


    private void checkWinner(TicTacToeField.Figure winner) {

        if (winner != TicTacToeField.Figure.NONE || ticTacToeField.isFull()) {
            switch (winner) {
                case CIRCLE:
                    winnerCaption.setText("Нолики");
                    break;
                case CROSS:
                    winnerCaption.setText("Крестики");
                    break;
                default:
                    winnerCaption.setText("Ничья");
                    break;
            }
            winnerDialog.setVisibility(View.VISIBLE);
        }
    }
}