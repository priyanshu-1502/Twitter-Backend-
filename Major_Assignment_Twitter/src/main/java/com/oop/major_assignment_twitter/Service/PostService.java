package com.oop.major_assignment_twitter.Service;

import com.oop.major_assignment_twitter.entity.Post;

import java.util.List;

public interface PostService {
    String createPost(String postBody, int userID);
    Post getPost(int postID);
    String editPost(String postBody, int postID);
    String deletePost(int postID);

    List<Post> getAllPosts();
}