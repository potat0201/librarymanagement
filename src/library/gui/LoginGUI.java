package library.gui;

import library.model.UserAccount;
import library.service.UserServiceDB;

import javax.swing.*;
import java.awt.*;

public class LoginGUI extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private UserServiceDB userService = new UserServiceDB();

    public LoginGUI() {
        setTitle("Library Management - Login");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
    }

    private void initComponents() {
        setLayout(new GridLayout(5, 2, 5, 5));

        add(new JLabel("Username:"));
        txtUsername = new JTextField();
        add(txtUsername);

        add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        add(txtPassword);

        JButton btnLogin = new JButton("Login");
        JButton btnExit = new JButton("Thoát");
        JButton btnRegister = new JButton("Register");

        add(btnLogin);
        add(btnExit);
        add(btnRegister);

        // Sự kiện Login
        btnLogin.addActionListener(e -> doLogin());

        // Sự kiện Exit
        btnExit.addActionListener(e -> System.exit(0));

        // 👉 Sự kiện mở màn hình Đăng ký
        btnRegister.addActionListener(e -> {
            new RegisterGUI().setVisible(true);
            this.dispose();
        });
    }

    private void doLogin() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        UserAccount user = userService.login(username, password);

        if (user == null) {
            JOptionPane.showMessageDialog(this, "Sai thông tin đăng nhập!");
            return;
        }

        JOptionPane.showMessageDialog(this, "Đăng nhập thành công! Role: " + user.getRoleName());

        if ("Admin".equalsIgnoreCase(user.getRoleName())) {
            new AdminMenuGUI(user).setVisible(true);
        } else {
            new UserMenuGUI(user).setVisible(true);
        }

        this.dispose();
    }
}
