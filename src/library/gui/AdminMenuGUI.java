package library.gui;

import library.model.UserAccount;

import javax.swing.*;
import java.awt.*;

public class AdminMenuGUI extends JFrame {

    private UserAccount currentUser;

    private JButton btnManageBooks;
    private JButton btnManageLoans;
    private JButton btnManageUsers;
    private JButton btnLogout;

    public AdminMenuGUI(UserAccount user) {
        this.currentUser = user;
        initComponents();
    }

    private void initComponents() {
        setTitle("Library Management - Admin Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblWelcome = new JLabel(
                "Xin chào Admin: " + currentUser.getUsername(),
                SwingConstants.CENTER
        );
        lblWelcome.setFont(lblWelcome.getFont().deriveFont(Font.BOLD, 16f));
        panel.add(lblWelcome, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(3, 1, 10, 10));

        btnManageBooks = new JButton("Quản lý sách");
        btnManageLoans = new JButton("Quản lý mượn / trả");
        btnManageUsers = new JButton("Quản lý tài khoản người dùng");

        center.add(btnManageBooks);
        center.add(btnManageLoans);
        center.add(btnManageUsers);

        panel.add(center, BorderLayout.CENTER);

        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnLogout = new JButton("Đăng xuất");
        south.add(btnLogout);

        panel.add(south, BorderLayout.SOUTH);

        add(panel);

        // Sự kiện – tạm thời chỉ log + message
        btnManageBooks.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "TODO: Mở màn hình quản lý sách")
        );

        btnManageLoans.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "TODO: Mở màn hình quản lý mượn/trả")
        );

        btnManageUsers.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "TODO: Mở màn hình quản lý user")
        );
        
        btnManageBooks.addActionListener(e -> new BookCRUDGUI().setVisible(true));


        btnLogout.addActionListener(e -> {
            this.dispose();
            new LoginGUI().setVisible(true);
        });
    }
}
