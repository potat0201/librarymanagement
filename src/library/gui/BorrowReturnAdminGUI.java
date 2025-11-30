package library.gui;

import library.dao.LoanDAO;
import library.dao.LoanDetailDAO;
import library.model.LoanDetail;
import library.service.LoanService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BorrowReturnAdminGUI extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private LoanDAO loanDAO = new LoanDAO();
    private LoanService loanService = new LoanService();
    private LoanDetailDAO detailDAO = new LoanDetailDAO();

    public BorrowReturnAdminGUI() {
        setTitle("Quản lý Mượn / Trả (Admin)");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        JLabel lblTitle = new JLabel("Theo dõi Mượn / Trả Sách", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        add(lblTitle, BorderLayout.NORTH);

        String[] cols = {"Loan ID", "Người mượn", "Tên sách", "Copy ID", "Ngày mượn", "Ngày trả", "Trạng thái", "Phí"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton btnReturn = new JButton("Xác nhận Trả sách (Đã nhận lại)");
        JButton btnRefresh = new JButton("Làm mới danh sách");
        btnReturn.setBackground(new Color(255, 100, 100)); btnReturn.setForeground(Color.WHITE);
        btnPanel.add(btnReturn); btnPanel.add(btnRefresh);
        add(btnPanel, BorderLayout.SOUTH);

        btnRefresh.addActionListener(e -> loadData());
        btnReturn.addActionListener(e -> confirmReturnBook());
    }

    private void loadData() {
        model.setRowCount(0);
        List<Object[]> data = loanDAO.getAllLoansInfo();
        for (Object[] row : data) model.addRow(row);
    }

    private void confirmReturnBook() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng!"); return; }
        String status = (String) model.getValueAt(row, 6);
        if ("Returned".equalsIgnoreCase(status)) { JOptionPane.showMessageDialog(this, "Sách đã được trả!"); return; }

        long loanId = (long) model.getValueAt(row, 0);
        if (JOptionPane.showConfirmDialog(this, "Xác nhận trả sách?", "Confirm", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;

        LoanDetail detail = detailDAO.getDetailByLoanId(loanId);
        if (detail != null && loanService.returnBook(loanId, detail.getCopy().getCopyId())) {
            JOptionPane.showMessageDialog(this, "Thành công!");
            loadData();
        } else JOptionPane.showMessageDialog(this, "Lỗi!");
    }
}