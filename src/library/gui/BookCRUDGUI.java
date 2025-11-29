package library.gui;

import library.dao.BookDAO;
import library.model.Book;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class BookCRUDGUI extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    private JTextField txtId, txtTitle, txtIsbn, txtPublisherId;
    private JTextArea txtDescription;
    private JTextField txtPublishDate;

    private BookDAO bookDAO = new BookDAO();

    public BookCRUDGUI() {
        setTitle("Quản lý sách (Admin)");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // ===== TABLE =====
        String[] cols = {"ID", "Tiêu đề", "ISBN", "Ngày XB", "Publisher"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        table.getSelectionModel().addListSelectionListener(e -> loadSelectedBook());
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== FORM =====
        JPanel form = new JPanel(new GridLayout(6, 2, 5, 5));
        form.setBorder(BorderFactory.createTitledBorder("Thông tin sách"));

        form.add(new JLabel("ID:"));
        txtId = new JTextField();
        txtId.setEnabled(false);
        form.add(txtId);

        form.add(new JLabel("Tiêu đề:"));
        txtTitle = new JTextField();
        form.add(txtTitle);

        form.add(new JLabel("ISBN:"));
        txtIsbn = new JTextField();
        form.add(txtIsbn);

        form.add(new JLabel("Mô tả:"));
        txtDescription = new JTextArea(3, 20);
        form.add(new JScrollPane(txtDescription));

        form.add(new JLabel("Ngày XB (yyyy-mm-dd):"));
        txtPublishDate = new JTextField();
        form.add(txtPublishDate);

        form.add(new JLabel("Publisher ID:"));
        txtPublisherId = new JTextField();
        form.add(txtPublisherId);

        add(form, BorderLayout.EAST);

        // ===== BUTTONS =====
        JPanel btnPanel = new JPanel();

        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");

        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);

        add(btnPanel, BorderLayout.SOUTH);

        // Events
        btnAdd.addActionListener(e -> addBook());
        btnUpdate.addActionListener(e -> updateBook());
        btnDelete.addActionListener(e -> deleteBook());
    }

    private void loadData() {
        model.setRowCount(0);
        List<Book> list = bookDAO.getAllBooks();

        for (Book b : list) {
            model.addRow(new Object[]{
                    b.getId(),
                    b.getTitle(),
                    b.getIsbn(),
                    b.getPublishDate(),
                    b.getPublisherId()
            });
        }
    }

    private void loadSelectedBook() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        txtId.setText(model.getValueAt(row, 0).toString());
        txtTitle.setText(model.getValueAt(row, 1).toString());
        txtIsbn.setText(model.getValueAt(row, 2).toString());
        txtPublishDate.setText(model.getValueAt(row, 3).toString());
        txtPublisherId.setText(model.getValueAt(row, 4).toString());

        // Mô tả phải load từ DB vì bảng không hiển thị
        long id = Long.parseLong(txtId.getText());
        Book b = bookDAO.getBookById(id);
        txtDescription.setText(b.getDescription());
    }

    private void addBook() {
        try {
            Book b = new Book(
                    0,
                    txtTitle.getText(),
                    txtIsbn.getText(),
                    txtDescription.getText(),
                    LocalDate.parse(txtPublishDate.getText()),
                    Long.parseLong(txtPublisherId.getText())
            );

            if (bookDAO.addBook(b)) {
                JOptionPane.showMessageDialog(this, "Thêm sách thành công!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi thêm!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!");
        }
    }

    private void updateBook() {
        try {
            Book b = new Book(
                    Long.parseLong(txtId.getText()),
                    txtTitle.getText(),
                    txtIsbn.getText(),
                    txtDescription.getText(),
                    LocalDate.parse(txtPublishDate.getText()),
                    Long.parseLong(txtPublisherId.getText())
            );

            if (bookDAO.updateBook(b)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!");
        }
    }

    private void deleteBook() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Xóa sách này?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        long id = Long.parseLong(txtId.getText());
        if (bookDAO.deleteBook(id)) {
            JOptionPane.showMessageDialog(this, "Đã xóa!");
            loadData();
        } else {
            JOptionPane.showMessageDialog(this, "Không thể xóa!");
        }
    }
}
