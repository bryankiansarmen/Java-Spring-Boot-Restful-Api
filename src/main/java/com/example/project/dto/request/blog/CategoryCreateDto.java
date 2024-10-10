package com.example.project.dto.request.blog;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryCreateDto {
    @NotBlank(message = "Category name is required.")
    @Size(min = 3, message = "Category name must be greater than 3 characters.")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
