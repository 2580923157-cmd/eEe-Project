package model;

import model.Cell;
import ui.GameFrame;

public class GameState {//存数据


    public Cell[][] getBoard() {
        return board;
    }

    public void setBoard(Cell[][] board) {
        this.board = board;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    private Cell[][] board;
    private int score;
    private int seconds;
    private int minutes;
    private int hours;

    public GameState(){}

    public GameState(Cell[][] board, int score, int hours,int minutes, int seconds){
        this.board = board;
        this.score = score;
        this.seconds = seconds;
        this.minutes = minutes;
        this.hours = hours;
    }



}
