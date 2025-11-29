package library.gui;

import library.model.UserAccount;
import library.service.UserServiceDB;

import javax.swing.*;
import java.awt.*;

public class LoginGUI extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnExit;

    private UserServiceDB userService = new UserServiceDB();

    public LoginGUI() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Library Management - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null); // giữa màn hình
        setResizable(false);

        // Panel chính
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitle = new JLabel("Đăng nhập hệ thống thư viện", SwingConstants.CENTER);
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 16f));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblTitle, gbc);

        // Username
        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        txtUsername = new JTextField(20);
        panel.add(txtUsername, gbc);

        // Password
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        txtPassword = new JPasswordField(20);
        panel.add(txtPassword, gbc);

        // Buttons
        gbc.gridy++;
        gbc.gridx = 0;
        btnLogin = new JButton("Login");
        panel.add(btnLogin, gbc);

        gbc.gridx = 1;
        btnExit = new JButton("Thoát");
        panel.add(btnExit, gbc);

        add(panel);

        // Sự kiện
        btnLogin.addActionListener(e -> doLogin());
        btnExit.addActionListener(e -> System.exit(0));

        // Enter = login
        getRootPane().setDefaultButton(btnLogin);
    }

    private void doLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập đầy đủ username và password!",
                    "Thiếu thông tin",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            UserAccount user = userService.login(username, password);

            if (user == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Sai username hoặc password!",
                        "Đăng nhập thất bại",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // Lấy tên role từ UserAccount
            String roleName = (user.getRole() != null)
                    ? user.getRole().getRoleName()
                    : "";

            JOptionPane.showMessageDialog(
                    this,
                    "Đăng nhập thành công! Role: " + roleName,
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // Mở menu tương ứng
            this.dispose(); // đóng màn login

            if ("Admin".equalsIgnoreCase(roleName)) {
                AdminMenuGUI admin = new AdminMenuGUI(user);
                admin.setVisible(true);
            } else {
                UserMenuGUI userMenu = new UserMenuGUI(user);
                userMenu.setVisible(true);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi hệ thống: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
