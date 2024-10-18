package com.example.project.controller.blog;

import com.example.project.dto.request.blog.PostCreateDto;
import com.example.project.dto.response.CustomResponse;
import com.example.project.model.blog.Post;
import com.example.project.service.blog.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1/posts")
@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Post> createPost(PostCreateDto postCreateDto) {
        Post post = postService.createPost(postCreateDto);

        return ResponseEntity.ok(post);
    }

    @GetMapping
    public ResponseEntity<?> getAllPost() {
        List<Post> postList = postService.getAllPost();

        if (postList.isEmpty()) {
            return ResponseEntity.ok(new CustomResponse(404, "No record found."));
        }

        return ResponseEntity.ok(postList);
    }
}
