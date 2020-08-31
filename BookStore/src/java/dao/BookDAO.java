/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Book;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import utils.DBUtils;

public class BookDAO extends BaseDAO<Book, String>{
    
    private BookDAO() {
    }
    
    private static BookDAO instance;
    private static final Object LOCK = new Object();
    
    public static BookDAO getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new BookDAO();
            }
        }
        return instance;
    }

    public synchronized List<Book> getBooks(String name, float minPrice, float maxPrice) {
        EntityManager em = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            List<Book> books = em.createNamedQuery("Book.findByNameAndRangePriceActive")
                    .setParameter("name", "%" + name + "%")
                    .setParameter("minPrice", minPrice)
                    .setParameter("maxPrice", maxPrice)
                    .getResultList();

            transaction.commit();

            return books;
        } catch (Exception e) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return null;
    }
    
    public synchronized List<Book> getAllBooks(String name, float minPrice, float maxPrice) {
        EntityManager em = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            List<Book> books = em.createNamedQuery("Book.findByNameAndRangePrice")
                    .setParameter("name", "%" + name + "%")
                    .setParameter("minPrice", minPrice)
                    .setParameter("maxPrice", maxPrice)
                    .getResultList();

            transaction.commit();

            return books;
        } catch (Exception e) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return null;
    }

    public synchronized Book getBookById(int id) {
        EntityManager em = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            List<Book> books = em.createNamedQuery("Book.findById")
                    .setParameter("id", id)
                    .getResultList();

            transaction.commit();
            if (books != null && !books.isEmpty()) {
                return books.get(0);
            }
        } catch (Exception e) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return null;
    }

    public synchronized Book updateBook(Book book) {
        EntityManager em = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            em.merge(book);

            transaction.commit();

            return book;
        } catch (Exception e) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return null;
    }

    public synchronized Book createBook(Book book) {
        EntityManager em = DBUtils.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            em.persist(book);

            transaction.commit();

            return book;
        } catch (Exception e) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return null;
    }
}
