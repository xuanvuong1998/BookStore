/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import dao.UserAccountDAO;
import entity.UserAccount;
import java.util.Map;


public class UserAccountService {
private Map session;
    
    public UserAccountService() {
    }

    public UserAccountService(Map session) {
        this.session = session;
    }

    public UserAccount login(String username, String password) {
        UserAccountDAO userAccountDao = new UserAccountDAO();
        UserAccount user = userAccountDao.getUserByUsername(username);

        if (user == null) {
            return null;
        }

        if (user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
    
    public void logout() {
        this.setCurrentUser(null);
    }
    
    public void setCurrentUser(UserAccount user) {
        if (session == null) {
            return;
        }
        session.put("USER", user);
    }
    
    public UserAccount getCurrentUser() {
        if (session == null) {
            return null;
        }
        return (UserAccount) session.get("USER");
    }
}
