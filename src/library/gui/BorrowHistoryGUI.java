package library.gui;

import library.model.*;
import library.service.LoanService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BorrowHistoryGUI extends JFrame {

    private UserAccount currentUser;

    private JTable table;
    private DefaultTableModel model;

    private LoanService loanService = new LoanService();

    public BorrowHistoryGUI(UserAccount user) {
        this.currentUser = user;

        setTitle("Lịch sử mượn sách");
        setSize(700, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        String[] cols = {"Loan ID", "Book", "Copy ID", "Ngày mượn", "Ngày trả", "Trạng thái"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void loadData() {
        model.setRowCount(0);

        List<LoanDetail> list = loanService.getLoanHistory(currentUser.getId());
        if (list == null) return;

        for (LoanDetail d : list) {
            model.addRow(new Object[]{
                    d.getLoan().getId(),
                    d.getCopy().getBookId(),
                    d.getCopy().getCopyId(),
                    d.getLoan().getLoanDate(),
                    d.getLoan().getReturnDate(),
                    d.getLoan().getStatus()
            });
        }
    }
}
