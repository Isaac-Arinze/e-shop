package com.zikan.e_shop.service.category;

import com.zikan.e_shop.exception.AlreadyExistsExcption;
import com.zikan.e_shop.exception.ResourceNotFoundExcepion;
import com.zikan.e_shop.model.Category;
import com.zikan.e_shop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundExcepion("Category with Id not found"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.ofNullable(category).filter(c-> !categoryRepository.existsByName(c.getName()))
                .map(categoryRepository :: save).orElseThrow(()-> new AlreadyExistsExcption(category.getName()+"already exist"));
    }

    @Override
    public Category updateCategory(Category category, Long id) {
        return Optional.ofNullable(getCategoryById(id)).map(oldCategory -> {
            oldCategory.setName(category.getName());
            return categoryRepository.save(oldCategory);
        }).orElseThrow(()-> new ResourceNotFoundExcepion("Category Not Found"));

    }

    @Override
    public void deleteCategoryById(Long id) {
          categoryRepository.findById(id)
                  .ifPresentOrElse(categoryRepository :: delete,
                          () ->{throw new ResourceNotFoundExcepion("Resource Not found with Id provided");});
                  ;

    }
}
