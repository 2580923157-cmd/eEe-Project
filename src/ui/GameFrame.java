package ui;

import model.Cell;
import model.GameBoard;
import model.GameState;
import model.Position;
import utils.AudioProcess;
import utils.AudioProcess.*;
import model.User;
import model.SaveManager;

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
    User user;
    StatusPanel statusPanel;
    ControlPanel controlPanel;
    BoardPanel boardPanel;
    SaveManager saveManager = new SaveManager();
    //boardPanel.setControlPanel(controlPanel);

    /*public BoardPanel getBoardPanel(){
        return boardPanel;
    }*/
    public GameFrame(String title, int width, int height,User user) {
        super(title);
        this.setResizable(false);
        int size = 4;//有效棋盘大小（中间格子数量）
        this.user=user;
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
                board[i][j] = new Cell(new Position(i, j), false, rand.nextInt(1,14));   //格子
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
        //用来保存
        controlPanel.setSaveAction(this::saveGame);

        //设置棋盘大小
        this.title = title;
        this.width = width;
        this.height = height;
        this.setLayout(null);
        this.setSize(width,height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        setLocationRelativeTo(null);

        //将几个panel添加到主界面
        this.boardPanel.setControlPanel(this.controlPanel);
        this.add(this.statusPanel);
        this.add(this.controlPanel);
        this.add(this.boardPanel);
        AudioProcess.playBgm();
    }


    // 存档接口
    //返回的是
    public GameState exportGameState() {
        Cell[][] board = boardPanel.gameBoard.getBoard();
        int score = statusPanel.getScore();
        int h = statusPanel.getHours();
        int m = statusPanel.getMinutes();
        int s = statusPanel.getSeconds();
        return new GameState(board, score, h, m, s);
    }

    //读档接口
    public void loadGameState(GameState state) {
        if (state == null)
            return;
        //controlPanel
        /*statusPanel.setScore(state.getScore());
        statusPanel.setHours(state.getHours());
        statusPanel.setMinutes(state.getMinutes());
        statusPanel.setSeconds(state.getSeconds());
        statusPanel.updateLabels();
        this.controlPanel = new ControlPanel(statusPanel, 0, 900, 1000, 100);*/
        // 恢复分数和时间
        statusPanel.setScore(state.getScore());
        statusPanel.setHours(state.getHours());
        statusPanel.setMinutes(state.getMinutes());
        statusPanel.setSeconds(state.getSeconds());
        statusPanel.updateLabels();

        // 恢复棋盘
        boardPanel.loadBoard(state.getBoard());
        controlPanel.forcedPause();
        controlPanel.forceToGameMode();

        // 如果控制面板尚未启动游戏，需要模拟已启动（隐藏Start按钮，显示控制按钮）
        // 这一步可以通过 GameFrame 初始化时决定，或者在 loadGame 中提前把状态设好。
        // 实际项目中你可以在进入游戏界面时自动完成 Start 按钮的隐藏工作。
        //controlPanel.setPaused(false);
    }
    /*// 存档方法
    public static void saveGame(String userName) {
        if (gameFrame == null || userName == null || userName.isBlank()) {
            JOptionPane.showMessageDialog(null, "游戏未打开或用户名为空！");
            return;
        }
        GameState state = gameFrame.exportGameState();
        saveManager.save(userName, state);
        JOptionPane.showMessageDialog(null, "存档成功！");
    }

    // 读档方法
    public static void loadGame(String userName) {
        if (gameFrame == null || userName == null || userName.isBlank()) {
            JOptionPane.showMessageDialog(null, "游戏未打开或用户名为空！");
            return;
        }
        GameState state = saveManager.load(userName);
        gameFrame.loadGameState(state);
        JOptionPane.showMessageDialog(null, "读档成功！");
    }*/
    public void saveGame() {
        if (user == null || user.isGuest()) {
            JOptionPane.showMessageDialog(this, "游客无法存档");
            return;
        }
        saveManager.save(user.getUserName(), exportGameState());
        JOptionPane.showMessageDialog(this, "存档成功！");
    }

    public void loadGame() {
        if (user == null || user.isGuest()) {
            JOptionPane.showMessageDialog(this, "游客无法读档");
            return;
        }
        GameState state = saveManager.load(user.getUserName());
        if (state == null) {
            JOptionPane.showMessageDialog(this, "没有可用的存档");
            return;
        }
        loadGameState(state);
        JOptionPane.showMessageDialog(this, "读档成功！");
    }

}
