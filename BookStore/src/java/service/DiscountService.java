/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import dao.DiscountDAO;
import entity.Discount;
import entity.UserAccount;
import java.util.ArrayList;
import java.util.List;


public class DiscountService {
    private DiscountDAO discountDAO;
    
    public DiscountService() {
        discountDAO = new DiscountDAO();
    }
    
    public Discount getDiscount(int id) {
        return discountDAO.getDiscount(id);
    }
    
    public List<Discount> getAllDiscounts(UserAccount user) {
        if (user == null) {
            return new ArrayList<>();
        }
        List<Discount> discounts = new ArrayList<>();
        discounts.add(new Discount(-1, "-- Select --", 0, null, false));
        discounts.addAll(discountDAO.getAllDiscounts(user));
        return discounts;
    }
    
    public Discount updateDiscount(Discount discount) {
        return discountDAO.updateDiscount(discount);
    }
}
