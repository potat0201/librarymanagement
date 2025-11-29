package library.gui;

import library.model.UserAccount;
import library.service.UserServiceDB;

import javax.swing.*;
import java.awt.*;

public class RegisterGUI extends JFrame {

    private JTextField txtUsername, txtEmail;
    private JPasswordField txtPassword, txtConfirm;
    private UserServiceDB userService = new UserServiceDB();

    public RegisterGUI() {
        setTitle("Đăng ký tài khoản");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Thông tin đăng ký"));

        panel.add(new JLabel("Username:"));
        txtUsername = new JTextField();
        panel.add(txtUsername);

        panel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panel.add(txtEmail);

        panel.add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        panel.add(txtPassword);

        panel.add(new JLabel("Xác nhận mật khẩu:"));
        txtConfirm = new JPasswordField();
        panel.add(txtConfirm);

        JButton btnRegister = new JButton("Đăng ký");
        JButton btnCancel = new JButton("Hủy");

        panel.add(btnRegister);
        panel.add(btnCancel);

        add(panel);

        // ====== EVENTS ======
        btnRegister.addActionListener(e -> register());
        btnCancel.addActionListener(e -> {
            new LoginGUI().setVisible(true);
            this.dispose();
        });
    }

    private void register() {
        String username = txtUsername.getText().trim();
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());
        String confirm = new String(txtConfirm.getPassword());

        // ==== VALIDATION ====
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        if (!password.equals(confirm)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!");
            return;
        }

        // ==== CHECK TRÙNG USERNAME ====
        if (userService.findByUsername(username) != null) {
            JOptionPane.showMessageDialog(this, "Username đã tồn tại!");
            return;
        }

        // ==== TẠO USER ====
        UserAccount newUser = new UserAccount();
        newUser.setUsername(username);
        newUser.setEmail(email);

        // nếu bạn dùng hash -> sửa chỗ này
        newUser.setPasswordHash(password);

        // member = role_id = 3
        newUser.setRoleId(3);
        newUser.setStatus("Active");

        boolean ok = userService.register(newUser);

        if (ok) {
            JOptionPane.showMessageDialog(this, "Đăng ký thành công, hãy đăng nhập!");

            new LoginGUI().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi khi đăng ký!");
        }
    }
}
