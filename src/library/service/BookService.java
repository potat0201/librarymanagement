package library.service;

import library.dao.BookDAO;
import library.model.Book;

import java.util.List;

public class BookService {

    private BookDAO bookDAO = new BookDAO();

    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }
}
