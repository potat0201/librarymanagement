package library.gui;

import library.dao.BookDAO;
import library.dao.BookCopyDAO;
import library.model.Book;
import library.model.BookCopy;
import library.model.UserAccount;
import library.service.LoanService;

import javax.swing.*;
import java.awt.*;

public class BorrowGUI extends JFrame {

    private JTextField txtTitle, txtCopyId;
    private JLabel lblStatus, lblBookInfo;

    private BookDAO bookDAO = new BookDAO();
    private BookCopyDAO copyDAO = new BookCopyDAO();
    private LoanService loanService = new LoanService();

    private UserAccount currentUser;

    public BorrowGUI(UserAccount user) {
        this.currentUser = user;

        setTitle("Mượn sách");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();
    }

    private void initComponents() {
        setLayout(new GridLayout(6, 2, 5, 5));

        add(new JLabel("Tên sách:"));
        txtTitle = new JTextField();
        add(txtTitle);

        add(new JLabel("Thông tin sách:"));
        lblBookInfo = new JLabel("...");
        add(lblBookInfo);

        add(new JLabel("Copy khả dụng:"));
        txtCopyId = new JTextField();
        txtCopyId.setEditable(false);
        add(txtCopyId);

        add(new JLabel("Trạng thái:"));
        lblStatus = new JLabel("...");
        add(lblStatus);

        JButton btnLoad = new JButton("Tải thông tin");
        JButton btnBorrow = new JButton("Mượn sách");

        add(btnLoad);
        add(btnBorrow);

        btnLoad.addActionListener(e -> loadBookInfo());
        btnBorrow.addActionListener(e -> borrowBook());
    }

    private void loadBookInfo() {
        try {
            String title = txtTitle.getText().trim();

            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nhập tên sách!");
                return;
            }

            Book book = bookDAO.getBookByTitle(title);

            if (book == null) {
                lblBookInfo.setText("Không tìm thấy sách!");
                lblStatus.setText("...");
                txtCopyId.setText("");
                return;
            }

            lblBookInfo.setText(book.getTitle());

            BookCopy copy = copyDAO.getAvailableCopy(book.getId());

            if (copy == null) {
                lblStatus.setText("Không có copy khả dụng!");
                txtCopyId.setText("");
                return;
            }

            txtCopyId.setText(String.valueOf(copy.getCopyId()));
            lblStatus.setText("Có thể mượn");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void borrowBook() {
        try {
            if (txtCopyId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không có copy khả dụng để mượn!");
                return;
            }

            long copyId = Long.parseLong(txtCopyId.getText());
            boolean ok = loanService.borrowBook(currentUser.getId(), copyId, 1);

            if (ok) {
                JOptionPane.showMessageDialog(this, "Mượn thành công!");
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi mượn!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!");
        }
    }
}
