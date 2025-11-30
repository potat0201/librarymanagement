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

        // ===== CÁC SỰ KIỆN (Đã dọn dẹp code trùng lặp) =====

        // 1. Mở quản lý sách
        btnManageBooks.addActionListener(e -> {
            new BookCRUDGUI().setVisible(true);
        });

        // 2. Mở quản lý mượn trả
        btnManageLoans.addActionListener(e -> {
            new BorrowReturnAdminGUI().setVisible(true);
        });

        // 3. Mở quản lý user
        btnManageUsers.addActionListener(e -> {
            new UserManagementGUI().setVisible(true);
        });

        // 4. ĐĂNG XUẤT (FIX LỖI KHÔNG TẮT CỬA SỔ)
        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, 
                    "Bạn có chắc chắn muốn đăng xuất?", "Xác nhận", 
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                this.dispose(); // Tắt cửa sổ Admin hiện tại
                new LoginGUI().setVisible(true); // Mở lại màn hình Login
            }
        });
    }
}