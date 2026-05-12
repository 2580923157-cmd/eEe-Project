package ui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Login {

    public static void loginFrame() {
        // 创建主窗口
        JFrame loginBox = new JFrame("连连看 - 登录");
        loginBox.setSize(600, 450);
        loginBox.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginBox.setLayout(null);
        // 窗口居中
        loginBox.setLocationRelativeTo(null);
        // 设置背景色
        loginBox.getContentPane().setBackground(new Color(240, 248, 255)); // AliceBlue

        // 标题
        JLabel welcomeText = new JLabel("欢迎来到连连看");
        welcomeText.setSize(400, 60);
        welcomeText.setLocation(100, 30);
        welcomeText.setFont(new Font("微软雅黑", Font.BOLD, 36));
        welcomeText.setForeground(new Color(25, 25, 112)); // MidnightBlue
        welcomeText.setHorizontalAlignment(SwingConstants.CENTER);
        loginBox.add(welcomeText);

        JLabel subtitle = new JLabel("请先登录或注册");
        subtitle.setSize(400, 30);
        subtitle.setLocation(100, 90);
        subtitle.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        subtitle.setForeground(Color.DARK_GRAY);
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        loginBox.add(subtitle);

        // 用户名标签和输入框
        JLabel userLabel = new JLabel("用户名：");
        userLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        userLabel.setSize(100, 30);
        userLabel.setLocation(120, 150);
        loginBox.add(userLabel);

        JTextField userField = new JTextField();
        userField.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        userField.setSize(250, 35);
        userField.setLocation(230, 148);
        userField.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 1)); // CornflowerBlue
        loginBox.add(userField);

        // 密码标签和密码框（回显*）
        JLabel passLabel = new JLabel("密　码：");
        passLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        passLabel.setSize(100, 30);
        passLabel.setLocation(120, 210);
        loginBox.add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        passField.setSize(250, 35);
        passField.setLocation(230, 208);
        passField.setEchoChar('*'); // 密码回显为*
        passField.setBorder(BorderFactory.createLineBorder(new Color(100, 149, 237), 1));
        loginBox.add(passField);

        // Login 按钮（暂不实现具体登录逻辑）
        JButton lgButton = new JButton("登录");
        styleButton(lgButton, new Color(70, 130, 180)); // SteelBlue
        lgButton.setSize(130, 45);
        lgButton.setLocation(150, 300);
        loginBox.add(lgButton);

        // Register 按钮
        JButton regButton = new JButton("注册");
        styleButton(regButton, new Color(34, 139, 34)); // ForestGreen
        regButton.setSize(130, 45);
        regButton.setLocation(320, 300);
        loginBox.add(regButton);

        // 为注册按钮添加事件：将用户名和密码写入users.txt
        regButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText().trim();
                String password = new String(passField.getPassword()).trim();

                // 输入校验
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(loginBox,
                            "用户名和密码不能为空！",
                            "注册失败",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (username.contains(",")) {
                    JOptionPane.showMessageDialog(loginBox,
                            "用户名不能包含逗号！",
                            "注册失败",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // 写入文件（追加模式，格式：用户名,密码）
                try (PrintWriter writer = new PrintWriter(new FileWriter("users.txt", true))) {
                    writer.println(username + "," + password);
                    JOptionPane.showMessageDialog(loginBox,
                            "注册成功！欢迎 " + username,
                            "注册",
                            JOptionPane.INFORMATION_MESSAGE);
                    // 清空输入框
                    userField.setText("");
                    passField.setText("");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(loginBox,
                            "文件写入失败：" + ex.getMessage(),
                            "错误",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 登录按钮暂时给出提示（后续可扩展）
        lgButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(loginBox,
                    "登录功能请在此处实现",
                    "提示",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        loginBox.setVisible(true);
    }

    // 按钮样式统一设置
    private static void styleButton(JButton button, Color bgColor) {
        button.setFont(new Font("微软雅黑", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // 测试主方法（可删除）
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

