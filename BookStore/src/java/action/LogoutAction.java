/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package action;

import com.opensymphony.xwork2.ActionContext;
import java.util.Map;
import service.UserAccountService;

public class LogoutAction {

    private final String SUCCESS = "success";

    public LogoutAction() {
    }

    public String execute() throws Exception {
        String url = SUCCESS;
        
        Map session = ActionContext.getContext().getSession();
        UserAccountService userService = new UserAccountService(session);
        userService.logout();
        
        return url;
    }

}