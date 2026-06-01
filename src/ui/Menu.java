/*package ui;

import javax.swing.*;

public class Menu extends JPanel {
    JFrame menu = new JFrame("主菜单");

    JPanel startPanel=new JPanel();
    JButton startButton=new JButton("开始游戏");
    JButton exitButton=new JButton("退出");



}*/

package ui;

import model.User;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import support.Language;
import utils.LanguageProcess;
import utils.ResourceProcess;

/**
 * 主菜单窗口
 */
public class Menu extends JFrame {
    //private String username;
    private User user;
    JButton startButton=new JButton();
    //JButton startButton = createButton("开始游戏", 225, 190);
    JButton continueButton=new JButton();
    JButton exitButton=new JButton();
    JButton languageButton=new JButton();
    boolean hasSave;
    public Menu(User user) {
        super("夏日大挑战 - 主菜单");
        this.user = user;
        setSize(600, 500);
        Image bgImage = ResourceProcess.loadImage("backgrounds/beach.png");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(240, 248, 255));
        setContentPane(new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
            }
        });
        getContentPane().setLayout(null);
// 确保背景面板不透明，否则可能不显示
        ((JPanel) getContentPane()).setOpaque(true);

        /*setContentPane(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
            }
        });
        getContentPane().setLayout(null);*/
