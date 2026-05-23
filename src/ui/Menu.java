/*package ui;

import javax.swing.*;

public class Menu extends JPanel {
    JFrame menu = new JFrame("主菜单");

    JPanel startPanel=new JPanel();
    JButton startButton=new JButton("开始游戏");
    JButton exitButton=new JButton("退出");



}*/

package ui;

import javax.swing.*;
import java.awt.*;

/**
 * 主菜单窗口
 */
public class Menu extends JFrame {
    private String username;

    public Menu(String username) {
        super("连连看 - 主菜单");
        this.username = username;
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(240, 248, 255));

        // ===== 右上角欢迎文字 =====
        JLabel welcomeLabel = new JLabel("欢迎，" + username);
        welcomeLabel.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        welcomeLabel.setForeground(new Color(25, 25, 112));
        welcomeLabel.setSize(250, 30);
        welcomeLabel.setLocation(330, 15);
        welcomeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(welcomeLabel);

        // ===== 顶部图片区域（留空，可放置 logo） =====
        JLabel logoLabel = new JLabel();
        logoLabel.setSize(200, 80);
        logoLabel.setLocation(200, 20);
        // 若要显示图片，取消以下注释并确保路径正确
        // ImageIcon logoIcon = new ImageIcon("resource/menus/logo.png");
        // logoLabel.setIcon(logoIcon);
        add(logoLabel);

        // ===== 功能按钮 =====
        JButton startButton = createButton("开始游戏", 225, 160);
        JButton levelButton = createButton("关卡选择", 225, 230);
        JButton exitButton  = createButton("退出游戏", 225, 300);

        add(startButton);
        add(levelButton);
        add(exitButton);

        // ----- 开始游戏：进入主界面 -----
        startButton.addActionListener(e -> {
            dispose();                                 // 关闭菜单窗口
            // 注意：GameFrame 构造器中已调用 setVisible(true)，无需重复
            new GameFrame("夏日大挑战", 1000, 1000);
        });

        // ----- 关卡选择（暂时无操作） -----
        levelButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "关卡选择功能暂未开放", "提示", JOptionPane.INFORMATION_MESSAGE);
        });

        // ----- 退出游戏 -----
        exitButton.addActionListener(e -> System.exit(0));
    }

    /**
     * 快速创建统一样式的按钮
     */
    private JButton createButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setFont(new Font("微软雅黑", Font.BOLD, 20));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setCursor(Cursor.getDefaultCursor());
        button.setSize(150, 50);
        button.setLocation(x, y);
        return button;
    }
}
