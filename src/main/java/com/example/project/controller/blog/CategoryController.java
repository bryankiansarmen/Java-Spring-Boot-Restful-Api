package com.example.project.controller.blog;

import com.example.project.dto.request.blog.CategoryCreateDto;
import com.example.project.dto.response.CategoryResponse;
import com.example.project.dto.response.CustomResponse;
import com.example.project.model.blog.Category;
import com.example.project.service.blog.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/categories")
@RestController
public class CategoryController {
    private CategoryService categoryService;

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
            return ResponseEntity.ok(new CustomResponse(200, "No record found."));
        }

        return ResponseEntity.ok(categoryList);
    }
}
