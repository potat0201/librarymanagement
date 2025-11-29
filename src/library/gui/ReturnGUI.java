package library.gui;

import library.dao.BookCopyDAO;
import library.dao.BookDAO;
import library.dao.LoanDAO;
import library.dao.LoanDetailDAO;
import library.model.Book;
import library.model.BookCopy;
import library.model.Loan;
import library.model.LoanDetail;
import library.model.UserAccount;
import library.service.LoanService;

import javax.swing.*;
import java.awt.*;

/**
 * Màn hình trả sách cho Member & Admin
 */
public class ReturnGUI extends JFrame {

    private UserAccount currentUser;

    private JTextField txtLoanId, txtCopyId, txtBookTitle, txtStatus;
    private JButton btnLoad, btnReturn;

    private LoanService loanService = new LoanService();
    private LoanDAO loanDAO = new LoanDAO();
    private LoanDetailDAO detailDAO = new LoanDetailDAO();
    private BookCopyDAO copyDAO = new BookCopyDAO();
    private BookDAO bookDAO = new BookDAO();

    public ReturnGUI(UserAccount user) {
        this.currentUser = user;

        setTitle("Trả sách");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(6, 2, 5, 5));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        form.add(new JLabel("Loan ID:"));
        txtLoanId = new JTextField();
        form.add(txtLoanId);

        form.add(new JLabel("Copy ID:"));
        txtCopyId = new JTextField();
        form.add(txtCopyId);

        form.add(new JLabel("Tên sách:"));
        txtBookTitle = new JTextField();
        txtBookTitle.setEditable(false);
        form.add(txtBookTitle);

        form.add(new JLabel("Trạng thái:"));
        txtStatus = new JTextField();
        txtStatus.setEditable(false);
        form.add(txtStatus);

        btnLoad = new JButton("Tải dữ liệu");
        btnReturn = new JButton("Trả sách");

        JPanel bottom = new JPanel();
        bottom.add(btnLoad);
        bottom.add(btnReturn);

        add(form, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        btnLoad.addActionListener(e -> loadLoanInfo());
        btnReturn.addActionListener(e -> returnBook());
    }

    // ================== TẢI THÔNG TIN LOAN ===================
    private void loadLoanInfo() {
        try {
            long loanId = Long.parseLong(txtLoanId.getText().trim());

            // Lấy loan
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

            txtCopyId.setText(String.valueOf(detail.getCopy().getCopyId()));

            txtStatus.setText(loan.getStatus());

            // Lấy Copy → Book
            BookCopy copy = copyDAO.getCopyById(detail.getCopy().getCopyId());

            if (copy == null) {
                txtBookTitle.setText("Không tìm thấy Copy!");
                return;
            }

            Book book = bookDAO.getBookById(copy.getBookId());
            txtBookTitle.setText(book != null ? book.getTitle() : "Không tìm thấy sách!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Loan ID không hợp lệ!");
        }
    }

    // ================== TRẢ SÁCH ===================
    private void returnBook() {
        try {
            long loanId = Long.parseLong(txtLoanId.getText().trim());
            long copyId = Long.parseLong(txtCopyId.getText().trim());

            boolean ok = loanService.returnBook(loanId, copyId);

            if (ok) {
                JOptionPane.showMessageDialog(this, "Trả sách thành công!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Trả sách thất bại!");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!");
        }
    }
}
