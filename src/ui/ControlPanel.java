package ui;

import javax.swing.*;
import java.awt.*;
//import javax.swing.border.LineBorder;
// 或使用 BorderFactory
//import javax.swing.BorderFactory;

import support.Chinese;
import support.Language;
import utils.AudioProcess;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import utils.LanguageProcess;
import utils.ResourceProcess;


//这是start那个按钮的控制区域
//打算在这里加入设置、音乐之类的
public class ControlPanel extends JPanel {
    StatusPanel statusPanel;
    JButton startButton;
    //JButton settingsButton;
    JButton audioButton;
    JButton pauseButton;
    JButton retryButton;
    JButton undoButton;
    JButton saveButton;
    JButton exitButton;
    JButton langButton;
    JButton newButton;

    Language lang = LanguageProcess.getCurrentLanguage();
    //以下两个用Runnable实现多线程
    //退出
    private Runnable exitAction;
    public void exitAction(Runnable action) { this.exitAction = action; }
    //保存
    private Runnable saveAction;                       // 新增
    public void setSaveAction(Runnable action) {       // 新增
        this.saveAction=action;
    }

    int offSetX;    //偏移
    int offSetY;
    int width;
    int height;

    // 按钮状态
    private boolean isPaused=false;
    private boolean bgmPlaying=true;
    //private ImageIcon settingsIcon;
    private ImageIcon audioOnIcon;
    private ImageIcon audioOffIcon;
    private ImageIcon pauseIcon;
    private ImageIcon continueIcon;
    private ImageIcon retryIcon;
    private ImageIcon undoIcon;
    private ImageIcon saveIcon;
    private ImageIcon exitIcon;
    private ImageIcon newIcon;
    private ImageIcon langIcon;

    /*public BoardPanel boardP;

    public void setBoardPanel(BoardPanel boardPanel) {
        this.boardP = boardPanel;
    }*/
    private BoardPanel boardPanel;   // 新增

    public void setBoardPanel(BoardPanel boardPanel) {
        this.boardPanel = boardPanel;
    }

    public ControlPanel(StatusPanel statusPanel, int offSetX, int offSetY,int width, int height) {
        loadIcons();
        this.setLayout(null);
        this.setBounds(offSetX, offSetY, width, height);
        this.offSetX = offSetX;
        this.offSetY = offSetY;
        this.width = width;
        this.height = height;
        this.startButton = new JButton(LanguageProcess.getCurrentLanguage().start());
        this.statusPanel = statusPanel;
        int startWidth = 150;
        int startHeight = 50;
        int startX = (width - startWidth) / 2;
        int startY = (height - startHeight) / 2-20;
        startButton.setBounds(startX, startY, startWidth, startHeight);
        if(LanguageProcess.getCurrentLanguage()==Chinese.INSTANCE){
            startButton.setFont(new Font("微软雅黑", Font.BOLD, 25));
        }else{
            startButton.setFont(new Font("Arial", Font.BOLD, 25));
        }

        startButton.setFocusPainted(false);
        this.add(startButton);
        //开始之后
        this.startButton.addActionListener(e -> {
            //statusPanel.setStatus("RUN");
            StatusPanel.resetTimer();
            StatusPanel.startTimer();
            startButton.setVisible(false);

            statusPanel.setStatus(LanguageProcess.getCurrentLanguage().score(0)+"×0");
            //welcomeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            // 显示控制按钮
            //settingsButton.setVisible(true);
            audioButton.setVisible(true);
            pauseButton.setVisible(true);
            retryButton.setVisible(true);
            undoButton.setVisible(true);
            exitButton.setVisible(true);
            saveButton.setVisible(true);
            newButton.setVisible(true);
            langButton.setVisible(true);
        });

        //设置按钮
        //settingsButton = createIconButton(settingsIcon, "设置");

        final int iconSize = 45;    //icon大小
        final int gap = 10;   //两个按钮之间的间距
        final int leftX = 20;   //左边距离
        int rightX = width-iconSize-20;    //右边极点
        int bottomY = height - iconSize-50;
        /*settingsButton.setBounds(leftX, bottomY, iconSize, iconSize);
        settingsButton.addActionListener(this::onSettingsClick);
        settingsButton.setVisible(false);
        this.add(settingsButton);*/

        //音频控制按钮
        audioButton = createIconButton(audioOnIcon/*, "音频控制"*/); // 初始未播放
        audioButton.setBounds(leftX + iconSize + gap, bottomY, iconSize, iconSize);
        audioButton.addActionListener(this::onAudioClick);
        audioButton.setVisible(false);
        this.add(audioButton);

        //暂停按钮
        pauseButton = createIconButton(pauseIcon/*, "暂停"*/);
        pauseButton.setBounds(rightX-iconSize-gap, bottomY, iconSize, iconSize);
        pauseButton.addActionListener(this::onPauseClick);
        pauseButton.setVisible(false);
        this.add(pauseButton);

        //重开按钮
        retryButton = createIconButton(retryIcon/*, "重新开始"*/);
        retryButton.setBounds(rightX-2*iconSize-2*gap, bottomY, iconSize, iconSize);
        retryButton.addActionListener(this::onRetryClick);
        retryButton.setVisible(false);
        this.add(retryButton);
        //撤销按钮
        undoButton=createIconButton(undoIcon/*,"回到上一步"*/);
        undoButton.setBounds(leftX+iconSize*2+2*gap,bottomY,iconSize,iconSize);
        undoButton.addActionListener(this::onUndoClick);
        undoButton.setVisible(false);
        this.add(undoButton);

        // 保存按钮（原设置按钮位置）
        saveButton = createIconButton(saveIcon/*, "保存"*/);
        saveButton.setBounds(leftX, bottomY, iconSize, iconSize);
        saveButton.addActionListener(this::onSaveClick);
        saveButton.setVisible(false);
        this.add(saveButton);

        // 退出按钮（最右边）
        //int exitX = width - iconSize - 20;               // 靠右
        //int pauseX = exitX - iconSize - gap;             // 暂停在左边
        exitButton = createIconButton(exitIcon/*, "退出"*/);
        exitButton.setBounds(rightX-10, bottomY, iconSize, iconSize);
        exitButton.addActionListener(this::onExitClick);
        //exitButton.setVisible(false);
        this.add(exitButton);

        langButton=createIconButton(langIcon/*,"切换语言"*/);
        langButton.setBounds(leftX+gap*3+iconSize*3,bottomY,iconSize,iconSize);
        langButton.addActionListener(this::onLangClick);
        langButton.setVisible(false);
        this.add(langButton);

        newButton=createIconButton(newIcon/*,"开始新游戏"*/);
        newButton.setBounds(rightX-gap*3-iconSize*3,bottomY,iconSize,iconSize);
        newButton.addActionListener(this::onNewClick);
        newButton.setVisible(false);
        this.add(newButton);
        refreshTooltips();
    }

