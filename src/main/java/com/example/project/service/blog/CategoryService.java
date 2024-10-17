package com.example.project.service.blog;

import com.example.project.dto.request.blog.CategoryCreateDto;
import com.example.project.dto.request.blog.CategoryUpdateDto;
import com.example.project.dto.response.CategoryResponse;
import com.example.project.model.blog.Category;
import com.example.project.repository.blog.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;


    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(CategoryCreateDto categoryCreateDto) {
        return categoryRepository.save(new Category(categoryCreateDto.getName()));
    }

    public List<CategoryResponse> getAllCategory() {
        List<Category> categoryList = categoryRepository.findAll();

        return categoryList.stream()
                .map(category -> new CategoryResponse(category.getId(), category.getName(), category.isActive()))
                .collect(Collectors.toList());
    }

    public Optional<CategoryResponse> getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);

        return category.map(c -> new CategoryResponse(c.getId(), c.getName(), c.isActive()));
    }

    public Category updateCategory(Long id, CategoryUpdateDto categoryUpdateDto) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found."));

        category.setName(categoryUpdateDto.getName());
        category.setUpdatedAt(categoryUpdateDto.getUpdateAt());
        category.setActive(categoryUpdateDto.isActive());

        return categoryRepository.save(category);
    }
}
