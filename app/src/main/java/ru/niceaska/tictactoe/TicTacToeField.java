package ru.niceaska.tictactoe;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class TicTacToeField implements Parcelable {

    private int[][] mData;
    private boolean[][] mWinnerMatrix;
    private boolean[] mIsCol;

    public TicTacToeField(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size should be positive number");
        }

        mData = new int[size][size];
        mIsCol = new boolean[size];
        mWinnerMatrix = new boolean[size][size];
    }

    protected TicTacToeField(Parcel in) {
        int size = in.readInt();

        mData = new int[size][];

        for (int i = 0; i < size; i++) {
            mData[i] = in.createIntArray();
        }

        mWinnerMatrix = new boolean[size][size];
        mIsCol = new boolean[size];
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mData.length);

        for (int i = 0; i < mData.length; i++) {
            dest.writeIntArray(mData[i]);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TicTacToeField> CREATOR = new Creator<TicTacToeField>() {
        @Override
        public TicTacToeField createFromParcel(Parcel in) {
            return new TicTacToeField(in);
        }

        @Override
        public TicTacToeField[] newArray(int size) {
            return new TicTacToeField[size];
        }
    };

    public Figure getFigure(int row, int col) {
        return Figure.values()[mData[row][col]];
    }

    public boolean setFigure(int row, int col, Figure figure) {
        if (mData[row][col] == 0) {
            mData[row][col] = figure.ordinal();
            return true;
        }

        return false;
    }

    public boolean isEmptyCell(int row, int col) {
        return mData[row][col] == 0;
    }

    public boolean isFull() {
        for (int[] row : mData) {
            for (int cell : row) {
                if (cell == 0) {
                    return false;
                }
            }
        }

        return true;
    }

    public Figure getWinner() {
        if (checkIsWinner(Figure.CROSS.ordinal())) {
            return Figure.CROSS;
        }

        if (checkIsWinner(Figure.CIRCLE.ordinal())) {
            return Figure.CIRCLE;
        }

        return Figure.NONE;
    }

    public boolean[][] getWinnerMatrix() {
        return mWinnerMatrix;
    }

    private boolean checkIsWinner(int player) {
        boolean winner = false;

        boolean diagonal1 = true;
        boolean diagonal2 = true;

        clearWinner();
        Arrays.fill(mIsCol, true);

        for (int row = 0; row < mData.length; row++) {
            boolean isRow = true;

            for (int col = 0; col < mData.length; col++) {
                mIsCol[col] = mIsCol[col] && mData[row][col] == player;
                isRow = isRow && mData[row][col] == player;
            }

            diagonal1 = diagonal1 && mData[row][row] == player;
            diagonal2 = diagonal2 && mData[row][mData.length - row - 1] == player;

            if (isRow) {
                for (int col = 0; col < mData.length; col++) {
                    mWinnerMatrix[row][col] = true;
                    winner = true;
                }
            }
        }

        for (int i = 0; i < mIsCol.length; i++) {
            if (mIsCol[i]) {
                for (int row = 0; row < mData.length; row++) {
                    mWinnerMatrix[row][i] = true;
                    winner = true;
                }
            }
        }

        if (diagonal1) {
            for (int i = 0; i < mWinnerMatrix.length; i++) {
                mWinnerMatrix[i][i] = true;
            }

            winner = true;
        }

        if (diagonal2) {
            for (int i = 0; i < mWinnerMatrix.length; i++) {
                mWinnerMatrix[i][mWinnerMatrix.length - i - 1] = true;
            }

            winner = true;
        }

        return winner;
    }

    private void clearWinner() {
        for (int i = 0; i < mWinnerMatrix.length; i++) {
            Arrays.fill(mWinnerMatrix[i], false);
        }
    }

    public int[][] getData() {
        return mData;
    }

    enum Figure {
        NONE,
        CROSS,
        CIRCLE
    }
}

