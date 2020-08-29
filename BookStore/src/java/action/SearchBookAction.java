/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import com.opensymphony.xwork2.ActionContext;
import entity.Book;
import entity.UserAccount;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import service.BookService;
import service.UserAccountService;

public class SearchBookAction {

    private static final int MAX_PRICE = 2000000000;

    private final String SUCCESS = "success";

    private String bookName;
    private int minPrice;
    private int maxPrice;
    private String categoryName;
    private List<Book> books;
    private int booksCount;
    private String message;

    public SearchBookAction() {
    }

    public String execute() throws Exception {
        String url = SUCCESS;

        bookName = bookName == null ? "" : bookName;
        categoryName = categoryName == null ? "" : categoryName;

        Map session = ActionContext.getContext().getSession();
        UserAccountService userService = new UserAccountService(session);
        UserAccount user = userService.getCurrentUser();

        try {
            BookService bookService = new BookService();
            int searchMaxPrice = maxPrice == 0 ? MAX_PRICE : maxPrice;
            if (user != null && user.getIsAdmin()) {
                books = bookService.getAllBooks(bookName, minPrice, searchMaxPrice);
            } else {
                books = bookService.getBooks(bookName, minPrice, searchMaxPrice);
            }

            // remove book with category not match with search category name
            List<Book> booksToRemove = new ArrayList<>();
            for (Book book : books) {
                if (!book.getCategoryId().getName().toLowerCase().contains(categoryName.toLowerCase())) {
                    booksToRemove.add(book);
                }
            }
            books.removeAll(booksToRemove);

            booksCount = books.size();
        } catch (Exception e) {
            setBooks(new ArrayList<>());
            setMessage("Error something happenned! Message: " + e.getMessage());
        } finally {

        }
        return url;

    }

    /**
     * @return the bookName
     */
    public String getBookName() {
        return bookName;
    }

    /**
     * @param bookName the bookName to set
     */
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    /**
     * @return the minPrice
     */
    public int getMinPrice() {
        return minPrice;
    }

    /**
     * @param minPrice the minPrice to set
     */
    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    /**
     * @return the maxPrice
     */
    public int getMaxPrice() {
        return maxPrice;
    }

    /**
     * @param maxPrice the maxPrice to set
     */
    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    /**
     * @return the categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @param categoryName the categoryName to set
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * @return the books
     */
    public List<Book> getBooks() {
        return books;
    }

    /**
     * @param books the books to set
     */
    public void setBooks(List<Book> books) {
        this.books = books;
    }

    /**
     * @return the booksCount
     */
    public int getBooksCount() {
        return booksCount;
    }

    /**
     * @param booksCount the booksCount to set
     */
    public void setBooksCount(int booksCount) {
        this.booksCount = booksCount;
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
}
