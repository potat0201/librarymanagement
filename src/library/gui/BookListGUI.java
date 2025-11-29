package library.gui;

import library.dao.BookDAO;
import library.dao.BookCopyDAO;
import library.model.Book;
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

    private BookDAO bookDAO = new BookDAO();
    private BookCopyDAO copyDAO = new BookCopyDAO();
    private LoanService loanService = new LoanService();

    private UserAccount currentUser;

    public BookListGUI(UserAccount user) {
        this.currentUser = user;

        setTitle("Danh sách sách");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();
        loadData();
    }

    private void initComponents() {

        model = new DefaultTableModel(
                new Object[]{"ID", "Tiêu đề", "ISBN", "Ngày xuất bản", "Copies Available", "Mượn"}, 0
        );

        table = new JTable(model) {
            public boolean isCellEditable(int row, int column) {
                return column == 5;  // chỉ nút Mượn được bấm
            }

            // Render nút vào JTable
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component comp = super.prepareRenderer(renderer, row, col);
                if (col == 5) {
                    JButton btn = new JButton("Mượn");
                    btn.addActionListener(e -> borrowBook(row));  // chạy khi click
                    return btn;
                }
                return comp;
            }
        };

        JScrollPane scroll = new JScrollPane(table);
        add(scroll);
    }


    private void loadData() {
        model.setRowCount(0);

        List<Book> books = bookDAO.getAllBooks();
        for (Book b : books) {
            int available = countAvailableCopies(b.getId());

            model.addRow(new Object[]{
                    b.getId(),
                    b.getTitle(),
                    b.getIsbn(),
                    b.getPublishDate(),
                    available,
                    "Mượn"
            });
        }
    }

    // Đếm số bản khả dụng
    private int countAvailableCopies(long bookId) {
        return (int) copyDAO.getCopiesByBookId(bookId)
                .stream()
                .filter(BookCopy -> BookCopy.isAvailable())
                .count();
    }


    // Xử lý mượn sách khi bấm nút “Mượn”
    private void borrowBook(int row) {
        long bookId = (long) model.getValueAt(row, 0);

        // tìm copy khả dụng
        long copyId = copyDAO.getCopiesByBookId(bookId)
                .stream()
                .filter(BookCopy -> BookCopy.isAvailable())
                .map(BookCopy -> BookCopy.getCopyId())
                .findFirst()
                .orElse(-1L);

        if (copyId == -1) {
            JOptionPane.showMessageDialog(this, "Hiện không còn bản copy khả dụng!");
            return;
        }

        boolean ok = loanService.borrowBook(currentUser.getId(), copyId, 1);

        if (ok) {
            JOptionPane.showMessageDialog(this, "Mượn sách thành công!");
            loadData();
        } else {
            JOptionPane.showMessageDialog(this, "Mượn sách thất bại!");
        }
    }

}
