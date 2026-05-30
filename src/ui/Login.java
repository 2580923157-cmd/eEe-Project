package ui;

import model.UserService;
import model.User;

import javax.swing.*;
import javax.swing.JButton;
import java.awt.*;

import support.Language;
import utils.LanguageProcess;

public class Login {

    private static UserService userService = new UserService();

    public static void loginFrame() {

        final int WIDTH=600;
        final int HEIGHT=450;
        Language lang=LanguageProcess.getCurrentLanguage();
        JFrame loginBox = new JFrame(/*lang.loginTitle0()*/);

        loginBox.setSize(WIDTH, HEIGHT);
        loginBox.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginBox.setLayout(null);
        loginBox.setLocationRelativeTo(null);
        loginBox.getContentPane().setBackground(new Color(240, 248, 255));

        //标题 -行1
        JLabel welcomeText1 = new JLabel(/*lang.loginTitle()*/);
        welcomeText1.setSize(400, 50);
        welcomeText1.setLocation(100, 10);
        welcomeText1.setFont(new Font("微软雅黑", Font.BOLD, 28));
        welcomeText1.setForeground(new Color(25, 25, 112));
        welcomeText1.setHorizontalAlignment(SwingConstants.CENTER);
        loginBox.add(welcomeText1);
        //标题-行2
        JLabel welcomeText2 = new JLabel(/*lang.loginTitle()*/);
        welcomeText2.setSize(400, 60);
        welcomeText2.setLocation(100, 45);
        welcomeText2.setFont(new Font("微软雅黑", Font.BOLD, 38));
        welcomeText2.setForeground(new Color(100,130,60));
        welcomeText2.setHorizontalAlignment(SwingConstants.CENTER);
        loginBox.add(welcomeText2);

        //副标题
        JLabel subtitle = new JLabel(/*lang.loginSubtitle()*/);
        subtitle.setSize(400, 30);
        subtitle.setLocation(100, 105);
        subtitle.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        subtitle.setForeground(Color.DARK_GRAY);
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        loginBox.add(subtitle);

        //用户名框
        JLabel userLabel = new JLabel(/*"用户名："*/);
        userLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        userLabel.setSize(100, 30);
        userLabel.setLocation(120, 150);
        loginBox.add(userLabel);

        JTextField userField = new JTextField();
        userField.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        userField.setSize(250, 35);
        userField.setLocation(230, 148);
        userField.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 1));
        loginBox.add(userField);

        //密码框
        JLabel passLabel = new JLabel(/*"密　码："*/);
        passLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        passLabel.setSize(100, 30);
        passLabel.setLocation(120, 210);
        loginBox.add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        passField.setSize(250, 35);
        passField.setLocation(230, 208);
        passField.setEchoChar('*'); //回显
        passField.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 1));
        loginBox.add(passField);

        // 登录按钮
        JButton lgButton = new JButton(/*"登录"*/);
        styleButton(lgButton, new Color(70, 130, 180));
        lgButton.setSize(110, 45);
        lgButton.setLocation(120, 300);
        loginBox.add(lgButton);

        // 注册按钮
        JButton regButton = new JButton(/*"注册"*/);
        styleButton(regButton, new Color(34, 139, 34));
        regButton.setSize(110, 45);
        regButton.setLocation(245, 300);
        loginBox.add(regButton);

        // 游客登录按钮
        JButton gueButton = new JButton(/*"游客登录"*/);
        styleButton(gueButton, new Color(180, 175, 70));
        gueButton.setSize(110, 45);
        gueButton.setLocation(370, 300);
        loginBox.add(gueButton);

        //退出按钮
        JButton exitButton=new JButton();
        //styleButton(exitButton,new Color());
        try {
            ImageIcon icon = new ImageIcon("resource\\menus\\exit.png");
            Image exitImg = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            exitButton.setIcon(new ImageIcon(exitImg));
        } catch (Exception e) {
            exitButton.setText("Esc");
        }
        //exitButton.setToolTipText(LanguageProcess.getCurrentLanguage().getLanguageTooltip());
        exitButton.setContentAreaFilled(false);
        exitButton.setOpaque(false);
        exitButton.setBorderPainted(false);
        exitButton.setBounds(520, 350, 80, 80);
        //exitButton.setBackground(Color.RED);
        loginBox.add(exitButton);


        JButton languageButton = new JButton(/*"Language"*/);
        try {
            ImageIcon icon = new ImageIcon("resource\\menus\\language.png");
            Image langImg = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            languageButton.setIcon(new ImageIcon(langImg));
        } catch (Exception e) {
            languageButton.setText("🌐");
        }
        languageButton.setContentAreaFilled(false);
        languageButton.setOpaque(false);
        languageButton.setToolTipText(LanguageProcess.getCurrentLanguage().getLanguageTooltip());
        languageButton.setBounds(15, 15, 32, 32);
        loginBox.add(languageButton);

        //现在确定做什么

        //语言设置
        // 窗口标题
        //JFrame loginBox = new JFrame(lang.loginTitle0());
        loginBox.setTitle(lang.loginTitle0());
        // 标题
        welcomeText1.setText(lang.loginTitle1());
        welcomeText2.setText(lang.loginTitle2());
        subtitle.setText(lang.loginSubtitle());
        // 标签
        userLabel.setText(lang.loginUsernameLabel());
        passLabel.setText(lang.loginPasswordLabel());
        // 按钮
        lgButton.setText(lang.loginLogin());
        regButton.setText(lang.loginReg());
        gueButton.setText(lang.loginGuest());

        // 登录
        lgButton.addActionListener(e -> {
            Language nowLang = LanguageProcess.getCurrentLanguage();
            String username = userField.getText().trim();
            String password = new String(passField.getPassword()).trim();
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(loginBox, nowLang.loginEmptyFields(), nowLang.loginFailTitle()/*"登录失败"*/, JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (userService.login(username, password)) {
                JOptionPane.showMessageDialog(loginBox, /*"登录成功！欢迎 " + username*/nowLang.loginSuccess(username));
                loginBox.dispose(); // 关闭登录窗口，后续进入游戏
                //这里的游戏设置标题、长、宽
                /*GameFrame frame = new GameFrame("Connect+ 连连看", 1000, 1000);
                frame.repaint();*/
                SwingUtilities.invokeLater(() -> {
                    Menu menu = new Menu(userService.getCurrentUser());
                    menu.setVisible(true);
                });
            } else {
                JOptionPane.showMessageDialog(loginBox, nowLang.loginFail()/*"用户名或密码错误！"*/,nowLang.loginFailTitle() /*"登录失败"*/, JOptionPane.ERROR_MESSAGE);
            }
        });

        // 注册
        regButton.addActionListener(e -> {
            Language nowLang = LanguageProcess.getCurrentLanguage();
            String username = userField.getText().trim();
            String password = new String(passField.getPassword()).trim();
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(loginBox, nowLang.loginEmptyFields()/*"用户名或密码不能为空！"*/, nowLang.loginRegFailTitle()/*"注册失败"*/, JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (username.contains(",")) {
                JOptionPane.showMessageDialog(loginBox,nowLang.loginCommaError() /*"用户名不能包含逗号！"*/, nowLang.loginRegFailTitle()/*"注册失败"*/, JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean success = userService.register(username, password);
            if (success) {
                JOptionPane.showMessageDialog(loginBox, nowLang.loginRegSuccess()/*"注册成功！请登录"*/, nowLang.loginRegSuccessTitle()/*"注册"*/, JOptionPane.INFORMATION_MESSAGE);
                userField.setText("");
                passField.setText("");
            } else {
                JOptionPane.showMessageDialog(loginBox, nowLang.loginRegFail()/*"用户名已存在，请更换！"*/, nowLang.loginRegFailTitle()/*"注册失败"*/, JOptionPane.WARNING_MESSAGE);
            }
        });

        // 游客登录
        gueButton.addActionListener(e -> {
            Language nowLang=LanguageProcess.getCurrentLanguage();
            userService.loginAsGuest();
            JOptionPane.showMessageDialog(loginBox, nowLang.loginGuestMsg()/*"以游客身份进入游戏"*/,nowLang.loginGuestMsgTitle(),JOptionPane.INFORMATION_MESSAGE);
            loginBox.dispose(); // 关闭窗口，进入游戏
            //现在进游戏，这里的游戏设置标题、长、宽
            /*GameFrame frame = new GameFrame("Connect+ 连连看", 1000, 1000);
            frame.repaint();*/
            User guest=new User();
            SwingUtilities.invokeLater(() -> {
                Menu menu=new Menu(guest);
                menu.setVisible(true);
            });
        });

        languageButton.addActionListener(e -> {
            LanguageProcess.switchLanguage();          // 切换为下一语言
            Language nowLang = LanguageProcess.getCurrentLanguage();

            loginBox.setTitle(nowLang.loginTitle0());
            welcomeText1.setText(nowLang.loginTitle1());
            welcomeText2.setText(nowLang.loginTitle2());
            subtitle.setText(nowLang.loginSubtitle());
            userLabel.setText(nowLang.loginUsernameLabel());
            passLabel.setText(nowLang.loginPasswordLabel());
            lgButton.setText(nowLang.loginLogin());
            regButton.setText(nowLang.loginReg());
            gueButton.setText(nowLang.loginGuest());
            languageButton.setToolTipText(nowLang.getLanguageTooltip());
        });

        exitButton.addActionListener(e -> System.exit(-1));

        loginBox.setVisible(true);
    }



    //获取当前登录的用户给后端
    public static User getCurrentUser() {
        return userService.getCurrentUser();
    }

    // 按钮样式
    private static void styleButton(JButton button, Color bgColor) {
        button.setFont(new Font("微软雅黑", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }




    //这里就是主入口
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login::loginFrame);
    }
}
//public class Login {
//    public static void loginFrame(){
        /*JFrame loginBox=new JFrame("登录");
        loginBox.setSize(1000,800);
        loginBox.setVisible(true);
        loginBox.setLayout(null);*/
        /*JPanel loginPanel=new JPanel();
        loginPanel.setVisible(false);
        loginPanel.setSize(1000,800);
        loginPanel.setLocation(0,0);*/

       // JLabel welcomeText =new JLabel("请先登录");
        //welcomeText.setText("<html><body>欢迎来到连连看！<br>  请先登录</body></html>");
        /*welcomeText.setSize(1000,120);
        welcomeText.setLocation(300,100);
        welcomeText.setFont(new Font("微软雅黑",Font.PLAIN,50));
        loginBox.add(welcomeText);
        //loginPanel.setVisible(true);
        JButton lgButton=new JButton("Login");
        lgButton.setFont(new Font("微软雅黑",Font.PLAIN,20));
        lgButton.setSize(150,50);
        lgButton.setLocation(100,500);
        loginBox.add(lgButton);

        JButton regButton =new JButton("Register");
        regButton.setFont(new Font("微软雅黑",Font.PLAIN,20));
        regButton.setSize(150,50);
        regButton.setLocation(700,500);
        loginBox.add(regButton);*/

        /*
        * JFrame loginBox=new JFrame("Login");
            loginBox.setSize(300,300);
            loginBox.setLayout(null);
            JButton lgButton=new JButton("Login");
            lgButton.setSize(100,50);
            lgButton.setLocation(100,200);
            JTextArea lgName=new JTextArea();
            lgName.setSize(100,50);
            lgName.setLocation(100,120);
            loginBox.add(lgButton);
            loginBox.add(lgName);
        * */

//    }
//}


