/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import entity.Book;
import entity.Category;
import java.text.SimpleDateFormat;
import java.util.Date;
import service.BookService;
import service.CategoryService;

public class UpdateBookAction {

    private final String SUCCESS = "success";
    private final String FAIL = "fail";

    private String btAction;
    private int bookId;
    private String name;
    private String description;
    private String imageUrl;
    private double price;
    private String author;
    private String importDate;
    private int categoryId;
    private int quantity;
    private boolean isActive;

    private String message;

    public UpdateBookAction() {
    }

    public String execute() throws Exception {
        BookService bookService = new BookService();
        Book book = bookService.getBook(bookId);
        
        CategoryService categoryService = new CategoryService();
        Category category = categoryService.getCategory(categoryId);
        
        Date importDateObj = new SimpleDateFormat("yyyy-MM-dd").parse(importDate);

        String url = SUCCESS;
        try {
            if ("Delete".equals(btAction)) {
                book.setIsActive(false);
                bookService.updateBook(book);
            } else if ("Update".equals(btAction)) {
                book.setName(name);
                book.setDescription(description);
                book.setImage(imageUrl);
                book.setPrice(price);
                book.setQuantity(quantity);
                book.setAuthor(author);
                book.setImportDate(importDateObj);
                book.setCategoryId(category);
                book.setIsActive(isActive);
                bookService.updateBook(book);
            }
        } catch (Exception ex) {
            url = FAIL;
            return url;
        } 
        
        return url;
    }

    /**
     * @return the btAction
     */
    public String getBtAction() {
        return btAction;
    }

    /**
     * @param btAction the btAction to set
     */
    public void setBtAction(String btAction) {
        this.btAction = btAction;
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
     * @return the imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * @param imageUrl the imageUrl to set
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
     * @return the importDate
     */
    public String getImportDate() {
        return importDate;
    }

    /**
     * @param importDate the importDate to set
     */
    public void setImportDate(String importDate) {
        this.importDate = importDate;
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
     * @return the isActive
     */
    public boolean isIsActive() {
        return isActive;
    }

    /**
     * @param isActive the isActive to set
     */
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
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
