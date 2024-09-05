package com.zikan.e_shop.controller;

import com.zikan.e_shop.exception.AlreadyExistsExcption;
import com.zikan.e_shop.exception.ResourceNotFoundExcepion;
import com.zikan.e_shop.model.Category;
import com.zikan.e_shop.response.APIResponse;
import com.zikan.e_shop.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<APIResponse> getAllCategories() {

        try {

            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new APIResponse("Found", categories));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse("Categories not found", INTERNAL_SERVER_ERROR));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<APIResponse> addCategory(Category name) {

        try {

            Category theCategory = categoryService.addCategory(name);
            return ResponseEntity.ok(new APIResponse("success", theCategory));
        } catch (AlreadyExistsExcption e) {
            return ResponseEntity.status(CONFLICT).body(new APIResponse(e.getMessage(), null));
        }
    }


    @GetMapping("/category/{id}/category")
    public ResponseEntity<APIResponse> getCategoryById(@PathVariable Long id) {

        try {
            Category category = categoryService.getCategoryById(id);
            return ResponseEntity.ok(new APIResponse("Found", category));
        } catch (ResourceNotFoundExcepion e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }

    }

    @DeleteMapping("/category/{id}/delete")
    public ResponseEntity<APIResponse> deleteCategory(@PathVariable Long id) {

        try {
            categoryService.deleteCategoryById(id);
            return ResponseEntity.ok(new APIResponse("Found", null));
        } catch (ResourceNotFoundExcepion e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }
    @PutMapping("/category/{id}/update")
    public ResponseEntity<APIResponse> updateCategory( @RequestBody Category category, @PathVariable Long id) {

        try {
            Category updateCategory = categoryService.updateCategory(category, id);
            return ResponseEntity.ok(new APIResponse("Found", updateCategory));
        } catch (ResourceNotFoundExcepion e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }


    }

    @GetMapping("/category/{name}/category")
    public ResponseEntity<APIResponse> getCategoryByName(@PathVariable String name) {

        try {
            Category theCategory = categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new APIResponse("Found", theCategory));
        } catch (ResourceNotFoundExcepion e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }


    }



}
