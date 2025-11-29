package library.service;

import library.dao.UserDAO;
import library.model.UserAccount;

public class UserServiceDB {

    private UserDAO userDAO = new UserDAO();

    public UserAccount login(String username, String password) {
        return userDAO.login(username, password);
    }

    public boolean register(UserAccount user) {
        return userDAO.register(user);
    }
}
