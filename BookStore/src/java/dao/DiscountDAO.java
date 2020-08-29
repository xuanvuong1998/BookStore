/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entity.Discount;
import entity.UserAccount;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import utils.DBUtils;


public class DiscountDAO {
    
    public List<Discount> getAllDiscounts(UserAccount user) {
        EntityManager em = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            List<Discount> discounts = em.createNamedQuery("Discount.findUnuseAllOfUser")
                    .setParameter("username", user)
                    .getResultList();

            transaction.commit();

            return discounts;
        } catch (Exception e) {
            Logger.getLogger(DiscountDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return null;
    }
    
    public Discount getDiscount(int id) {
        EntityManager em = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            List<Discount> discounts = em.createNamedQuery("Discount.findById")
                    .setParameter("id", id)
                    .getResultList();

            transaction.commit();

            if (discounts != null && !discounts.isEmpty()) {
                return discounts.get(0);
            }
        } catch (Exception e) {
            Logger.getLogger(PaymentMethodDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return null;
    }
    
    public Discount updateDiscount(Discount discount) {
        EntityManager em = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            em.merge(discount);

            transaction.commit();

            return discount;
        } catch (Exception e) {
            Logger.getLogger(DiscountDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return null;
    }
    
    public Discount createDiscount(Discount discount) {
        EntityManager em = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            em.persist(discount);

            transaction.commit();

            return discount;
        } catch (Exception e) {
            Logger.getLogger(DiscountDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return null;
    }
}
