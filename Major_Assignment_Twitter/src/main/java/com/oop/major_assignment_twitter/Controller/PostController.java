package com.oop.major_assignment_twitter.Controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oop.major_assignment_twitter.Service.PostService;
import com.oop.major_assignment_twitter.entity.Comment;
import com.oop.major_assignment_twitter.entity.ErrorObject;
import com.oop.major_assignment_twitter.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class PostController {

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    static class CreatePostParameter{
        private String postBody;
        private int userID;

        public String getPostBody() {
            return postBody;
        }

        public int getUserID() {
            return userID;
        }
    }

    static class PatchPostParameter{
        private String postBody;
        private int postID;


        public String getPostBody() {
            return postBody;
        }

        public int getPostID() {
            return postID;
        }
    }

    @Autowired
    private PostService postService;

    @GetMapping("/post")
    public ResponseEntity<?> getPost(@RequestParam int postID) {
        Post post = postService.getPost(postID);
        if (post != null) {
            PostResponse postResponse = new PostResponse(
                    post.getPostID(),
                    post.getPostBody(),
                    formatDate(post.getDate()),
                    mapComments(post.getComments())
            );

            return ResponseEntity.ok(postResponse);
        } else {
            ErrorObject errorObject = new ErrorObject("Post does not exist");
            return ResponseEntity.ok(errorObject);
        }
    }

    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestBody CreatePostParameter cpp) {
        String postBody = cpp.getPostBody();
        int userID = cpp.getUserID();
        String response = postService.createPost(postBody, userID);
        if(response.equals("User does not exist")){
            ErrorObject errorObject = new ErrorObject(response);
            return ResponseEntity.ok(errorObject);
        }
        else {
            return ResponseEntity.ok(response);
        }
    }

    @PatchMapping("/post")
    public ResponseEntity<?> editPost(@RequestBody PatchPostParameter ppp) {
        String postBody = ppp.getPostBody();
        int postID = ppp.getPostID();
        String response = postService.editPost(postBody, postID);
        if(response.equals("Post does not exist")){
            ErrorObject errorObject = new ErrorObject(response);
            return ResponseEntity.ok(errorObject);
        }
        else {
            return ResponseEntity.ok(response);
        }
    }

    @DeleteMapping("/post")
    public ResponseEntity<?> deletePost(@RequestParam int postID) {
        String response = postService.deletePost(postID);
        if(response.equals("Post does not exist")){
            ErrorObject errorObject = new ErrorObject(response);
            return ResponseEntity.ok(errorObject);
        }
        else {
            return ResponseEntity.ok(response);
        }
    }


    @GetMapping("/")
    public ResponseEntity<List<PostResponse>> getUserFeed() {
        List<Post> posts = postService.getAllPosts();
        posts.sort(Comparator.comparing(Post::getDate)); // Sort posts by creation date

        List<PostResponse> postResponses = posts.stream()
                .map(post -> new PostResponse(
                        post.getPostID(),  // Keep as Long
                        post.getPostBody(),
                        formatDate(post.getDate()),   // Format date to String
                        mapComments(post.getComments()) // Map comments to CommentDTO
                ))
                .collect(Collectors.toList());
        Collections.reverse(postResponses);
        return ResponseEntity.ok(postResponses);
    }


    private static String formatDate(Date date) {
        try {
            return DATE_FORMATTER.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    private List<CommentDTO> mapComments(List<Comment> comments) {
        return comments.stream()
                .map(comment -> new CommentDTO(
                        comment.getCommentID(),
                        comment.getCommentBody(),
                        new CommentCreator(comment.getUser().getUserID(), comment.getUser().getName())
                ))
                .collect(Collectors.toList());
    }

    private static class CommentDTO {
        private final int commentID;
        private final String commentBody;
        private final CommentCreator commentCreator;

        public CommentDTO(int commentID, String commentBody, CommentCreator commentCreator) {
            this.commentID = commentID;
            this.commentBody = commentBody;
            this.commentCreator = commentCreator;
        }

        public int getCommentID() {
            return commentID;
        }

        public String getCommentBody() {
            return commentBody;
        }

        public CommentCreator getCommentCreator() {
            return commentCreator;
        }
    }

    private static class CommentCreator {
        private final int userID;
        private final String name;

        public CommentCreator(int userID, String name) {
            this.userID = userID;
            this.name = name;
        }

        public int getUserID() {
            return userID;
        }

        public String getName() {
            return name;
        }
    }

    private static class PostResponse {
        private final int postID; // Keep as Long
        private final String postBody;
        private final String date;
        private final List<CommentDTO> comments;

        public PostResponse(int postID, String postBody, String date, List<CommentDTO> comments) {
            this.postID = postID;
            this.postBody = postBody;
            this.date = date;
            this.comments = comments;
        }

        public int getPostID() {
            return postID;
        }

        public String getPostBody() {
            return postBody;
        }

        public String getDate() {
            return date;
        }

        public List<CommentDTO> getComments() {
            return comments;
        }
    }
}
