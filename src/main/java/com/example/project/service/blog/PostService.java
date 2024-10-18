package com.example.project.service.blog;

import com.example.project.dto.request.blog.PostCreateDto;
import com.example.project.model.blog.Post;
import com.example.project.repository.blog.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post createPost(PostCreateDto postCreateDto) {
        return postRepository.save(new Post(postCreateDto.getTitle(), postCreateDto.getContent(), postCreateDto.getUserId(), postCreateDto.getCategoryId()));
    }

    public List<Post> getAllPost() {
        return postRepository.findAll();
    }
}
