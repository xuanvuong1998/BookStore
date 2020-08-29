/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import com.opensymphony.xwork2.ActionContext;
import entity.Book;
import entity.OrderDetails;
import java.util.Map;
import service.BookService;
import service.CartService;

public class AddToCartAction {
    
    private final String SUCCESS = "success";
    
    private int bookId;
    private String message;
    
    public AddToCartAction() {
    }
    
    public String execute() throws Exception {
        Map session = ActionContext.getContext().getSession();
        CartService cartService = new CartService(session);
        
        BookService bookService = new BookService();
        Book book = bookService.getBook(bookId);
        
        OrderDetails item = new OrderDetails();
        item.setBookId(book);
        item.setPrice(book.getPrice());
        item.setQuantity(1);
        boolean result = cartService.addToCart(item);
        
        if (!result) {
            message = "Error: Cannot add book " + book.getName() + " to cart!";
        } else {
            message = "Add book " + book.getName() + " success!";
        }
        return SUCCESS;
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
     * @return the bookId
     */
    public int getBookId() {
        return bookId;
    }

    /**
     * @param bookId the bookId to set
     */
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    
}
