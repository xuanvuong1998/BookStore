/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import com.opensymphony.xwork2.ActionContext;
import entity.ShoppingOrder;
import entity.UserAccount;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import service.OrderService;
import service.UserAccountService;

/**
 *
 * @author NhanTT
 */
public class OrderHistoryAction {

    private final String SUCCESS = "success";

    private List<ShoppingOrder> orders;
    private String bookName;
    private String fromDateStr;
    private String toDateStr;

    public OrderHistoryAction() {
    }

    public String execute() throws Exception {
        Map session = ActionContext.getContext().getSession();
        UserAccountService userService = new UserAccountService(session);
        UserAccount user = userService.getCurrentUser();

        bookName = bookName == null ? "" : bookName;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date fromDate = fromDateStr == null ? Date.from(Instant.now()) : dateFormat.parse(fromDateStr);
        Date toDate = toDateStr == null ? Date.from(Instant.now()) : dateFormat.parse(toDateStr);
        Calendar cal = Calendar.getInstance();
        cal.setTime(fromDate);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        fromDate = cal.getTime();
        
        cal = Calendar.getInstance();
        cal.setTime(toDate);
        cal.add(Calendar.DATE, 1);
        toDate = cal.getTime();

        fromDateStr = fromDateStr == null ? dateFormat.format(Date.from(Instant.now())) : fromDateStr;
        toDateStr = toDateStr == null ? dateFormat.format(Date.from(Instant.now())) : toDateStr;

        OrderService orderService = new OrderService();
        orders = orderService.getOrders(user, bookName, fromDate, toDate);

        return SUCCESS;
    }

    

    /**
     * @return the bookName
     */
    public String getBookName() {
        return bookName;
    }

    /**
     * @param bookName the bookName to set
     */
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    /**
     * @return the fromDateStr
     */
    public String getFromDateStr() {
        return fromDateStr;
    }

    /**
     * @param fromDateStr the fromDateStr to set
     */
    public void setFromDateStr(String fromDateStr) {
        this.fromDateStr = fromDateStr;
    }

    /**
     * @return the toDateStr
     */
    public String getToDateStr() {
        return toDateStr;
    }

    /**
     * @param toDateStr the toDateStr to set
     */
    public void setToDateStr(String toDateStr) {
        this.toDateStr = toDateStr;
    }

    /**
     * @return the orders
     */
    public List<ShoppingOrder> getOrders() {
        return orders;
    }

    /**
     * @param orders the orders to set
     */
    public void setOrders(List<ShoppingOrder> orders) {
        this.orders = orders;
    }

}
