/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import com.opensymphony.xwork2.ActionContext;
import entity.UserAccount;
import java.util.Map;
import service.UserAccountService;

public class LoginAction {
    
    private String username;
    private String password;
    private String message;
    private final String SUCCESS = "success";
    private final String FAIL = "fail";

    public LoginAction() {
    }

    public String execute() throws Exception {
        String url = FAIL;

        Map session = ActionContext.getContext().getSession();
        UserAccountService userAccountService = new UserAccountService(session);

        UserAccount user = userAccountService.login(getUsername(), getPassword());
        if (user == null) {
            this.message = "Invalid username or password";
        } else {
            this.message = "";
            userAccountService.setCurrentUser(user);
            url = SUCCESS;
        }
        return url;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
}
