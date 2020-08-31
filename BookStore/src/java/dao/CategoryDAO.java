/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Category;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import utils.DBUtils;

public class CategoryDAO extends BaseDAO<Category, String>{
    
    private CategoryDAO() {
    }
    
    private static CategoryDAO instance;
    private static final Object LOCK = new Object();
    
    public static CategoryDAO getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new CategoryDAO();
            }
        }
        return instance;
    }

    public synchronized List<Category> getAllCategories() {
        EntityManager em = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            List<Category> categories = em.createNamedQuery("Category.findAll")
                    .getResultList();

            transaction.commit();

            return categories;
        } catch (Exception e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return null;
    }

    public synchronized Category getCategory(int id) {
        EntityManager em = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            List<Category> categories = em.createNamedQuery("Category.findById")
                    .setParameter("id", id)
                    .getResultList();

            transaction.commit();
            if (categories != null && !categories.isEmpty()) {
                return categories.get(0);
            }
        } catch (Exception e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return null;
    }  
    
     public synchronized Category getFirstCategory(String name) {
        EntityManager em = DBUtils.getEntityManager();
        try {
            List<Category> result = em.createNamedQuery("Category.findByName", Category.class)
                    .setParameter("name", name)
                    .getResultList();
            if (result != null && !result.isEmpty()) {
                return result.get(0);
            }
        } catch (Exception e) {
            Logger.getLogger(CategoryDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }
}
