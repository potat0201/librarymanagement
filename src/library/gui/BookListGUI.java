package library.gui;

import library.dao.BookDAO;
import library.dao.BookCopyDAO;
import library.model.Book;
import library.model.BookCopy;
import library.model.UserAccount;
import library.service.LoanService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.List;

public class BookListGUI extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JTextField txtSearch;
    private BookDAO bookDAO = new BookDAO();
    private BookCopyDAO copyDAO = new BookCopyDAO();
    private LoanService loanService = new LoanService();
    private UserAccount currentUser;

    public BookListGUI(UserAccount user) {
        this.currentUser = user;
        setTitle("Thư viện - Tìm kiếm & Mượn sách");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        loadData("");
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlSearch.add(new JLabel("Tên sách:"));
        txtSearch = new JTextField(30);
        pnlSearch.add(txtSearch);
        JButton btnSearch = new JButton("Tìm kiếm");
        btnSearch.setBackground(new Color(70, 130, 180));
        btnSearch.setForeground(Color.WHITE);
        pnlSearch.add(btnSearch);
        add(pnlSearch, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"ID", "Tiêu đề", "ISBN", "Ngày XB", "Phí mượn", "SL Khả dụng", "Hành động"}, 0) {
            public boolean isCellEditable(int row, int column) { return column == 6; }
        };
        table = new JTable(model);
        table.setRowHeight(35);
        table.getColumn("Hành động").setCellRenderer(new ButtonRenderer());
        table.getColumn("Hành động").setCellEditor(new ButtonEditor(new JCheckBox()));
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnSearch.addActionListener(e -> loadData(txtSearch.getText().trim()));
        txtSearch.addActionListener(e -> loadData(txtSearch.getText().trim()));
    }

    private void loadData(String keyword) {
        model.setRowCount(0);
        List<Book> books = bookDAO.searchBooks(keyword);
        for (Book b : books) {
            int available = countAvailableCopies(b.getId());
            model.addRow(new Object[]{b.getId(), b.getTitle(), b.getIsbn(), b.getPublishDate(), "5,000 VNĐ", available, "Mượn ngay"});
        }
    }

    private int countAvailableCopies(long bookId) {
        return (int) copyDAO.getCopiesByBookId(bookId).stream().filter(BookCopy::isAvailable).count();
    }

    private void borrowBook(int row) {
        long bookId = (long) model.getValueAt(row, 0);
        String title = (String) model.getValueAt(row, 1);
        int available = (int) model.getValueAt(row, 5);

        if (available <= 0) {
            JOptionPane.showMessageDialog(this, "Sách [" + title + "] đã hết!", "Hết sách", JOptionPane.WARNING_MESSAGE);
            return;
        }
        BookCopy copy = copyDAO.getAvailableCopy(bookId);
        if (copy == null) {
            JOptionPane.showMessageDialog(this, "Lỗi dữ liệu copy.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Mượn sách: " + title + "\nPhí: 5,000 VNĐ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        boolean ok = loanService.borrowBook(currentUser.getId(), copy.getCopyId(), currentUser.getId());
        if (ok) {
            JOptionPane.showMessageDialog(this, "Mượn thành công!");
            loadData(txtSearch.getText().trim());
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi hệ thống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() { setOpaque(true); setBackground(new Color(100, 200, 100)); setForeground(Color.WHITE); }
        public Component getTableCellRendererComponent(JTable t, Object v, boolean isS, boolean hasF, int r, int c) { setText((v == null) ? "" : v.toString()); return this; }
    }

    class ButtonEditor extends DefaultCellEditor {
        protected JButton button; private String label; private boolean isPushed; private int currentRow;
        public ButtonEditor(JCheckBox checkBox) { super(checkBox); button = new JButton(); button.setOpaque(true); button.addActionListener(e -> fireEditingStopped()); }
        public Component getTableCellEditorComponent(JTable t, Object v, boolean isS, int r, int c) { label = (v == null) ? "" : v.toString(); button.setText(label); isPushed = true; currentRow = r; return button; }
        public Object getCellEditorValue() { if (isPushed) borrowBook(currentRow); isPushed = false; return label; }
        public boolean stopCellEditing() { isPushed = false; return super.stopCellEditing(); }
    }
}