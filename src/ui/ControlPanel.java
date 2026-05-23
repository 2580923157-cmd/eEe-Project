package ui;

import javax.swing.*;
import java.awt.*;
//import javax.swing.border.LineBorder;
// 或使用 BorderFactory
//import javax.swing.BorderFactory;

import utils.AudioProcess;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//这是start那个按钮的控制区域
//打算在这里加入设置、音乐之类的
public class ControlPanel extends JPanel {
    StatusPanel statusPanel;
    JButton startButton;
    JButton settingsButton;
    JButton audioButton;
    JButton pauseButton;

    int offSetX;
    int offSetY;
    int width;
    int height;

    // 按钮状态
    private boolean isPaused=false;
    private boolean bgmPlaying=true;
    private ImageIcon settingsIcon;
    private ImageIcon audioOnIcon;
    private ImageIcon audioOffIcon;
    private ImageIcon pauseIcon;
    private ImageIcon continueIcon;

    public ControlPanel(StatusPanel statusPanel, int offSetX, int offSetY,int width, int height) {
        loadIcons();
        this.setLayout(null);
        this.setBounds(offSetX, offSetY, width, height);
        this.offSetX = offSetX;
        this.offSetY = offSetY;
        this.width = width;
        this.height = height;
        this.startButton = new JButton("start");
        this.statusPanel = statusPanel;
        int btnWidth = 150;
        int btnHeight = 50;
        int x = (width - btnWidth) / 2;
        int y = (height - btnHeight) / 2-20;
        startButton.setBounds(x, y, btnWidth, btnHeight);
        startButton.setFont(new Font("Arial", Font.BOLD, 25));
        startButton.setFocusPainted(false);
        this.add(startButton);
        this.startButton.addActionListener(e -> {
            //statusPanel.setStatus("RUN");
            StatusPanel.resetTimer();
            StatusPanel.startTimer();
            startButton.setVisible(false);
            // 显示控制按钮
            settingsButton.setVisible(true);
            audioButton.setVisible(true);
            pauseButton.setVisible(true);
        });

        //设置按钮
        settingsButton = createIconButton(settingsIcon, "设置");
        int iconSize = 40;
        int gap = 10;
        int leftX = 20;
        int bottomY = height - iconSize-50;
        settingsButton.setBounds(leftX, bottomY, iconSize, iconSize);
        settingsButton.addActionListener(this::onSettingsClick);
        settingsButton.setVisible(false);
        this.add(settingsButton);

        //音频控制按钮
        audioButton = createIconButton(audioOnIcon, "音频控制"); // 初始未播放
        audioButton.setBounds(leftX + iconSize + gap, bottomY, iconSize, iconSize);
        audioButton.addActionListener(this::onAudioClick);
        audioButton.setVisible(false);
        this.add(audioButton);

        //暂停按钮
        pauseButton = createIconButton(pauseIcon, "暂停");
        int rightX = width - iconSize - 20;
        pauseButton.setBounds(rightX, bottomY, iconSize, iconSize);
        pauseButton.addActionListener(this::onPauseClick);
        pauseButton.setVisible(false);
        this.add(pauseButton);
    }
    // 加载图标资源
    private void loadIcons() {
        final int iconSize=45;
        settingsIcon = loadAndScaleIcon("settings.png", iconSize, iconSize);
        audioOnIcon  = loadAndScaleIcon("audio_on.png", iconSize, iconSize);
        audioOffIcon = loadAndScaleIcon("audio_off.png", iconSize, iconSize);
        pauseIcon    = loadAndScaleIcon("pause.png", iconSize, iconSize);
        continueIcon = loadAndScaleIcon("continue.png", iconSize, iconSize);
    }

    /**
    * 创建纯图标按钮，鼠标靠近时候会”凸起“，这里利用swing的border实现；累死我啦
     * 其中的tootip是鼠标靠近时候显示的文字
    * */
    private JButton createIconButton(ImageIcon icon, String tooltip) {
        JButton btn = new JButton(icon);
        btn.setToolTipText(tooltip);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getDefaultCursor());



        // 鼠标悬停效果
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                /*ImageIcon originalIcon=(ImageIcon) btn.getIcon();
                if(originalIcon!=null) {
                    ImageIcon biggerIcon = new ImageIcon(
                            icon.getImage().getScaledInstance(icon.getIconWidth() + 6, icon.getIconHeight() + 6, Image.SCALE_SMOOTH)
                    );
                    btn.setIcon(biggerIcon);
                }*/

