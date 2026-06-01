package ui;

import model.*;
import model.Rectangle;
import utils.AudioProcess;

import javax.swing.*;
import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BoardPanel extends JPanel {
    int offSetX;
    int offSetY;

    List<Image> imageList = new ArrayList<>();
    GameBoard gameBoard;
    List<Line> lineList = new ArrayList<>();

    int totalRow;
    int totalCol;
    boolean lineVisible;
    int width;
    int height;
    int cellWidth;
    int cellHeight;

    Position firstSelected = null;
    Position secondSelected = null;

    boolean animating = false;
    private float animProgress = 0.0f;        // 画线进度 0~1
    private Timer animTimer;                  // 动画用定时器
    private Cell animCell1, animCell2;        // 当前正在动画的起点和终点
    //过段时间再实现！

    private Stack<Cell[][]> historyStack = new Stack<>();//用于把上一步信息保存在内存栈

    public Position getPositionByPoint(int x, int y) {

        int col = x / cellWidth;
        int row = y / cellHeight;
        if (row < 0 || row >= totalRow || col < 0 || col >= totalCol) {
            return null;
        }
        return new Position(row, col);
    }
    /*
    public boolean isAdjacent(Position p1, Position p2) {
        int dr = Math.abs(p1.getRow() - p2.getRow());
        int dc = Math.abs(p1.getCol() - p2.getCol());
        return dr + dc == 1;
    }
    */
    private ControlPanel controlPanel = null;

    public void setControlPanel(ControlPanel controlPanel) {
        this.controlPanel = controlPanel;
    }

    // 获取连通路径
    public List<Position> getLinkPath(Position a, Position b) {
        Cell ca = gameBoard.getCell(a.getRow(), a.getCol());
        Cell cb = gameBoard.getCell(b.getRow(), b.getCol());
        if (!ca.isSameIcon(cb)) return null; //补充一个isSameIcon判断是否是同种图案的方法

        List<Position> path = new ArrayList<>();
        if (direct(a, b)) {
            path.add(a); path.add(b); return path;
        }
        Position corner1 = oneCorner(a, b);
        if (corner1 != null) {
            path.add(a); path.add(corner1); path.add(b); return path;
        }
        List<Position> corners = twoCorner(a, b);
        if (corners != null) {
            path.add(a);
            path.add(corners.get(0));
            path.add(corners.get(1));
            path.add(b);
            return path;
        }
        return null;
    }


    // 直线连通
    private boolean direct(Position a, Position b) {
        if (a.getRow() != b.getRow() && a.getCol() != b.getCol()) {
            return false;
        }
        int r1 = a.getRow(), c1 = a.getCol();
        int r2 = b.getRow(), c2 = b.getCol();
        if (r1 == r2) {
            int min = Math.min(c1, c2), max = Math.max(c1, c2);
            for (int c = min+1; c < max; c++) {
                if (!gameBoard.getCell(r1, c).isEmpty()) return false;
            }
        } else {
            int min = Math.min(r1, r2), max = Math.max(r1, r2);
            for (int r = min+1; r < max; r++) {
                if (!gameBoard.getCell(r, c1).isEmpty()) return false;
            }
        }
        return true;
    }


    // 一个拐点
    private Position oneCorner(Position a, Position b) {
        Position p1 = new Position(a.getRow(), b.getCol());
        if (direct(a, p1) && direct(p1, b) && gameBoard.getCell(p1.getRow(), p1.getCol()).isEmpty()) {
            return p1;
        }
        Position p2 = new Position(b.getRow(), a.getCol());
        if (direct(a, p2) && direct(p2, b) && gameBoard.getCell(p2.getRow(), p2.getCol()).isEmpty()) {
            return p2;
        }
        return null;
    }


    // 两个拐点
    private List<Position> twoCorner(Position a, Position b) {
        for (int r = 0; r < totalRow; r++) {
            Position p1 = new Position(r, a.getCol());
            Position p2 = new Position(r, b.getCol());
            if (gameBoard.getCell(p1.getRow(), p1.getCol()).isEmpty() &&
                    gameBoard.getCell(p2.getRow(), p2.getCol()).isEmpty() &&
                    direct(a, p1) && direct(p1, p2) && direct(p2, b)) {
                List<Position> list = new ArrayList<>();
                list.add(p1); list.add(p2); return list;
            }
        }
        for (int c = 0; c < totalCol; c++) {
            Position p1 = new Position(a.getRow(), c);
            Position p2 = new Position(b.getRow(), c);
            if (gameBoard.getCell(p1.getRow(), p1.getCol()).isEmpty() &&
                    gameBoard.getCell(p2.getRow(), p2.getCol()).isEmpty() &&
                    direct(a, p1) && direct(p1, p2) && direct(p2, b)) {
                List<Position> list = new ArrayList<>();
                list.add(p1); list.add(p2); return list;
            }
        }
        return null;
    }

    // 统一判定是否可以消除
    private boolean canEliminate(Position a, Position b) {
        Cell ca = gameBoard.getCell(a.getRow(), a.getCol());
        Cell cb = gameBoard.getCell(b.getRow(), b.getCol());
        if (!ca.isSameIcon(cb)){
            return false;
        }
        if(direct(a,b)) return true;
        if(oneCorner(a,b) != null) return true;
        if(twoCorner(a,b) != null) return true;
        return false;
    }


    //接下来需要写一个记录消除路线的方法 再写一个点击方法 点击时调用前者
    public void showLine(Cell c1, Cell c2) {
        lineList.clear();
        lineList.add(new Line(c1, c2));
        lineVisible = true;
        repaint();
    }
    /**添加一条折线*/
    public void addLinePart(Cell c1, Cell c2) {
        lineList.add(new Line(c1, c2));
        lineVisible = true;
        repaint();
    }
    public void clearLine() {
        lineVisible = false;
        lineList.clear();
        repaint();
    }

    public BoardPanel(GameBoard gameBoard, int offSetX, int offSetY,int width, int height) {
        this.offSetX = offSetX;
        this.offSetY = offSetY;
        this.setBounds(offSetX, offSetY, width, height);
        this.totalRow = gameBoard.getRowCnt();
        this.totalCol = gameBoard.getColCnt();
        this.width = width;
        this.height = height;
        this.setLayout(new GridLayout(this.totalRow, this.totalCol));
        this.gameBoard = gameBoard;
        this.setPreferredSize(new Dimension(this.width, this.height));
        this.cellWidth = this.width / totalCol;
        this.cellHeight = this.height / totalRow;

        //接下来读文件（棋子的照片）
        File dir = new File("resource\\images");
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.getName().endsWith(".png")) {
                ImageIcon icon = new ImageIcon(file.getPath());
                imageList.add(icon.getImage());
            }
        }
        saveHistory();


        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleClick(e.getX() , e.getY());
                //StatusPanel.startTimer();
                //startButton.setVisible(false);
            }
        });
    }


    // 保存当前棋盘快照
    public void saveHistory() {
        Cell[][] copy = new Cell[totalRow][totalCol];
        for (int i = 0; i < totalRow; i++) {
            for (int j = 0; j < totalCol; j++) {
                Cell orig = gameBoard.getCell(i, j);
                if (orig == null) continue;
                copy[i][j] = new Cell(orig.getPos(), orig.isEmpty(), orig.getIconIndex());
            }
        }
        historyStack.push(copy);
    }

    // 撤销
    public void undoStep() {
        if (historyStack.size() <= 1) {
            AudioProcess.playClickSpecial();
            JOptionPane.showMessageDialog(this, "已经是第一步！");
            return;
        }

        // 回退分数
        StatusPanel.undoScore();

        // 恢复棋盘
        //historyStack.pop();
        Cell[][] lastBoard = historyStack.pop();
        for (int i = 0; i < totalRow; i++) {
            for (int j = 0; j < totalCol; j++) {
                Cell c = gameBoard.getCell(i, j);
                Cell saved = lastBoard[i][j];
                c.setEmpty(saved.isEmpty());
                c.setIconIndex(saved.getIconIndex());
            }
        }

        gameBoard.clearAllChosen();
        firstSelected = null;
        secondSelected = null;
        repaint();

        // 撤销后重新判断胜负
        checkWin();
    }
    /**
     * 重开游戏
     */
    public void resetGame() {
        // 有操作再执行
        if (!historyStack.isEmpty()) {
            // 获取栈底元素（第一个保存的状态）
            Cell[][] initialBoard = historyStack.firstElement();
            // 将当前棋盘格子全部恢复为该快照
            for (int i = 0; i < totalRow; i++) {
                for (int j = 0; j < totalCol; j++) {
                    Cell cell = gameBoard.getCell(i, j);
                    Cell saved = initialBoard[i][j];
                    cell.setEmpty(saved.isEmpty());
                    cell.setIconIndex(saved.getIconIndex());
                }
            }
        }
        // 清空所有选择
        gameBoard.clearAllChosen();
        firstSelected = null;
        secondSelected = null;
        lineVisible = false;
        lineList.clear();
        animating = false;
        // 清空历史并重新保存当前初始状态
        historyStack.clear();
        /*controlPanel=null;
        System.gc();*/
        saveHistory();
        repaint();
    }

    //胜负判断，棋盘空了为胜
    public boolean checkWin() {
        for (int i = 1; i <= totalRow - 2; i++) {
            for (int j = 1; j <= totalCol - 2; j++) {
                Cell c = gameBoard.getCell(i, j);
                if (!c.isEmpty()) {
                    return false;
                }
            }
        }
        AudioProcess.playWin();
        JOptionPane.showMessageDialog(this, "🎉 恭喜通关！");
        StatusPanel.stopTimer();
        return true;
    }
    /**加载棋盘，与load存档共同使用*/
    public void loadBoard(Cell[][] newBoard) {
        for (int i = 0; i < totalRow && i < newBoard.length; i++) {
            for (int j = 0; j < totalCol && j < newBoard[i].length; j++) {
                Cell src=newBoard[i][j];
                Cell target=gameBoard.getCell(i, j);
                if(target==null){
                    target=new Cell(new Position(i, j), src.isEmpty(), src.getIconIndex());
                    gameBoard.setCell(i,j,target);
                } else {
                    target.setEmpty(src.isEmpty());
                    target.setIconIndex(src.getIconIndex());
                }
                /*target.setEmpty(src.isEmpty());
                target.setIconIndex(src.getIconIndex());*/
            }
        }
        // 清空选中状态和历史
        gameBoard.clearAllChosen();
        firstSelected = null;
        secondSelected = null;
        historyStack.clear();
        saveHistory();      //重新入栈
        repaint();
    }

    /**点击的相关处理*/
    public void handleClick(int x, int y) {
        if(controlPanel!=null&&
                !controlPanel.isBoardActive()){
            return;
        }
        if (animating) {
            return;
        }

        Position pos = getPositionByPoint(x, y);
        if (pos == null) {
            return;
        }

        Cell clickedCell = gameBoard.getCell(pos.getRow(), pos.getCol());
        if (clickedCell == null || clickedCell.isEmpty()) {
            return;
        }

        // 第一次选中
        if (firstSelected == null) {
            gameBoard.clearAllChosen();
            clickedCell.setChosen(true);
            firstSelected = pos;
            repaint();
            return;
        }

        // 重复点击则取消选中
        if (firstSelected.equals(pos)) {
            gameBoard.clearAllChosen();
            firstSelected = null;
            secondSelected = null;
            repaint();
            return;
        }

        // 第二次选中
        secondSelected = pos;
        Cell secondCell = gameBoard.getCell(secondSelected.getRow(), secondSelected.getCol());
        secondCell.setChosen(true);
        repaint();


        //先判断能否消除
        if (canEliminate(firstSelected, secondSelected)) {
            //获取路径端点
            List<Position> path = getLinkPath(firstSelected, secondSelected);
            animating = true;

            //根据路径分段画线
            lineList.clear();
            for (int i = 0; i < path.size() - 1; i++) {
                Position p1 = path.get(i);
                Position p2 = path.get(i + 1);
                Cell c1 = gameBoard.getCell(p1.getRow(), p1.getCol());
                Cell c2 = gameBoard.getCell(p2.getRow(), p2.getCol());
                //showLine(c1, c2); // 多次调用画线 拼接成完整折线
                addLinePart(c1,c2); //画几段
            }
            lineVisible=true;
            repaint();
            AudioProcess.playClear();

            // 延时消除
            Timer timer = new Timer(300, e -> {
                Cell c1 = gameBoard.getCell(firstSelected.getRow(), firstSelected.getCol());
                Cell c2 = gameBoard.getCell(secondSelected.getRow(), secondSelected.getCol());

                // 保存历史
                saveHistory();

                c1.setEmpty(true);
                c2.setEmpty(true);
                c1.setChosen(false);
                c2.setChosen(false);

                // 加分
                StatusPanel.addScore(c1.getIconIndex());
                lineVisible = false;
                lineList.clear();
                firstSelected = null;
                secondSelected = null;
                animating = false;
                repaint();
                // 判断胜利
                checkWin();
            });
            timer.setRepeats(false);
            timer.start();

        } else {
            // 不能消除则切换选中并且将连消计数combo重置
            StatusPanel.breakCombo();
            gameBoard.clearAllChosen();
            secondCell.setChosen(true);
            firstSelected = secondSelected;
            secondSelected = null;
            repaint();
        }
    }
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public Rectangle getRectangle(Position position) {
        int x = position.getCol() * cellWidth;
        int y = position.getRow() * cellHeight;
        return new Rectangle(x, y, cellWidth, cellHeight);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g; //画笔
        for (int i = 0; i < gameBoard.getRowCnt(); i++) {
            for (int j = 0; j < gameBoard.getColCnt(); j++) {
                //加一层空判断
                Cell cell = gameBoard.getCell(i, j);
                if (cell == null) continue;

                Rectangle rec = getRectangle(new Position(i, j));
                g2.drawImage(
                        imageList.get(gameBoard.getCell(i, j).getIconIndex()),
                        rec.getX(), rec.getY(), rec.getWidth(), rec.getHeight(),
                        this
                );
                //在格子外画正方形
                if (gameBoard.getCell(i, j).getIsChosen()) {
                    /*g2.setColor(Color.RED);
                    g2.setStroke(new BasicStroke(3));
                    g2.drawRect(
                            rec.getX() + 1,
                            rec.getY() + 1,
                            rec.getWidth() - 3,
                            rec.getHeight() - 3
                    );     */
/*                    g2.drawLine(rec.getX()+6,rec.getY(),rec.getWidth()+rec.getX()-6,rec.getY());
                    g2.drawArc(rec.getWidth()+rec.getX()-6,rec.getY(),1,1,0,90);*/
                    g2.setColor(Color.RED);
                    g2.setStroke(new BasicStroke(3));
                    //x, y, width, height, arcWidth, arcHeight
                    int arcSize=20; //弧度
                    g2.drawRoundRect(
                            rec.getX() + 2,
                            rec.getY() + 2,
                            rec.getWidth() - 4,
                            rec.getHeight() - 4,
                            arcSize,
                            arcSize
                    );  //选中画圆角矩形
                } else {
                    g2.setColor(Color.GRAY);
                    g2.setStroke(new BasicStroke(1));
                    g2.drawRect(
                            rec.getX(),
                            rec.getY(),
                            rec.getWidth() - 1,
                            rec.getHeight() - 1
                    );  //未选中画灰
                }
            }
        }
        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(3));
        if (lineVisible) {
            for (Line line: lineList) {
                //再加一层空判断
                Cell c1 = line.getCell1();
                Cell c2 = line.getCell2();
                if (c1 == null || c2 == null) continue;

                Rectangle rec1 = getRectangle(line.getCell1().getPos());
                Rectangle rec2 = getRectangle(line.getCell2().getPos());
                g.drawLine((int) rec1.getCenterPosition().getX(), (int) rec1.getCenterPosition().getY(), (int) rec2.getCenterPosition().getX(), (int) rec2.getCenterPosition().getY());
            }
        }

    }




}
//老的判定
/*public void handleClick(int x, int y) {

        if(controlPanel!=null&&!controlPanel.isBoardActive()){
            return;
        }
        if (animating) {
            return;
        }

        Position pos = getPositionByPoint(x, y);
        if (pos == null) {
            return;
        }

        Cell clickedCell = gameBoard.getCell(pos.getRow(), pos.getCol());
        if (clickedCell == null || clickedCell.isEmpty()) {
            return;
        }

        // 第一次选中
        if (firstSelected == null) {
            gameBoard.clearAllChosen();
            clickedCell.setChosen(true);
            firstSelected = pos;
            repaint();
            return;
        }

        // 重复点击则取消选中
        if (firstSelected.equals(pos)) {
            gameBoard.clearAllChosen();
            firstSelected = null;
            secondSelected = null;
            repaint();
            return;
        }

        // 第二次选中
        secondSelected = pos;
        Cell secondCell = gameBoard.getCell(secondSelected.getRow(), secondSelected.getCol());
        secondCell.setChosen(true);
        repaint();


        //先判断能否消除
        if (canEliminate(firstSelected, secondSelected)) {
            //获取路径端点
            List<Position> path = getLinkPath(firstSelected, secondSelected);
            animating = true;

            //根据路径分段画线
            lineList.clear();
            for (int i = 0; i < path.size() - 1; i++) {
                Position p1 = path.get(i);
                Position p2 = path.get(i + 1);
                Cell c1 = gameBoard.getCell(p1.getRow(), p1.getCol());
                Cell c2 = gameBoard.getCell(p2.getRow(), p2.getCol());
                showLine(c1, c2); // 多次调用画线 拼接成完整折线
            }

            // 延时消除逻辑
            Timer timer = new Timer(300, e -> {
                Cell c1 = gameBoard.getCell(firstSelected.getRow(), firstSelected.getCol());
                Cell c2 = gameBoard.getCell(secondSelected.getRow(), secondSelected.getCol());
                c1.setEmpty(true);
                c2.setEmpty(true);
                c1.setChosen(false);
                c2.setChosen(false);
                lineVisible = false;
                lineList.clear();
                firstSelected = null;
                secondSelected = null;
                animating = false;
                repaint();
                AudioProcess.playClear();
            });
            timer.setRepeats(false);
            timer.start();

        } else {
            // 不能消除则切换选中
            gameBoard.clearAllChosen();
            secondCell.setChosen(true);
            firstSelected = secondSelected;
            secondSelected = null;
            repaint();
        }
    }*/