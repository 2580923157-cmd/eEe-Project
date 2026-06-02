package ui;

import javax.swing.*;
import java.awt.*;

import utils.LanguageProcess;
import utils.ResourceProcess;

public class StatusPanel extends JPanel {
    static JLabel statusLabel;
    static JLabel timeLabel;
    static JLabel pairsLabel;
    static Timer timer;
    private static int score = 0;
    private static int combo = 0;
    // 上一次消除的图案ID（用于判断是否相同）
    private static int lastEliminateIcon = -1;
    public static int pairs=16;
    public static int nowPairs=16;

    private static long lastEliminateTime = 0;   // 上一次消除时间（毫秒）
    private static final long comboTimeLimit  = 5000; // 5 秒连击限时
    private Image comboIcon;
    private static JLabel comboLabel;

    public static int getCombo() {
        return combo;
    }

    public static void setCombo(int combo) {
        StatusPanel.combo = combo;
    }

    public static int getLastEliminateIcon() {
        return lastEliminateIcon;
    }

    public static void setLastEliminateIcon(int lastEliminateIcon) {
        StatusPanel.lastEliminateIcon = lastEliminateIcon;
    }



    // 加分
    public static void addScore(int currentIconIndex) {
        long now = System.currentTimeMillis();
        // 判断当前消除的图案和上一次消除的图案是否相同
        if (currentIconIndex == lastEliminateIcon&& (now - lastEliminateTime) <= comboTimeLimit) {
            combo++;
            comboLabel.setVisible(true);
        } else {
            setCombo(1);
            comboLabel.setVisible(false);
        }
        lastEliminateIcon = currentIconIndex;
        lastEliminateTime = now;
        int add = 10 * combo;
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
        setCombo(0);
        updateLabels();
        lastEliminateTime = 0;

    }

    /** 重新开始的一部分：分数重置*/
    public void resetScore() {
        setScore(0);
        breakCombo();  // 重置连击计数
        statusLabel.setText(LanguageProcess.getCurrentLanguage().score(0));  // 或调用 setStatus("...")
    }

    // 静态刷新 显示分数、连消
    public static void updateLabels() {
        if (timeLabel == null || timeLabel.getParent() == null) return;
        StatusPanel panel = (StatusPanel) timeLabel.getParent();
        timeLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
        panel.statusLabel.setText(LanguageProcess.getCurrentLanguage().score(score) +"×" + combo);
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
    static int seconds;
    int offSetX;
    int offSetY;
    int width;
    int height;
    private static int totalGameSeconds=60;
    private static Runnable onTimeUp;
    static int remainingSeconds=totalGameSeconds;

    public StatusPanel(int offSetX, int offSetY,int width, int height) {
        this.setLayout(null);
        this.setBounds(offSetX, offSetY, width, height);
        this.offSetX = offSetX;
        this.offSetY = offSetY;
        this.width = width;
        this.height = height;
        statusLabel = new JLabel(LanguageProcess.getCurrentLanguage().ready());
        statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        timeLabel = new JLabel("00:00:00");
        comboLabel=new JLabel();
        comboLabel.setVisible(false);
        pairsLabel=new JLabel();
        pairsLabel.setVisible(false);


        comboIcon=ResourceProcess.loadImage("effects/combo.png");
        Image img=comboIcon.getScaledInstance(115,88,Image.SCALE_SMOOTH);
        comboLabel.setIcon(new ImageIcon(img));
        /*try {
            //ImageIcon icon = new ImageIcon("resource\\menus\\language.png");
            //Image langImg = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            Image img=comboIcon.getScaledInstance(42,42,Image.SCALE_SMOOTH);
            comboLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            comboLabel.setText("COMBO");
        }*/
        // 倒计时计时器
        timer=new Timer(1000, t -> {
            // 借位逻辑
            if (seconds > 0) {
                seconds--;
            } else if (minutes > 0) {
                minutes--;
                seconds=59;
            } else if (hours > 0) {
                hours--;
                minutes = 59;
                seconds = 59;
            } else {
                // 时间耗尽
                stopTimer();
                if (onTimeUp != null) {
                    onTimeUp.run();
                }
            }
            if(hours==0&&minutes==0&&seconds<=20){
                //timeLabel.setFont(new Font("Arial", Font.BOLD, 50));
                timeLabel.setForeground(Color.RED);
            }
            updateTimer();
        });
        /*timer = new Timer(1000, e -> {
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
            updateT
        });*/
        //开始计时！
        //timer.start();

        statusLabel.setFont(new Font("微软雅黑", Font.PLAIN, 30));
        timeLabel.setFont(new Font("Arial", Font.BOLD, 50));
        pairsLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        pairsLabel.setForeground(Color.BLACK);
        Dimension size = statusLabel.getPreferredSize();
        Dimension timeLabelSize = timeLabel.getPreferredSize();
        Dimension comboSize=comboLabel.getPreferredSize();
        //Dimension pairsSize=pairsLabel.getPreferredSize();
        int x = (width - size.width) / 4 ;
        int y = (height - size.height) / 3;
        int time_x = (width - timeLabelSize.width) * 2 / 3;
        int time_y = (height - timeLabelSize.height) * 2 /3;
        statusLabel.setBounds(x-200, y-10, size.width+150, size.height);
        timeLabel.setBounds(320,30, timeLabelSize.width, timeLabelSize.height);
        comboLabel.setBounds(800,6, comboSize.width, comboSize.height);
        pairsLabel.setBounds(x-170,y+35,190,30);
        this.add(statusLabel);
        this.add(timeLabel);
        this.add(comboLabel);
        this.add(pairsLabel);

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
        timeLabel.setForeground(Color.BLACK);
        int totalTime=totalGameSeconds;
        hours=totalTime/3600;
        minutes=(totalTime%3600)/60;
        seconds=totalTime%60;
        //timeLabel.setText("00:00:00");
        updateTimer();
    }
    /**计时器更新显示，SP类里直接调用*/
    private static void updateTimer() {
        if (timeLabel!=null) {
            timeLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
        }
    }
    /**用来决定游戏时间
     * @param totalSec 传入游戏时间（秒数），简单60s，困难180s；准备写挑战*/
    public static void setGameTime(int totalSec) {
        totalGameSeconds=totalSec;
        resetTimer();
    }

    public static void setOnTimeUp(Runnable callback) {
        onTimeUp = callback;
    }
    public static void updatePairsLabel(int remPairs) {
        if (pairsLabel!=null) {
            pairsLabel.setText(LanguageProcess.getCurrentLanguage().pairs(remPairs,pairs));
        }
    }
    public static void setRemainingTime(int h, int m, int s) {
        stopTimer();
        hours=h;
        minutes=m;
        seconds=s;
        remainingSeconds=h*3600+m*60+s;
        updateTimer();
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