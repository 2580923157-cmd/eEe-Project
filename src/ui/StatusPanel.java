package ui;

import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel {
    static JLabel statusLabel;
    static JLabel timeLabel;
    static Timer timer;
    static int seconds;
    private static int score = 0;
    private static int combo = 0;
    //static int score;

    //加分(包括连消)
    public static void addScore(int base) {
        combo++;
        int add = base * combo;
        score += add;
        updateLabels();
    }
    //撤销分数
    public static void undoScore() {
        if (score >= 10) {
            int lastAdd = 10 * combo;
            score = Math.max(0, score - lastAdd);
            combo = Math.max(0, combo - 1);
            updateLabels();
        }
    }
    //连消中断（点错时调用）
    public static void breakCombo() {
        combo = 0;
        updateLabels();
    }

    /** 重新开始的一部分：分数重置*/
    public void resetScore() {
        setScore(0);
        breakCombo();  // 重置连击计数
        statusLabel.setText("Score: 0");  // 或调用 setStatus("...")
    }

    // 静态刷新 显示分数、连消
    public static void updateLabels() {
        if (timeLabel == null || timeLabel.getParent() == null) return;
        StatusPanel panel = (StatusPanel) timeLabel.getParent();
        timeLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
        panel.statusLabel.setText("Score: " + score + "  ×" + combo);
        panel.repaint();
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        StatusPanel.score = score;
    }



    public static int getMinutes() {
        return minutes;
    }

    public static void setMinutes(int minutes) {
        StatusPanel.minutes = minutes;
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(JLabel statusLabel) {
        this.statusLabel = statusLabel;
    }

    public static JLabel getTimeLabel() {
        return timeLabel;
    }

    public static void setTimeLabel(JLabel timeLabel) {
        StatusPanel.timeLabel = timeLabel;
    }

    public static Timer getTimer() {
        return timer;
    }

    public static void setTimer(Timer timer) {
        StatusPanel.timer = timer;
    }

    public static int getSeconds() {
        return seconds;
    }

    public static void setSeconds(int seconds) {
        StatusPanel.seconds = seconds;
    }

    public static int getHours() {
        return hours;
    }

    public static void setHours(int hours) {
        StatusPanel.hours = hours;
    }

    public int getOffSetX() {
        return offSetX;
    }

    public void setOffSetX(int offSetX) {
        this.offSetX = offSetX;
    }

    public int getOffSetY() {
        return offSetY;
    }

    public void setOffSetY(int offSetY) {
        this.offSetY = offSetY;
    }

    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    static int minutes;
    static int hours;
    int offSetX;
    int offSetY;
    int width;
    int height;

    public StatusPanel(int offSetX, int offSetY,int width, int height) {
        this.setLayout(null);
        this.setBounds(offSetX, offSetY, width, height);
        this.offSetX = offSetX;
        this.offSetY = offSetY;
        this.width = width;
        this.height = height;
        statusLabel = new JLabel("READY");
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        timeLabel = new JLabel("00:00:00");
        timer = new Timer(1000, e -> {
            seconds++;
            if (seconds == 60) {
                minutes++;
                seconds = 0;
                if (minutes == 60) {
                    minutes = 0;
                    hours++;
                }
            }
            timeLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
        });
        //开始计时！
        //timer.start();
        statusLabel.setFont(new Font("微软雅黑", Font.PLAIN, 30));
        timeLabel.setFont(new Font("Arial", Font.BOLD, 50));
        Dimension size = statusLabel.getPreferredSize();
        Dimension timeLabelSize = timeLabel.getPreferredSize();
        int x = (width - size.width) / 4 ;
        int y = (height - size.height) / 3;
        int time_x = (width - timeLabelSize.width) * 2 / 3;
        int time_y = (height - timeLabelSize.height) * 2 /3;
        statusLabel.setBounds(x-130, y, size.width+150, size.height);
        timeLabel.setBounds(time_x, time_y, timeLabelSize.width, timeLabelSize.height);
        this.add(statusLabel);
        this.add(timeLabel);
    }
    //启动计时器，外部调用用
    public static void startTimer() {
        if (timer != null && !timer.isRunning()) {
            timer.start();
        }
    }

    // 停止计时器，外部调用用
    public static void stopTimer() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }

    /** 重开的一部分：重置时间*/
    public static void resetTimer() {
        stopTimer();
        seconds = 0;
        minutes = 0;
        hours = 0;
        timeLabel.setText("00:00:00");
    }
    public void setStatus(String text) {
        statusLabel.setText(text);
        Dimension size = statusLabel.getPreferredSize();
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        int x = (width - size.width)/4;
        int y = (height - size.height)/3;
        statusLabel.setBounds(x-130, y, size.width+150, size.height);
        repaint();
    }

    //刷新显示
    /*public void updateLabels() {
        timeLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
        statusLabel.setText("Score: " + score);
        repaint();
    }*/

}