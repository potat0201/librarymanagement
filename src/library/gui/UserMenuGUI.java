package library.gui;

import library.model.UserAccount;

import javax.swing.*;
import java.awt.*;

public class UserMenuGUI extends JFrame {

    private UserAccount currentUser;

    private JButton btnViewBooks;
    private JButton btnBorrow;
    private JButton btnReturn;
    private JButton btnHistory;
    private JButton btnLogout;

    public UserMenuGUI(UserAccount user) {
        this.currentUser = user;
        initComponents();
    }

    private void initComponents() {
        setTitle("Library Management - User Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblWelcome = new JLabel(
                "Xin chào: " + currentUser.getUsername(),
                SwingConstants.CENTER
        );
        lblWelcome.setFont(lblWelcome.getFont().deriveFont(Font.BOLD, 16f));
        panel.add(lblWelcome, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(4, 1, 10, 10));

        btnViewBooks = new JButton("Xem danh sách sách");
        btnBorrow = new JButton("Mượn sách");
        btnReturn = new JButton("Trả sách");
        btnHistory = new JButton("Lịch sử mượn");

        center.add(btnViewBooks);
        center.add(btnBorrow);
        center.add(btnReturn);
        center.add(btnHistory);

        panel.add(center, BorderLayout.CENTER);

        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnLogout = new JButton("Đăng xuất");
        south.add(btnLogout);

        panel.add(south, BorderLayout.SOUTH);

        add(panel);

        // Sự kiện tạm thời – để sau nối với LoanService + DAO
        btnViewBooks.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "TODO: Mở màn hình danh sách sách từ DB")
        );

        btnBorrow.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "TODO: Mở màn hình mượn sách (LoanService.borrowBook)")
        );

        btnReturn.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "TODO: Mở màn hình trả sách (LoanService.returnBook)")
        );

        btnHistory.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "TODO: Mở màn hình lịch sử mượn")
        );

        btnLogout.addActionListener(e -> {
            this.dispose();
            new LoginGUI().setVisible(true);
        });
    }
}
