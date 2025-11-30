package library.gui;

import library.model.LoanDetail;
import library.model.UserAccount;
import library.service.LoanService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BorrowHistoryGUI extends JFrame {
    private UserAccount currentUser;
    private JTable table;
    private DefaultTableModel model;
    private LoanService loanService = new LoanService();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public BorrowHistoryGUI(UserAccount user) {
        this.currentUser = user;
        setTitle("Lịch sử mượn sách chi tiết");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        String[] cols = {"Loan ID", "Book ID", "Copy ID", "Ngày giờ mượn", "Ngày giờ trả", "Trạng thái"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        table.getColumnModel().getColumn(4).setPreferredWidth(150);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void loadData() {
        model.setRowCount(0);
        List<LoanDetail> list = loanService.getLoanHistory(currentUser.getId());
        if (list == null) return;
        for (LoanDetail d : list) {
            String loanTime = d.getLoan().getLoanDate().format(formatter);
            String returnTime = (d.getLoan().getReturnDate() != null) ? d.getLoan().getReturnDate().format(formatter) : "";
            model.addRow(new Object[]{ d.getLoan().getId(), d.getCopy().getBookId(), d.getCopy().getCopyId(), loanTime, returnTime, d.getLoan().getStatus() });
        }
    }
}