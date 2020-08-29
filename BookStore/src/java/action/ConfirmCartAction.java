/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import com.opensymphony.xwork2.ActionContext;
import entity.PaymentMethod;
import entity.ShoppingOrder;
import entity.UserAccount;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import service.CartService;
import service.PaymentMethodService;
import service.UserAccountService;

public class ConfirmCartAction {

    private final String SUCCESS = "success";
    private final String FAIL = "fail";

    private int paymentMethodId;

    private String message;

    public ConfirmCartAction() {
    }

    public String execute() throws Exception {
        Map session = ActionContext.getContext().getSession();
        CartService cartService = new CartService(session);
        UserAccountService userService = new UserAccountService(session);
        UserAccount user = userService.getCurrentUser();

        String url = SUCCESS;
        if (user == null || user.getIsAdmin()) {
            url = FAIL;
        } else {
            ShoppingOrder cart = cartService.getCart();
            if (!cartService.confirmBooks(cart)) {
                message = "WARNING: Your book quantity exceed our stock quantity!";
            } else {
                PaymentMethodService paymentMethodService = new PaymentMethodService();
                PaymentMethod paymentMethod = paymentMethodService.getPaymentMethod(paymentMethodId);
                cart.setPaymentMethodId(paymentMethod);
                cart.setUsername(user);
                cart.setCreatedDate(Date.from(Instant.now()));

                if (cartService.saveCart(cart)) {
                    cartService.setCart(null);
                    message = "Your cart has been confirmed successfully!";
                } else {
                    message = "ERROR: Some thing happenned. We are working on it to solve the issue.";
                }
            }
        }

        return url;
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

    /**
     * @return the paymentMethodId
     */
    public int getPaymentMethodId() {
        return paymentMethodId;
    }

    /**
     * @param paymentMethodId the paymentMethodId to set
     */
    public void setPaymentMethodId(int paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

}
