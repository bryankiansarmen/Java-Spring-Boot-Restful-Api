package com.example.project.dto.request.blog;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

public class CategoryUpdateDto {
    @NotBlank(message = "Category name is required.")
    @Size(min = 3, message = "Category name must be greater than 3 characters.")
    private String name;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    private boolean active;

    public CategoryUpdateDto(String name, boolean active) {
        this.name = name;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
