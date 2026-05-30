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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(240, 248, 255));

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
        logoLabel.setSize(200, 80);
        logoLabel.setLocation(200, 20);
        // 若要显示图片，取消以下注释并确保路径正确
        // ImageIcon logoIcon = new ImageIcon("resource/menus/logo.png");
        // logoLabel.setIcon(logoIcon);
        add(logoLabel);

        //功能按钮（中）
        hasSave = new File("saves\\" + user.getUserName() + ".txt").exists();
        if(user.getUserName().equals("Guest"))
            hasSave=false;
        //String startText = hasSave ? "开始新游戏" : "开始游戏";
        startButton = createButton(hasSave?lang.menuStartNewGame():lang.menuStartGame(),
                225, 200);
        continueButton = createButton(lang.menuContinueGame(), 225, 260);
        exitButton = createButton(lang.menuExitGame(), 225, 320);

        //语言按钮（左上）
        //private JButton languageButton, helpButton;

        try {
            ImageIcon icon = new ImageIcon("resource\\menus\\language.png");
            Image langImg = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            languageButton.setIcon(new ImageIcon(langImg));
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
            int choice=1;
            if (hasSave) {
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
                }


                GameFrame gameFrame=new GameFrame("夏日大挑战", 1000, 1000, user);
                dispose();
            }else{
                GameFrame gameFrame=new GameFrame("夏日大挑战", 1000, 1000, user);
                dispose();
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

    /**
     * 快速创建统一样式的按钮
     */
    private JButton createButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setFont(new Font("微软雅黑", Font.BOLD, 20));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setCursor(Cursor.getDefaultCursor());
        button.setSize(150, 40);
        button.setLocation(x, y);
        return button;
    }
}
