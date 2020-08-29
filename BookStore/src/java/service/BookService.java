/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import dao.BookDAO;
import entity.Book;
import java.util.List;


public class BookService {
    
    private BookDAO bookDao;

    public BookService() {
        this.bookDao = new BookDAO();
    }

    public List<Book> getBooks(String name, float minPrice, float maxPrice) {
        return bookDao.getBooks(name, minPrice, maxPrice);
    }
    
    public List<Book> getAllBooks(String name, float minPrice, float maxPrice) {
        return bookDao.getAllBooks(name, minPrice, maxPrice);
    }
    
    public Book getBook(int id) {
        return bookDao.getBookById(id);
    }
    
    public Book updateBook(Book book) {
        return bookDao.updateBook(book);
    }
    
    public Book createBook(Book book) {
        return bookDao.createBook(book);
    }
}
