/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.ShoppingOrder;
import entity.UserAccount;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import utils.DBUtils;

public class ShoppingOrderDAO {

    public ShoppingOrder createOrder(ShoppingOrder order) {
        EntityManager em = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            em.persist(order);

            transaction.commit();

            return order;
        } catch (Exception e) {
            Logger.getLogger(ShoppingOrderDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return null;
    }

    public List<ShoppingOrder> getOrders(UserAccount user, Date fromDate, Date toDate) {
        EntityManager em = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            List<ShoppingOrder> orders = em.createNamedQuery("ShoppingOrder.findByUserAndDateRange")
                    .setParameter("fromDate", fromDate)
                    .setParameter("toDate", toDate)
                    .setParameter("user", user)
                    .getResultList();

            transaction.commit();

            return orders;
        } catch (Exception e) {
            Logger.getLogger(ShoppingOrderDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return null;
    }
}
