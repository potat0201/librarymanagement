package library.gui;

import library.dao.UserDAO;
import library.model.UserAccount;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserManagementGUI extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private UserDAO userDAO = new UserDAO();

    public UserManagementGUI() {
        setTitle("Quản lý tài khoản người dùng");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        JLabel lblTitle = new JLabel("Danh sách Khách hàng (Member)", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitle, BorderLayout.NORTH);

        String[] cols = {"ID", "Username", "Email", "Trạng thái", "Vai trò"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnRefresh = new JButton("Làm mới danh sách");
        btnRefresh.addActionListener(e -> loadData());
        JPanel bottom = new JPanel(); bottom.add(btnRefresh);
        add(bottom, BorderLayout.SOUTH);
    }

    private void loadData() {
        model.setRowCount(0);
        List<UserAccount> users = userDAO.getAllMembers();
        for (UserAccount u : users) model.addRow(new Object[]{u.getId(), u.getUsername(), u.getEmail(), u.getStatus(), u.getRoleName()});
    }
}