/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.ShoppingOrderDAO;
import entity.OrderDetails;
import entity.ShoppingOrder;
import entity.UserAccount;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author TiTi
 */
public class OrderService {

    private ShoppingOrderDAO orderDAO;

    public OrderService() {
        orderDAO = new ShoppingOrderDAO();
    }

    public List<ShoppingOrder> getOrders(UserAccount user, String productName, Date fromDate, Date toDate) {
        List<ShoppingOrder> orders = orderDAO.getOrders(user, fromDate, toDate);

        List<ShoppingOrder> ordersToRemove = new ArrayList<>();

        productName = productName.toLowerCase();
        for (ShoppingOrder order : orders) {
            List<OrderDetails> detailsToRemove = new ArrayList<>();
            List<OrderDetails> details = (List<OrderDetails>) order.getOrderDetailsCollection();
            
            for (OrderDetails detail : details) {
                if (!detail.getBookId().getName().toLowerCase().contains(productName)) {
                    detailsToRemove.add(detail);
                }
            }
            
            details.removeAll(detailsToRemove);
            order.setOrderDetailsCollection(details);
            if (details.isEmpty()) {
                ordersToRemove.add(order);
            }
        }
        
        orders.removeAll(ordersToRemove);

        return orders;
    }
}
