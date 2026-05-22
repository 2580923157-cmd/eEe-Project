/*package app;

import javax.swing.*;

public class TestMain {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
        });
    }
}
*//*

package app;

import model.User;
import model.UserDAO;
import ui.Login;

import javax.swing.*;
import java.awt.*;

public class TestMain {
    private static UserDAO userDAO = new UserDAO();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createTestFrame());
    }

    // 测试主界面
    private static void createTestFrame() {
        JFrame frame = new JFrame("连连看 —— 功能测试");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(245, 245, 245));

        // 标题
        JLabel title = new JLabel("功能测试界面");
        title.setFont(new Font("微软雅黑", Font.BOLD, 28));
        title.setBounds(120, 20, 300, 50);
        frame.add(title);

        // ================== 测试1：注册 ==================
        JLabel label1 = new JLabel("测试注册：");
        label1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        label1.setBounds(50, 90, 100, 30);
        frame.add(label1);

        JTextField regUser = new JTextField();
        regUser.setBounds(150, 90, 120, 30);
        regUser.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        frame.add(regUser);

        JPasswordField regPwd = new JPasswordField();
        regPwd.setBounds(280, 90, 120, 30);
        regPwd.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        frame.add(regPwd);

        JButton btnReg = new JButton("注册");
        btnReg.setBounds(410, 90, 80, 30);
        frame.add(btnReg);

        // ================== 测试2：登录 ==================
        JLabel label2 = new JLabel("测试登录：");
        label2.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        label2.setBounds(50, 140, 100, 30);
        frame.add(label2);

        JTextField loginUser = new JTextField();
        loginUser.setBounds(150, 140, 120, 30);
        loginUser.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        frame.add(loginUser);

        JPasswordField loginPwd = new JPasswordField();
        loginPwd.setBounds(280, 140, 120, 30);
        loginPwd.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        frame.add(loginPwd);

        JButton btnLogin = new JButton("登录");
        btnLogin.setBounds(410, 140, 80, 30);
        frame.add(btnLogin);

        // ================== 测试3：游客登录 ==================
        JButton btnGuest = new JButton("游客登录测试");
        btnGuest.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        btnGuest.setBounds(150, 200, 200, 40);
        frame.add(btnGuest);

        // ================== 测试4：打开原登录界面 ==================
        JButton btnOpenLogin = new JButton("打开原登录窗口");
        btnOpenLogin.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        btnOpenLogin.setBounds(150, 260, 200, 40);
        frame.add(btnOpenLogin);

        // ================== 按钮事件 ==================
        // 注册
        btnReg.addActionListener(e -> {
            String u = regUser.getText().trim();
            String p = new String(regPwd.getPassword()).trim();

            if (u.isEmpty() || p.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "用户名密码不能为空");
                return;
            }

            boolean ok = userDAO.registerUser(u, p);
            if (ok) {
                JOptionPane.showMessageDialog(frame, "注册成功！");
            } else {
                JOptionPane.showMessageDialog(frame, "用户名已存在！");
            }
        });

        // 登录
        btnLogin.addActionListener(e -> {
            String u = loginUser.getText().trim();
            String p = new String(loginPwd.getPassword()).trim();
            boolean ok = userDAO.validateUser(u, p);
            if (ok) {
                JOptionPane.showMessageDialog(frame, "登录成功！");
            } else {
                JOptionPane.showMessageDialog(frame, "用户名或密码错误");
            }
        });

        // 游客
        btnGuest.addActionListener(e -> {
            User guest = new User();
            JOptionPane.showMessageDialog(frame,
                    "游客登录成功\n能否保存游戏：" + guest.canSaveGame());
        });

        // 打开你原来的登录界面
        btnOpenLogin.addActionListener(e -> {
            Login.loginFrame();
        });

        frame.setVisible(true);
    }
}*/

package app;

import model.GameState;
import model.SaveManager;
import model.User;
import model.UserDAO;
import ui.GameFrame;
import ui.Login;

import javax.swing.*;
import java.awt.*;

