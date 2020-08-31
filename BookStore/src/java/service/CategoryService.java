/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.CategoryDAO;
import entity.Category;
import java.util.List;

/**
 *
 * @author TiTi
 */
public class CategoryService {

    private CategoryDAO categoryDAO;

    public CategoryService() {
        categoryDAO = CategoryDAO.getInstance();
    }

    public List<Category> getAllCategories() {
        return categoryDAO.getAllCategories();
    }
    
    public Category getCategory(int id) {
        return categoryDAO.getCategory(id);
    }
}
