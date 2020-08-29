/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import entity.Discount;
import entity.UserAccount;
import java.time.Instant;
import java.util.Date;
import service.DiscountService;
import service.UserAccountService;

public class CreateDiscountAction {
    
    private final String SUCCESS = "success";
    
    private String discountCode;
    private int discountPercent;
    private String username;
    
    private String message;
    
    public CreateDiscountAction() {
    }
    
    public String execute() throws Exception {
        UserAccountService userService = new UserAccountService();
        UserAccount user = userService.getUser(username);
        
        Discount discount = new Discount();
        
        discount.setDiscountCode(discountCode);
        discount.setDiscountPercent(discountPercent);
        discount.setUsername(user);
        discount.setIsUsed(false);
        discount.setCreatedDate(Date.from(Instant.now()));
        
        DiscountService discountService = new DiscountService();
        discountService.createDiscount(discount);
        
        message = "Create new discount success!";
        return SUCCESS;
    }

    /**
     * @return the discountCode
     */
    public String getDiscountCode() {
        return discountCode;
    }

    /**
     * @param discountCode the discountCode to set
     */
    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    /**
     * @return the discountPercent
     */
    public int getDiscountPercent() {
        return discountPercent;
    }

    /**
     * @param discountPercent the discountPercent to set
     */
    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
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
