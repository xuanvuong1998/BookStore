/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import dao.PaymentMethodDAO;
import entity.PaymentMethod;
import java.util.List;

public class PaymentMethodService {
    
    private PaymentMethodDAO paymentMethodDAO;

    public PaymentMethodService() {
        paymentMethodDAO = new PaymentMethodDAO();
    }
    
    public PaymentMethod getPaymentMethod(int id) {
        return paymentMethodDAO.getPaymentMethod(id);
    }
    
    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodDAO.getAllPaymentMethods();
    }
}
