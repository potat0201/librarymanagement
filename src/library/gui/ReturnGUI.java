package library.gui;

import library.dao.LoanDAO;
import library.dao.LoanDetailDAO;
import library.model.Loan;
import library.model.LoanDetail;
import library.service.LoanService;

import javax.swing.*;
import java.awt.*;

public class ReturnGUI extends JFrame {

    private JTextField txtLoanId;
    private JLabel lblBookId, lblCopyId, lblStatus;

    private LoanService loanService = new LoanService();
    private LoanDAO loanDAO = new LoanDAO();
    private LoanDetailDAO detailDAO = new LoanDetailDAO();

    public ReturnGUI() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Return Book");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 10, 10));

        add(new JLabel("Loan ID:"));
        txtLoanId = new JTextField();
        add(txtLoanId);

        add(new JLabel("Book ID:"));
        lblBookId = new JLabel("-");
        add(lblBookId);

        add(new JLabel("Copy ID:"));
        lblCopyId = new JLabel("-");
        add(lblCopyId);

        add(new JLabel("Status:"));
        lblStatus = new JLabel("-");
        add(lblStatus);

        JButton btnLoad = new JButton("Load Loan");
        JButton btnReturn = new JButton("Return Book");

        add(btnLoad);
        add(btnReturn);

        // Load loan info
        btnLoad.addActionListener(e -> loadLoanInfo());

        // Return book
        btnReturn.addActionListener(e -> returnBook());
    }

    private void loadLoanInfo() {
        try {
            long loanId = Long.parseLong(txtLoanId.getText().trim());

            Loan loan = loanDAO.getLoanById(loanId);
            if (loan == null) {
                JOptionPane.showMessageDialog(this, "Loan ID không tồn tại!");
                return;
            }

            // Lấy loan_detail
            LoanDetail detail = detailDAO.getDetailByLoanId(loanId);
            if (detail == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy LoanDetail của loan!");
                return;
            }

            lblBookId.setText(String.valueOf(detail.getCopy().getBookId()));
            lblCopyId.setText(String.valueOf(detail.getCopy().getCopyId()));
            lblStatus.setText(loan.getStatus());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Loan ID không hợp lệ!");
        }
    }

    private void returnBook() {

        try {
            long loanId = Long.parseLong(txtLoanId.getText().trim());
            long copyId = Long.parseLong(lblCopyId.getText().trim());

            boolean ok = loanService.returnBook(loanId, copyId);

            if (ok) {
                JOptionPane.showMessageDialog(this, "Trả sách thành công!");
                lblStatus.setText("Returned");
            } else {
                JOptionPane.showMessageDialog(this, "Trả sách thất bại!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Không thể trả sách!");
        }
    }
}
