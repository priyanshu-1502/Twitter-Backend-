package com.oop.major_assignment_twitter.Service;

import com.oop.major_assignment_twitter.entity.Post;
import com.oop.major_assignment_twitter.entity.User;
import com.oop.major_assignment_twitter.repository.PostRepository;
import com.oop.major_assignment_twitter.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public String createPost(String postBody, int userID) {
        Optional<User> userOptional = userRepository.findById(userID);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Post post = new Post();
            post.setPostBody(postBody);
            post.setUser(user);
            post.setDate(new Date());
            postRepository.save(post);
            return "Post created successfully";
        } else {
            return "User does not exist";
        }
    }

    @Override
    public Post getPost(int postID) {
        Optional<Post> postOptional = postRepository.findById(postID);
        return postOptional.orElse(null);
    }

    @Override
    public String editPost(String postBody, int postID) {
        Optional<Post> postOptional = (postRepository.findById(postID));
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.setPostBody(postBody);
            postRepository.save(post);
            return "Post edited successfully";
        } else {
            return "Post does not exist";
        }
    }

    @Override
    @Transactional
    public String deletePost(int postID) {
        Optional<Post> postOptional = postRepository.findById(postID);
        if (postOptional.isPresent()) {
            postRepository.delete(postOptional.get());
            return "Post deleted";
        } else {
            return "Post does not exist";
        }
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }


}
