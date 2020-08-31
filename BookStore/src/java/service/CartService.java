/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import dao.BookDAO;
import dao.ShoppingOrderDAO;
import entity.Book;
import entity.OrderDetails;
import entity.ShoppingOrder;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class CartService {
    private Map session;

    public CartService(Map session) {
        this.session = session;
    }
    
    public boolean addToCart(OrderDetails item) {
        this.increaseClickCount(item.getBookId());
        
        if (!this.ensureCartExist()) {
            return false;
        }
        
        ShoppingOrder cart = this.getCart();

        boolean isFound = false;
        for (OrderDetails d : cart.getOrderDetailsCollection()) {
            if (Objects.equals(d.getBookId().getId(), item.getBookId().getId())) {
                d.setQuantity(d.getQuantity()+ item.getQuantity());
                isFound = true;
                break;
            }
        }

        if (!isFound) {
            item.setOrderId(cart);
            cart.getOrderDetailsCollection().add(item);
        }
        return true;
    }
    
    public boolean ensureCartExist() {
        if (session == null) {
            return false;
        }
        ShoppingOrder cart = this.getCart();
        if (cart == null) {
            cart = new ShoppingOrder();
            cart.setOrderDetailsCollection(new ArrayList<>());
            this.setCart(cart);
        }
        return true;
    }
    
    public ShoppingOrder getCart() {
        if (session == null) {
            return null;
        }
        ShoppingOrder cart = (ShoppingOrder) session.get("CART");
        return cart;
    }
    
    public boolean setCart(ShoppingOrder cart) {
        if (session == null) {
            return false;
        }
        session.put("CART", cart);
        return true;
    }
    
    public boolean confirmBook(OrderDetails detail) {
        BookDAO bookDao = BookDAO.getInstance();
        int bookId = detail.getBookId().getId();
        Book book = bookDao.getBookById(bookId);
        
        return detail.getQuantity() <= book.getQuantity();
    }
    
    public boolean confirmBooks(ShoppingOrder order) {
        for (OrderDetails detail : order.getOrderDetailsCollection()) {
            if (!this.confirmBook(detail)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean saveCart(ShoppingOrder cart) {        
        ShoppingOrderDAO orderDAO = new ShoppingOrderDAO();
        ShoppingOrder order = orderDAO.createOrder(cart);
        
        if (order != null) {
            // change products amount
            BookDAO bookDao = BookDAO.getInstance();
            for (OrderDetails detail : cart.getOrderDetailsCollection()) {
                Book dbBook = bookDao.getBookById(detail.getBookId().getId());
                
                int quantity = dbBook.getQuantity() - detail.getQuantity();
                quantity = quantity < 0 ? 0 : quantity;
                dbBook.setQuantity(quantity);
                
                bookDao.updateBook(dbBook);
            }
            
            return true;
        }
        
        return false;
    }
    
    public void increaseClickCount(Book book) {
        if (book.getClickCount() == null) {
            book.setClickCount(0);
        }
        book.setClickCount(book.getClickCount() + 1);
        BookDAO bookDAO = BookDAO.getInstance();
        bookDAO.update(book);
    }
}