                btn.setBorderPainted(true);
                btn.setBorder(BorderFactory.createLineBorder(new Color(100, 126, 247), 2));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                //btn.setIcon(originalIcon);
                btn.setBorderPainted(false);
                btn.setBorder(null);
            }
        });
        return btn;

    }

    // 从文件加载图标并缩放
    private ImageIcon loadAndScaleIcon(String filename, int w, int h) {
        ImageIcon icon = new ImageIcon("resource\\menus\\" + filename);
        Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    // ========== 事件处理 ==========

    //设置
    private void onSettingsClick(ActionEvent e) {
        //后续实现！

    }

    //音频启停
    private void onAudioClick(ActionEvent e) {
        AudioProcess.playClick();
        if (bgmPlaying) {
            AudioProcess.stopBgm();
            audioButton.setIcon(audioOffIcon);
            audioButton.setToolTipText("播放音乐");
            bgmPlaying = false;
        } else {
            AudioProcess.playBgm();
            audioButton.setIcon(audioOnIcon);
            audioButton.setToolTipText("停止音乐");
            bgmPlaying = true;
        }

    }

    // 暂停/继续：控制计时器
    private void onPauseClick(ActionEvent e) {
        AudioProcess.playClick();
        if (!isPaused) {
            statusPanel.stopTimer();
            pauseButton.setIcon(continueIcon);
            pauseButton.setToolTipText("继续");
            isPaused = true;

        } else {
            statusPanel.startTimer();
            pauseButton.setIcon(pauseIcon);
            pauseButton.setToolTipText("暂停");
            isPaused = false;
            // 恢复游戏交互
        }

    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean isPaused){
        this.isPaused=isPaused;
    }
    public boolean isBgmPlaying() {
        return bgmPlaying;
    }

    public void setBgmPlaying(boolean bgmPlaying) {
        this.bgmPlaying = bgmPlaying;
    }

    public boolean isBoardActive() {
        return !startButton.isVisible() && !isPaused;
    }
    /*
    * package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import utils.AudioProcess;

public class ControlPanel extends JPanel {
    StatusPanel statusPanel;
    JButton startButton;
    JButton settingsButton;
    JButton audioButton;
    JButton pauseButton;

    int offSetX, offSetY, width, height;

    // 按钮状态
    private boolean isPaused = false;
    private boolean bgmPlaying = false;

    // 图标（从 resource/menus 加载）
    private ImageIcon settingsIcon;
    private ImageIcon audioOnIcon;
    private ImageIcon audioOffIcon;
    private ImageIcon pauseIcon;
    private ImageIcon continueIcon;

    public ControlPanel(StatusPanel statusPanel, int offSetX, int offSetY, int width, int height) {
        this.statusPanel = statusPanel;
        this.offSetX = offSetX;
        this.offSetY = offSetY;
        this.width = width;
        this.height = height;

        setLayout(null);
        setBounds(offSetX, offSetY, width, height);
        loadIcons();

        // ---------- 原有 Start 按钮（居中） ----------
        startButton = new JButton("Start");
        int btnWidth = 150, btnHeight = 50;
        startButton.setBounds((width - btnWidth) / 2, (height - btnHeight) / 2, btnWidth, btnHeight);
        startButton.setFont(new Font("Arial", Font.BOLD, 25));
        startButton.setFocusPainted(false);
        startButton.addActionListener(e -> {
            statusPanel.setStatus("RUN");
            statusPanel.resetTimer();
            statusPanel.startTimer();
        });
        add(startButton);

        // ---------- 左下角：设置按钮 ----------
        settingsButton = createIconButton(settingsIcon, "设置");
        int iconSize = 40;
        int gap = 10;
        int leftX = 20;
        int bottomY = height - iconSize - 15;
        settingsButton.setBounds(leftX, bottomY, iconSize, iconSize);
        settingsButton.addActionListener(this::onSettingsClick);
        add(settingsButton);

        // ---------- 左下角：音频控制按钮 ----------
        audioButton = createIconButton(audioOffIcon, "音频控制"); // 初始未播放
        audioButton.setBounds(leftX + iconSize + gap, bottomY, iconSize, iconSize);
        audioButton.addActionListener(this::onAudioClick);
        add(audioButton);

        // ---------- 右下角：暂停/继续按钮 ----------
        pauseButton = createIconButton(pauseIcon, "暂停");
        int rightX = width - iconSize - 20;
        pauseButton.setBounds(rightX, bottomY, iconSize, iconSize);
        pauseButton.addActionListener(this::onPauseClick);
        add(pauseButton);
    }

    // 加载图标资源
    private void loadIcons() {
        settingsIcon = loadAndScaleIcon("settings.png", 36, 36);
        audioOnIcon  = loadAndScaleIcon("audio_on.png", 36, 36);
        audioOffIcon = loadAndScaleIcon("audio_off.png", 36, 36);
        pauseIcon    = loadAndScaleIcon("pause.png", 36, 36);
        continueIcon = loadAndScaleIcon("continue.png", 36, 36);
    }

    // 创建纯图标按钮（无边框、透明背景）
    private JButton createIconButton(ImageIcon icon, String tooltip) {
        JButton btn = new JButton(icon);
        btn.setToolTipText(tooltip);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getDefaultCursor());
        return btn;
    }

    // 从文件加载图标并缩放
    private ImageIcon loadAndScaleIcon(String filename, int w, int h) {
        ImageIcon icon = new ImageIcon("resource/menus/" + filename);
        Image img = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    // ========== 事件处理 ==========

    // 设置按钮（预留）
    private void onSettingsClick(ActionEvent e) {
        // 暂时不做任何事，等待后续实现
        // 例如：打开设置对话框
    }

    // 音频控制：切换背景音乐播放/停止
    private void onAudioClick(ActionEvent e) {
        if (bgmPlaying) {
            AudioProcess.stopBgm();
            audioButton.setIcon(audioOffIcon);
            audioButton.setToolTipText("播放音乐");
            bgmPlaying = false;
        } else {
            AudioProcess.playBgm();
            audioButton.setIcon(audioOnIcon);
            audioButton.setToolTipText("停止音乐");
            bgmPlaying = true;
        }
    }

    // 暂停/继续：控制计时器
    private void onPauseClick(ActionEvent e) {
        if (!isPaused) {
            statusPanel.stopTimer();
            pauseButton.setIcon(continueIcon);
            pauseButton.setToolTipText("继续");
            isPaused = true;
            // 可以在这里调用游戏暂停逻辑（如禁止棋盘点击）
        } else {
            statusPanel.startTimer();
            pauseButton.setIcon(pauseIcon);
            pauseButton.setToolTipText("暂停");
            isPaused = false;
            // 恢复游戏交互
        }
    }
}

    */

}