// 确保背景面板不透明，否则可能不显示
        ((JPanel) getContentPane()).setOpaque(true);
        Language lang = LanguageProcess.getCurrentLanguage();
        //欢迎文字(右上)
        JLabel welcomeLabel = new JLabel(/*"欢迎，" + user.getUserName()*/);
        welcomeLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
        welcomeLabel.setForeground(new Color(25, 25, 112));
        welcomeLabel.setSize(300, 30);
        welcomeLabel.setLocation(260, 20);
        welcomeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(welcomeLabel);

        //welcome logo（正上）
        JLabel logoLabel = new JLabel();
        logoLabel.setSize(360, 180);
        logoLabel.setLocation(158, 20);
        Image titleImage = ResourceProcess.loadImage("backgrounds/title.png");
        //ImageIcon logoIcon = new ImageIcon("resource/menus/logo.png");
        //ImageIcon titleIcon=new ImageIcon(titleImage);
        //logoLabel.setIcon(titleIcon);
        if (titleImage != null) {
            Image scaled=titleImage.getScaledInstance(289, 139, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(scaled));
        } else {
            logoLabel.setText("Title");  // 加载失败时显示文字
        }
        add(logoLabel);

        //功能按钮（中）
        hasSave=new File("saves\\"+user.getUserName()+".txt").exists();
        if(user.getUserName().equals("Guest"))
            hasSave=false;
        //String startText = hasSave ? "开始新游戏" : "开始游戏";
        startButton = createButton(hasSave?lang.menuStartNewGame():lang.menuStartGame(),
                225, 200);
        continueButton = createButton(lang.menuContinueGame(), 225, 260);
        exitButton = createButton(lang.menuExitGame(), 225, 320);

        //语言按钮（左上）
        //private JButton languageButton, helpButton;

        Image languageIcon= ResourceProcess.loadImage("menus/language.png");
        try {
            //ImageIcon icon = new ImageIcon("resource\\menus\\language.png");
            //Image langImg = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            Image img=languageIcon.getScaledInstance(32,32,Image.SCALE_SMOOTH);
            languageButton.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            languageButton.setText("🌐");
        }
        languageButton.setToolTipText(LanguageProcess.getCurrentLanguage().getLanguageTooltip());
        languageButton.setBounds(15, 15, 32, 32);
        //languageButton.addActionListener(this::onLanguageSwitch);

        add(startButton);
        //add(continueButton);
        if(hasSave)
            add(continueButton);
        add(exitButton);
        add(languageButton);


        setTitle(lang.menuTitle());
        welcomeLabel.setText(lang.menuWelcome(user.getUserName()));
        startButton.setText(hasSave ? lang.menuStartNewGame() : lang.menuStartGame());
        continueButton.setText(lang.menuContinueGame());
        exitButton.setText(lang.menuExitGame());
        languageButton.setToolTipText(lang.getLanguageTooltip());

        //下面是事件
        //开始游戏
        startButton.addActionListener(e -> {
            //dispose();
            Language l =LanguageProcess.getCurrentLanguage();
            //String[] diffOptions=new String[]{l.easy(),l.hard()};

            //int choice=1;
            if (hasSave) {
                int choice=1,diff=0;
                choice = JOptionPane.showOptionDialog(this,
                        l.menuNewGameWarningMessage(),
                        l.WarningTitle(),
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        new Object[]{l.yes(), l.no()},   // 自定义按钮文字
                        l.no());
                if (choice==1){
                    return;
                }else{
                    diff=JOptionPane.showOptionDialog(this,
                            l.chooseDifficulty(),
                            l.difficultyTitle(),
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            new Object[]{l.easy(),l.hard()},
                            l.easy());
                    if (diff==JOptionPane.CLOSED_OPTION) {
                        return; // 用户关闭了对话框
                    }
                }

                GameFrame gameFrame=new GameFrame("夏日大挑战", 1000, 1000, user);
                if(diff==0){
                    gameFrame.startEasyMode();
                }else{
                    gameFrame.startHardMode();
                }
                dispose();
            }else{
                int diff=JOptionPane.showOptionDialog(this,
                        l.chooseDifficulty(),
                        l.difficultyTitle(),
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[]{l.easy(),l.hard()},
                        l.easy());
                if (diff==JOptionPane.CLOSED_OPTION) {
                    return; // 用户关闭了对话框
                }
                GameFrame gameFrame=new GameFrame("夏日大挑战", 1000, 1000, user);
                dispose();
                if(diff==0){
                    gameFrame.startEasyMode();
                }else{
                    gameFrame.startHardMode();
                }
                //dispose();
            }



            /*if (hasSave) {
                gameFrame.loadGame();  // 读取存档并恢复游戏状态
            }*/
        });

        //继续游戏
        continueButton.addActionListener(e -> {
            Language l = LanguageProcess.getCurrentLanguage();
            dispose();
            //JOptionPane.showMessageDialog(this, "关卡选择功能暂未开放", "提示", JOptionPane.INFORMATION_MESSAGE);
            GameFrame gameFrame=new GameFrame("夏日大挑战", 1000, 1000, user);
            if (hasSave) {
                gameFrame.loadGame();  // 读取存档并恢复游戏状态
            }else{
                JOptionPane.showMessageDialog(this, l.menuSaveNotFound(), l.WarningTitle(), JOptionPane.WARNING_MESSAGE);
            }
        });


        //语言切换
        languageButton.addActionListener(e -> {
            LanguageProcess.switchLanguage();          // 切换为下一语言
            Language nowLang = LanguageProcess.getCurrentLanguage();
            setTitle(nowLang.menuTitle());
            welcomeLabel.setText(nowLang.menuWelcome(user.getUserName()));
            startButton.setText(hasSave ? nowLang.menuStartNewGame() : nowLang.menuStartGame());
            continueButton.setText(nowLang.menuContinueGame());
            exitButton.setText(nowLang.menuExitGame());
            languageButton.setToolTipText(nowLang.getLanguageTooltip());
            languageButton.setToolTipText(nowLang.getLanguageTooltip());
        });

        //退出游戏
        exitButton.addActionListener(e ->{
            Language l = LanguageProcess.getCurrentLanguage();
            int choice = JOptionPane.showOptionDialog(this,
                    l.menuExitConfirmMessage(),
                    l.menuExitConfirmTitle(),
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new Object[]{l.yes(), l.no()},   // 自定义按钮文字
                    l.no());
            if (choice==0) {
                System.exit(-1);
            }

        });
    }
    /**模式选择，0为简单，1为困难
    public int gameMode(){
        Language l = LanguageProcess.getCurrentLanguage();
        int choice = JOptionPane.showOptionDialog(this,
                l.menuExitConfirmMessage(),
                l.menuExitConfirmTitle(),
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[]{l.yes(), l.no()},   // 自定义按钮文字
                l.no());
        return choice;
    }*/

    /**
     * 快速创建统一样式的按钮
     */
    private JButton createButton(String text, int x, int y) {
        JButton button = new JButton(text);
        ImageIcon btnBg = ResourceProcess.loadIcon("menus/menu_button.png");
        //JButton button = new JButton(text);
        button.setFont(new Font("微软雅黑", Font.BOLD, 20));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        //button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        //button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setCursor(Cursor.getDefaultCursor());
        button.setSize(150, 40);
        button.setLocation(x, y);
        if (btnBg != null) {
            Image img = btnBg.getImage().getScaledInstance(150, 80, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(img));
            button.setHorizontalTextPosition(SwingConstants.CENTER); // 文字居中在图标之上
            button.setVerticalTextPosition(SwingConstants.CENTER);
        }else{
            // 图片加载失败则使用默认背景色
            button.setBackground(new Color(70, 130, 180));
            button.setContentAreaFilled(true);
            button.setOpaque(true);
        }

        return button;
    }
}
