package library.gui;

import library.dao.BookCopyDAO;
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

    // Đã xóa txtPublisherId
    private JTextField txtId, txtTitle, txtIsbn, txtPublishDate, txtQuantity;
    private JTextArea txtDescription;

    private BookDAO bookDAO = new BookDAO();
    private BookCopyDAO copyDAO = new BookCopyDAO();

    public BookCRUDGUI() {
        setTitle("Quản lý sách & Kho (Admin)");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();
        loadData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // ===== TABLE (Bỏ cột Publisher) =====
        String[] cols = {"ID", "Tiêu đề", "ISBN", "Ngày XB", "Số lượng Copy"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        
        table.getSelectionModel().addListSelectionListener(e -> loadSelectedBook());
        
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== FORM (Giảm số dòng vì bỏ Publisher) =====
        JPanel form = new JPanel(new GridLayout(6, 2, 5, 5));
        form.setBorder(BorderFactory.createTitledBorder("Thông tin sách & Kho"));

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

        form.add(new JLabel("Số lượng bản in:"));
        txtQuantity = new JTextField();
        form.add(txtQuantity);

        add(form, BorderLayout.EAST);

        // ===== BUTTONS =====
        JPanel btnPanel = new JPanel();
        JButton btnAdd = new JButton("Thêm mới");
        JButton btnUpdate = new JButton("Cập nhật / Nhập thêm");
        JButton btnDelete = new JButton("Xóa");
        JButton btnClear = new JButton("Làm mới form");

        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        btnPanel.add(btnClear);

        add(btnPanel, BorderLayout.SOUTH);

        // Events
        btnAdd.addActionListener(e -> addBook());
        btnUpdate.addActionListener(e -> updateBook());
        btnDelete.addActionListener(e -> deleteBook());
        btnClear.addActionListener(e -> clearForm());
    }

    private void loadData() {
        model.setRowCount(0);
        List<Book> list = bookDAO.getAllBooks();

        for (Book b : list) {
            int total = copyDAO.countTotalCopies(b.getId());
            
            model.addRow(new Object[]{
                    b.getId(),
                    b.getTitle(),
                    b.getIsbn(),
                    b.getPublishDate(),
                    total
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
        // Cột số lượng giờ là cột index 4
        txtQuantity.setText(model.getValueAt(row, 4).toString());

        long id = Long.parseLong(txtId.getText());
        Book b = bookDAO.getBookById(id);
        if (b != null) txtDescription.setText(b.getDescription());
    }

    private void clearForm() {
        txtId.setText("");
        txtTitle.setText("");
        txtIsbn.setText("");
        txtDescription.setText("");
        txtPublishDate.setText("");
        txtQuantity.setText("");
        table.clearSelection();
    }

    private void addBook() {
        try {
            Book b = new Book(
                    0,
                    txtTitle.getText(),
                    txtIsbn.getText(),
                    txtDescription.getText(),
                    LocalDate.parse(txtPublishDate.getText())
            );

            long newBookId = bookDAO.addBookAndGetId(b);

            if (newBookId != -1) {
                int qty = 0;
                try {
                    qty = Integer.parseInt(txtQuantity.getText());
                } catch (NumberFormatException e) { qty = 0; }

                if (qty > 0) {
                    copyDAO.addCopies(newBookId, qty);
                }

                JOptionPane.showMessageDialog(this, "Thêm sách thành công!");
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Lỗi khi thêm sách!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!");
        }
    }

    private void updateBook() {
        try {
            long bookId = Long.parseLong(txtId.getText());
            
            Book b = new Book(
                    bookId,
                    txtTitle.getText(),
                    txtIsbn.getText(),
                    txtDescription.getText(),
                    LocalDate.parse(txtPublishDate.getText())
            );

            bookDAO.updateBook(b);

            int currentQty = copyDAO.countTotalCopies(bookId);
            int inputQty = Integer.parseInt(txtQuantity.getText());

            if (inputQty > currentQty) {
                int diff = inputQty - currentQty;
                int confirm = JOptionPane.showConfirmDialog(this, 
                    "Bạn có muốn nhập thêm " + diff + " bản copy?", 
                    "Nhập thêm sách", JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    copyDAO.addCopies(bookId, diff);
                }
            } 

            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            loadData();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sách để sửa!");
        }
    }

    private void deleteBook() {
        if (txtId.getText().isEmpty()) return;
        
        int confirm = JOptionPane.showConfirmDialog(this,
                "Xóa sách này sẽ xóa TOÀN BỘ copy và lịch sử mượn liên quan.\nTiếp tục?",
                "Cảnh báo xóa",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        if (bookDAO.deleteBook(Long.parseLong(txtId.getText()))) {
            JOptionPane.showMessageDialog(this, "Đã xóa!");
            loadData();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi xóa!");
        }
    }
}