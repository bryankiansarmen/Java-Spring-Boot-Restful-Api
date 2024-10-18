package com.example.project.controller.blog;

import com.example.project.dto.request.blog.CategoryCreateDto;
import com.example.project.dto.request.blog.CategoryUpdateDto;
import com.example.project.dto.response.blog.CategoryResponse;
import com.example.project.dto.response.CustomResponse;
import com.example.project.model.blog.Category;
import com.example.project.service.blog.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/v1/categories")
@RestController
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody @Valid CategoryCreateDto categoryCreateDto) {
        Category category = categoryService.createCategory(categoryCreateDto);

        return ResponseEntity.ok(category);
    }

    @GetMapping
    public ResponseEntity<?> getAllCategory() {
        List<CategoryResponse> categoryList = categoryService.getAllCategory();

        if (categoryList.isEmpty()) {
            return ResponseEntity.ok(new CustomResponse(404, "No record found."));
        }

        return ResponseEntity.ok(categoryList);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable @Valid Long id) {
        Optional<CategoryResponse> category = categoryService.getCategoryById(id);

        if (category.isEmpty()) {
            return ResponseEntity.ok(new CustomResponse(404, "Record not found."));
        }

        return ResponseEntity.ok(category);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateCategory(@PathVariable @Valid Long id, @RequestBody @Valid CategoryUpdateDto categoryUpdateDto) {
        Category category = categoryService.updateCategory(id, categoryUpdateDto);

        return ResponseEntity.ok(category);
    }
}