    // 加载图标资源
    private void loadIcons() {
        final int iconSize=45;
        //settingsIcon = loadAndScaleIcon("settings.png", iconSize, iconSize);
        audioOnIcon= loadAndScaleIcon("audio_on.png", iconSize, iconSize);
        audioOffIcon = loadAndScaleIcon("audio_off.png", iconSize, iconSize);
        pauseIcon=loadAndScaleIcon("pause.png", iconSize, iconSize);
        continueIcon = loadAndScaleIcon("continue.png", iconSize, iconSize);
        undoIcon=loadAndScaleIcon("undo.png",iconSize,iconSize);
        retryIcon=loadAndScaleIcon("retry.png",iconSize,iconSize);
        saveIcon = loadAndScaleIcon("save.png", iconSize, iconSize);
        exitIcon = loadAndScaleIcon("exit.png", iconSize, iconSize);
        langIcon=loadAndScaleIcon("language.png",iconSize,iconSize);
        newIcon=loadAndScaleIcon("new_game.png",iconSize,iconSize);
    }

    /**
    * 创建纯图标按钮，鼠标靠近时候会有一个边框，这里利用swing的border实现；累死我啦
     * 其中的tooltip是鼠标靠近时候显示的文字
    * */
    private JButton createIconButton(ImageIcon icon/*,String tooltip*/) {
        JButton btn = new JButton(icon);
        //btn.setToolTipText(tooltip);
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
        Image icon =ResourceProcess.loadImage("menus/"+filename);
        Image img = icon.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    // ========== 事件处理 ==========

    /*//设置
    private void onSettingsClick(ActionEvent e) {
        //后续实现！

    }

    /**音频启停*/
    private void onAudioClick(ActionEvent e) {
        AudioProcess.playClick1();
        if (bgmPlaying) {
            AudioProcess.stopBgm();
            audioButton.setIcon(audioOffIcon);
            //audioButton.setToolTipText("播放音乐");
            bgmPlaying = false;
        } else {
            AudioProcess.playBgm();
            audioButton.setIcon(audioOnIcon);
            //audioButton.setToolTipText("停止音乐");
            bgmPlaying = true;
        }

    }

    /** 暂停/继续,能控制计时器*/
    private void onPauseClick(ActionEvent e) {
        AudioProcess.playClick1();
        if (!isPaused) {
            statusPanel.stopTimer();
            pauseButton.setIcon(continueIcon);
            //pauseButton.setToolTipText("继续");
            isPaused = true;

        } else {
            statusPanel.startTimer();
            pauseButton.setIcon(pauseIcon);
            //pauseButton.setToolTipText("暂停");
            isPaused = false;
            // 恢复游戏交互
        }

    }
    /** 重来 */
    /*private void onRetryClick(ActionEvent e) {
        AudioProcess.playClick1();
        if (boardPanel != null) {
            boardPanel.resetGame();
            statusPanel.resetTimer();
            statusPanel.resetScore();
            statusPanel.setStatus("READY");
            // 重置暂停状态
            isPaused = false;
            pauseButton.setIcon(pauseIcon);
            pauseButton.setToolTipText("暂停");
        }
    }*/
    private void onRetryClick(ActionEvent re) {
        AudioProcess.playClick1();
        //pauseButton.setToolTipText("重新开始");
        if (boardPanel!=null) {
            boardPanel.resetGame();
        }
        statusPanel.resetTimer();
        statusPanel.resetScore();
        statusPanel.setStatus(LanguageProcess.getCurrentLanguage().ready());
        resetToStart();   // 让控制面板恢复初始状态
    }
    /**
     * 重来之后的重置控制面板到初始状态
     */
    public void resetToStart() {
        startButton.setVisible(true);
        //settingsButton.setVisible(false);
        audioButton.setVisible(false);
        pauseButton.setVisible(false);
        retryButton.setVisible(false);
        undoButton.setVisible(false);
        exitButton.setVisible(true);
        saveButton.setVisible(false);
        langButton.setVisible(false);
        newButton.setVisible(false);

        // 重置暂停状态
        isPaused = false;
        pauseButton.setIcon(pauseIcon);
        //pauseButton.setToolTipText("暂停");
        //AudioProcess.playBgm();
    }
    /** 撤销 */
    private void onUndoClick(ActionEvent undo) {
        AudioProcess.playClick1();
        if (boardPanel != null) {
            boardPanel.undoStep();
        }
        //pauseButton.setToolTipText("撤销");
    }
    /**保存*/
    private void onSaveClick(ActionEvent save) {
        AudioProcess.playClick1();
        if (saveAction!=null) {
            saveAction.run();
        }
        //pauseButton.setToolTipText("保存");
    }
    /**退出*/
    private void onExitClick(ActionEvent e) {
        AudioProcess.playClickSpecial();
        if (exitAction!=null) {
            exitAction.run();
        }
        //pauseButton.setToolTipText("退出");
    }

    /***/
    private void onLangClick(ActionEvent l){
        LanguageProcess.switchLanguage();
        AudioProcess.playClick1();
        refreshTooltips();
    }
    /***/
    private void onNewClick(ActionEvent n){
        AudioProcess.playClickSpecial();


    }
    /**即load之后的“强制”暂停*/
    public void forcedPause() {
        if (!isPaused) {
            statusPanel.stopTimer();
            isPaused = true;
            pauseButton.setIcon(continueIcon);
            //pauseButton.setToolTipText("继续");
        }
    }
    /**即load之后的“强制”开始*/
    public void forceToGameMode() {
        startButton.setVisible(false);
        audioButton.setVisible(true);
        pauseButton.setVisible(true);
        retryButton.setVisible(true);
        undoButton.setVisible(true);
        saveButton.setVisible(true);
        exitButton.setVisible(true);
        langButton.setVisible(true);
        newButton.setVisible(true);
    }
    /*public void setTips() {
        startButton.setVisible(false);
        audioButton.setVisible(true);
        pauseButton.setVisible(true);
        retryButton.setVisible(true);
        undoButton.setVisible(true);
        saveButton.setVisible(true);
        exitButton.setVisible(true);
        langButton.setVisible(true);
        newButton.setVisible(true);
    }*/
    private void refreshTooltips() {
        Language lang = LanguageProcess.getCurrentLanguage();
        audioButton.setToolTipText(bgmPlaying?lang.audioOnTooltip():lang.audioOffTooltip());
        pauseButton.setToolTipText(isPaused?lang.continueTooltip():lang.pauseTooltip());
        retryButton.setToolTipText(lang.retryTooltip());
        undoButton.setToolTipText(lang.undoTooltip());
        saveButton.setToolTipText(lang.saveTooltip());
        exitButton.setToolTipText(lang.exitTooltip());
        langButton.setToolTipText(lang.getLanguageTooltip());
        newButton.setToolTipText(lang.new_game());
        //statusPanel.setStatus("");
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
