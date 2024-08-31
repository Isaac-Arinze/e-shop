package com.zikan.e_shop.service.category;

import com.zikan.e_shop.model.Category;

import java.util.List;

public interface CategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName (String Name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory (Category category, Long id);
    void deleteCategoryById(Long id);

}