public class TestMain {
    private static UserDAO userDAO = new UserDAO();
    private static SaveManager saveManager = new SaveManager();
    private static GameFrame gameFrame;  // 游戏窗口对象

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createTestFrame());
    }


    // 存档接口
    public static void saveGame(String userName) {
        if (gameFrame == null || userName == null || userName.isBlank()) {
            JOptionPane.showMessageDialog(null, "游戏未打开或用户名为空！");
            return;
        }
        GameState state = gameFrame.exportGameState();
        saveManager.save(userName, state);
        JOptionPane.showMessageDialog(null, "存档成功！");
    }

    // 读档接口
    public static void loadGame(String userName) {
        if (gameFrame == null || userName == null || userName.isBlank()) {
            JOptionPane.showMessageDialog(null, "游戏未打开或用户名为空！");
            return;
        }
        GameState state = saveManager.load(userName);
        gameFrame.loadGameState(state);
        JOptionPane.showMessageDialog(null, "读档成功！");
    }
    // ==================================================================

    // 测试主界面
    private static void createTestFrame() {
        JFrame frame = new JFrame("连连看 —— 功能测试");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(245, 245, 245));

        // 标题
        JLabel title = new JLabel("功能测试界面");
        title.setFont(new Font("微软雅黑", Font.BOLD, 28));
        title.setBounds(120, 20, 300, 50);
        frame.add(title);

        // ================== 测试1：注册 ==================
        JLabel label1 = new JLabel("测试注册：");
        label1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        label1.setBounds(50, 90, 100, 30);
        frame.add(label1);

        JTextField regUser = new JTextField();
        regUser.setBounds(150, 90, 120, 30);
        regUser.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        frame.add(regUser);

        JPasswordField regPwd = new JPasswordField();
        regPwd.setBounds(280, 90, 120, 30);
        regPwd.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        frame.add(regPwd);

        JButton btnReg = new JButton("注册");
        btnReg.setBounds(410, 90, 80, 30);
        frame.add(btnReg);

        // ================== 测试2：登录 ==================
        JLabel label2 = new JLabel("测试登录：");
        label2.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        label2.setBounds(50, 140, 100, 30);
        frame.add(label2);

        JTextField loginUser = new JTextField();
        loginUser.setBounds(150, 140, 120, 30);
        loginUser.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        frame.add(loginUser);

        JPasswordField loginPwd = new JPasswordField();
        loginPwd.setBounds(280, 140, 120, 30);
        loginPwd.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        frame.add(loginPwd);

        JButton btnLogin = new JButton("登录");
        btnLogin.setBounds(410, 140, 80, 30);
        frame.add(btnLogin);

        // ================== 测试3：游客登录 ==================
        JButton btnGuest = new JButton("游客登录测试");
        btnGuest.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        btnGuest.setBounds(150, 200, 200, 40);
        frame.add(btnGuest);

        // ================== 测试4：打开原登录界面 ==================
        JButton btnOpenLogin = new JButton("打开原登录窗口");
        btnOpenLogin.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        btnOpenLogin.setBounds(150, 260, 200, 40);
        frame.add(btnOpenLogin);

        // ================== 按钮事件 ==================
        // 注册
        btnReg.addActionListener(e -> {
            String u = regUser.getText().trim();
            String p = new String(regPwd.getPassword()).trim();

            if (u.isEmpty() || p.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "用户名密码不能为空");
                return;
            }

            boolean ok = userDAO.registerUser(u, p);
            if (ok) {
                JOptionPane.showMessageDialog(frame, "注册成功！");
            } else {
                JOptionPane.showMessageDialog(frame, "用户名已存在！");
            }
        });

        // 登录 → 登录成功自动打开游戏
        btnLogin.addActionListener(e -> {
            String u = loginUser.getText().trim();
            String p = new String(loginPwd.getPassword()).trim();
            boolean ok = userDAO.validateUser(u, p);
            if (ok) {
                JOptionPane.showMessageDialog(frame, "登录成功！游戏已打开");
                // 登录成功自动打开游戏
                gameFrame = new GameFrame("连连看", 1000, 1000);
            } else {
                JOptionPane.showMessageDialog(frame, "用户名或密码错误");
            }
        });

        // 游客
        btnGuest.addActionListener(e -> {
            User guest = new User();
            JOptionPane.showMessageDialog(frame,
                    "游客登录成功\n能否保存游戏：" + guest.canSaveGame());
        });

        // 打开你原来的登录界面
        btnOpenLogin.addActionListener(e -> {
            Login.loginFrame();
        });

        frame.setVisible(true);
    }
}
