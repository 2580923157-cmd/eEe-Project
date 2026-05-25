package ui;

import model.Cell;
import model.GameBoard;
import model.GameState;
import model.Position;
import utils.AudioProcess;
import utils.AudioProcess.*;
import model.User;

import javax.swing.*;
import java.awt.*;

import java.time.LocalTime;
import java.util.Random;

/**
 * 主游戏框架及界面。重要的游戏主内容都在这里实现。
 * 这里创建了棋盘、控制面板和状态栏
 */

public class GameFrame extends JFrame{
    int width;
    int height;
    String title;
    StatusPanel statusPanel;
    ControlPanel controlPanel;
    BoardPanel boardPanel;
    //boardPanel.setControlPanel(controlPanel);

    /*public BoardPanel getBoardPanel(){
        return boardPanel;
    }*/
    public GameFrame(String title, int width, int height,User user) {
        super(title);
        this.setResizable(false);
        int size = 4;//有效棋盘大小（中间格子数量）
        Cell[][] board = new Cell[size + 2][size + 2];

        for (int i = 0; i < size + 2; i++) {
            for (int j = 0; j < size + 2; j++) {
                if (i == 0 || i == size + 1 || j == 0 || j == size + 1) {
                    board[i][j] = new Cell(new Position(i, j), true, 0); // 边框
                }
            }
        }

        Random rand=new Random(LocalTime.now().getSecond()+LocalTime.now().getMinute()+LocalTime.now().getHour());
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                board[i][j] = new Cell(new Position(i, j), false, rand.nextInt(1,9));   //格子
            }
        }
        //目前是完全随机

        /*//测试用：全1
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                board[i][j] = new Cell(new Position(i, j), false, 1);   //格子全是1
            }
        }*/
        //BoardPanel boardPanel = new BoardPanel(new GameBoard(size+2, size+2, board), 0, 100, 800, 800);
        this.boardPanel=new BoardPanel(new GameBoard(size+2, size+2, board), 80, 100, 800, 800);
        this.statusPanel = new StatusPanel(30, 0, 800, 100);
        this.controlPanel = new ControlPanel(statusPanel, 0, 900, 1000, 100);
        //状态注入
        boardPanel.setControlPanel(controlPanel);
        controlPanel.setBoardPanel(boardPanel);
        //用来退回menu
        controlPanel.exitAction(() -> {
            dispose();                              // 关闭当前游戏窗口
            SwingUtilities.invokeLater(() -> {      // 返回主菜单
                Menu menu=new Menu(user);  // 需要把当前用户传回菜单
                AudioProcess.stopBgm();
                menu.setVisible(true);
            });
        });

        //设置棋盘大小
        this.title = title;
        this.width = width;
        this.height = height;
        this.setLayout(null);
        this.setSize(width,height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        setLocationRelativeTo(null);

        //这个
        this.boardPanel.setControlPanel(this.controlPanel);
        this.add(this.statusPanel);
        this.add(this.controlPanel);
        this.add(this.boardPanel);
        AudioProcess.playBgm();
    }


    // 存档接口
    public GameState exportGameState() {
        int score = statusPanel.getScore();
        int h = statusPanel.getHours();
        int m = statusPanel.getMinutes();
        int s = statusPanel.getSeconds();
        return new GameState(null, score, h, m, s);
    }

    //读档接口
    public void loadGameState(GameState state) {
        if (state == null) return;
        statusPanel.setScore(state.getScore());
        statusPanel.setHours(state.getHours());
        statusPanel.setMinutes(state.getMinutes());
        statusPanel.setSeconds(state.getSeconds());
        statusPanel.updateLabels();
    }

}
