package library.gui;

import library.model.Role;
import library.model.UserAccount;
import library.service.UserServiceDB;
import library.exception.FileProcessingException;

import javax.swing.*;
import java.awt.*;

public class RegisterGUI extends JFrame {

    private JTextField txtUser, txtEmail;
    private JPasswordField txtPass;

    private UserServiceDB userService = new UserServiceDB();

    public RegisterGUI() {
        setTitle("Đăng ký tài khoản");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Username:"));
        txtUser = new JTextField();
        add(txtUser);

        add(new JLabel("Password:"));
        txtPass = new JPasswordField();
        add(txtPass);

        add(new JLabel("Email:"));
        txtEmail = new JTextField();
        add(txtEmail);

        JButton btnRegister = new JButton("Đăng ký");
        add(btnRegister);

        btnRegister.addActionListener(e -> register());
    }

    private void register() {
    try {
        long id = System.currentTimeMillis();
        String uname = txtUser.getText();
        String pass = new String(txtPass.getPassword());
        String email = txtEmail.getText();

        // Tạo UserAccount mới
        UserAccount user = new UserAccount();
        user.setId(id);
        user.setUsername(uname);
        user.setPasswordHash(pass);
        user.setEmail(email);
        user.setStatus("Active");

        // Gán role = 3 (Độc giả/User)
        Role role = new Role();
        role.setId(3);          // id = 3 trong bảng role
        role.setRoleName("Độc giả");
        user.setRole(role);

        boolean ok = userService.register(user);

        if (ok) {
            JOptionPane.showMessageDialog(this,
                    "Đăng ký thành công!",
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);

            this.dispose();
            new LoginGUI().setVisible(true);

        } else {
            JOptionPane.showMessageDialog(this,
                    "Đăng ký thất bại!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this,
                "Có lỗi xảy ra!",
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
    }
}

}
