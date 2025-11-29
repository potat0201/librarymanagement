package library.gui;

import library.dao.BookDAO;
import library.dao.BookCopyDAO;
import library.model.Book;
import library.model.BookCopy;
import library.model.UserAccount;
import library.service.LoanService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class BorrowGUI extends JFrame {

    private JTextField txtBookId, txtCopyId, txtStaffId;
    private JLabel lblBookTitle, lblCopyStatus;

    private BookDAO bookDAO = new BookDAO();
    private BookCopyDAO copyDAO = new BookCopyDAO();
    private LoanService loanService = new LoanService();

    private UserAccount currentUser;

    public BorrowGUI(UserAccount user) {
        this.currentUser = user;

        setTitle("Mượn sách");
        setSize(420, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();
    }

    private void initComponents() {
        setLayout(new GridLayout(8, 2, 10, 10));

        add(new JLabel("Book ID:"));
        txtBookId = new JTextField();
        add(txtBookId);

        JButton btnLoadCopies = new JButton("Tải Copy khả dụng");
        btnLoadCopies.addActionListener(e -> loadBookInfo());
        add(btnLoadCopies);

        add(new JLabel("Copy ID (chọn):"));
        txtCopyId = new JTextField();
        add(txtCopyId);

        add(new JLabel("Tiêu đề sách:"));
        lblBookTitle = new JLabel("-");
        add(lblBookTitle);

        add(new JLabel("Trạng thái copy:"));
        lblCopyStatus = new JLabel("-");
        add(lblCopyStatus);

        add(new JLabel("Staff ID:"));
        txtStaffId = new JTextField("1"); // mặc định staff id = 1
        add(txtStaffId);

        JButton btnBorrow = new JButton("Mượn sách");
        btnBorrow.addActionListener(e -> borrowBook());
        add(btnBorrow);
    }

    private void loadBookInfo() {
        try {
            long bookId = Long.parseLong(txtBookId.getText());

            Book b = bookDAO.getBookById(bookId);
            if (b == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy sách!");
                return;
            }
            lblBookTitle.setText(b.getTitle());

            // Lấy copy khả dụng
            List<BookCopy> copies = copyDAO.getCopiesByBookId(bookId);
            BookCopy available = copies.stream()
                    .filter(BookCopy::isAvailable)
                    .findFirst()
                    .orElse(null);

            if (available == null) {
                lblCopyStatus.setText("Không có copy khả dụng!");
                txtCopyId.setText("");
            } else {
                txtCopyId.setText(available.getCopyId() + "");
                lblCopyStatus.setText("Copy khả dụng");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Book ID không hợp lệ!");
        }
    }


    private void borrowBook() {
        try {
            long bookId = Long.parseLong(txtBookId.getText());
            long copyId = Long.parseLong(txtCopyId.getText());
            long staffId = Long.parseLong(txtStaffId.getText());

            boolean ok = loanService.borrowBook(
                    currentUser.getId(),
                    copyId,
                    staffId
            );

            if (ok) {
                JOptionPane.showMessageDialog(this, "Mượn sách thành công!");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Mượn sách thất bại!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Dữ liệu nhập không hợp lệ!");
        }
    }
}
