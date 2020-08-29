/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import com.opensymphony.xwork2.ActionContext;
import entity.Book;
import entity.Category;
import entity.UserAccount;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import service.BookService;
import service.CategoryService;
import service.UserAccountService;

public class CreateBookAction {
    
    private final String SUCCESS = "success";
    
    private String name;
    private String image;
    private String author;
    private String description;
    private double price;
    private int quantity;
    private int categoryId;
    
    private String message;
    
    public CreateBookAction() {
    }
    
    public String execute() throws Exception {
        Map session = ActionContext.getContext().getSession();
        UserAccountService userService = new UserAccountService(session);
        UserAccount user = userService.getCurrentUser();
        
        CategoryService categoryService = new CategoryService();
        Category category = categoryService.getCategory(categoryId);
        
        Book book = new Book();
        
        book.setName(name);
        book.setImage(image);
        book.setAuthor(author);
        book.setDescription(description);
        book.setPrice(price);
        book.setQuantity(quantity);
        book.setCategoryId(category);
        book.setIsActive(true);
        book.setImportDate(Date.from(Instant.now()));
        
        BookService bookService = new BookService();
        bookService.createBook(book);
        
        message = "Create new product success!";
        return SUCCESS;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the categoryId
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }
    
}
