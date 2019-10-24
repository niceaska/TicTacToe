package ru.niceaska.tictactoe;

import android.os.Parcel;
import android.os.Parcelable;

public class TicTacToeGame implements Parcelable {

    public static final Creator<TicTacToeGame> CREATOR = new Creator<TicTacToeGame>() {
        @Override
        public TicTacToeGame createFromParcel(Parcel in) {
            return new TicTacToeGame(in);
        }

        @Override
        public TicTacToeGame[] newArray(int size) {
            return new TicTacToeGame[size];
        }
    };

    private boolean gamer;
    private boolean isGameRun;
    private int winner = TicTacToeField.Figure.NONE.ordinal();
    private int circlePoints;
    private int crossPoints;

    protected TicTacToeGame(Parcel in) {
        gamer = in.readByte() != 0;
        isGameRun = in.readByte() != 0;
        winner = in.readInt();
        circlePoints = in.readInt();
        crossPoints = in.readInt();
    }

    public TicTacToeGame(boolean gamer, boolean isGameRun, int circlePoints, int crossPoints) {
        this.gamer = gamer;
        this.isGameRun = isGameRun;
        this.circlePoints = circlePoints;
        this.crossPoints = crossPoints;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (gamer ? 1 : 0));
        dest.writeByte((byte) (isGameRun ? 1 : 0));
        dest.writeInt(winner);
        dest.writeInt(circlePoints);
        dest.writeInt(crossPoints);
    }

    public boolean isGamer() {
        return gamer;
    }

    public void setGamer(boolean gamer) {
        this.gamer = gamer;
    }

    public int getCirclePoints() {
        return circlePoints;
    }

    public void setCirclePoints(int circlePoints) {
        this.circlePoints = circlePoints;
    }

    public int getCrossPoints() {
        return crossPoints;
    }

    public void setCrossPoints(int crossPoints) {
        this.crossPoints = crossPoints;
    }

    public boolean isGameRun() {
        return isGameRun;
    }

    public void setGameRun(boolean gameRun) {
        isGameRun = gameRun;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }
}
