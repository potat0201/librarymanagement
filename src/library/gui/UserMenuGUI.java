package library.gui;

import library.model.UserAccount;
import javax.swing.*;
import java.awt.*;

public class UserMenuGUI extends JFrame {
    private UserAccount currentUser;
    private JButton btnSearchBorrow, btnHistory, btnLogout;

    public UserMenuGUI(UserAccount user) {
        this.currentUser = user;
        initComponents();
    }

    private void initComponents() {
        setTitle("User Menu - Thư viện");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblWelcome = new JLabel("Xin chào: " + currentUser.getUsername(), SwingConstants.CENTER);
        lblWelcome.setFont(lblWelcome.getFont().deriveFont(Font.BOLD, 16f));
        panel.add(lblWelcome, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(2, 1, 10, 10));
        btnSearchBorrow = new JButton("Tìm kiếm & Mượn sách");
        btnHistory = new JButton("Lịch sử mượn");
        center.add(btnSearchBorrow);
        center.add(btnHistory);
        panel.add(center, BorderLayout.CENTER);

        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnLogout = new JButton("Đăng xuất");
        south.add(btnLogout);
        panel.add(south, BorderLayout.SOUTH);

        add(panel); // QUAN TRỌNG

        btnLogout.addActionListener(e -> { this.dispose(); new LoginGUI().setVisible(true); });
        btnSearchBorrow.addActionListener(e -> new BookListGUI(currentUser).setVisible(true));
        btnHistory.addActionListener(e -> new BorrowHistoryGUI(currentUser).setVisible(true));
    }
}