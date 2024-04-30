package com.oop.major_assignment_twitter.Controller;

import com.oop.major_assignment_twitter.Service.CommentService;
import com.oop.major_assignment_twitter.entity.Comment;
import com.oop.major_assignment_twitter.entity.ErrorObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    static class PostCommentParameter{
        private String commentBody;
        private int postID;
        private int userID;

        public String getCommentBody() {
            return commentBody;
        }

        public int getPostID() {
            return postID;
        }

        public int getUserID() {
            return userID;
        }
    }


    static class PatchCommentParameter{
        private String commentBody;
        private int commentID;

        public String getCommentBody() {
            return commentBody;
        }

        public int getCommentID() {
            return commentID;
        }
    }
    @Autowired
    private CommentService commentService;

    @PostMapping("/comment")
    public ResponseEntity<?> createComment(@RequestBody PostCommentParameter pcc) {
        String commentBody = pcc.getCommentBody();
        int postID = pcc.getPostID();
        int userID = pcc.getUserID();
        String response = commentService.createComment(commentBody, postID, userID);
        if(response.equals("User does not exist")){
            ErrorObject errorObject = new ErrorObject(response);
            return ResponseEntity.ok(errorObject);
        }
        else if(response.equals("Post does not exist")){
            ErrorObject errorObject = new ErrorObject(response);
            return ResponseEntity.ok(errorObject);
        }
        else {
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/comment")
    public ResponseEntity<?> getComment(@RequestParam int commentID) {//CCCCCCC
        Comment comment = commentService.getComment(commentID);
        if (comment != null) {
            CommentResponse commentResponse = new CommentResponse(
                    comment.getCommentID(),
                    comment.getCommentBody(),
                    new CommentCreator(comment.getUser().getUserID(), comment.getUser().getName())
            );
            return ResponseEntity.ok(commentResponse);
        } else {
            ErrorObject errorObject = new ErrorObject("Comment does not exist");
            return ResponseEntity.ok(errorObject);
        }
    }

    @PatchMapping("/comment")
    public ResponseEntity<?> editComment(@RequestBody PatchCommentParameter pcp) {
        String commentBody = pcp.getCommentBody();
        int commentID = pcp.getCommentID();
        String response = commentService.editComment(commentBody, commentID);
        if(response.equals("Comment does not exist")){
            ErrorObject errorObject = new ErrorObject(response);
            return ResponseEntity.ok(errorObject);
        }
        else {
            return ResponseEntity.ok(response);
        }
    }

    @DeleteMapping("/comment")
    public ResponseEntity<?> deleteComment(@RequestParam int commentID) {
        String response = commentService.deleteComment(commentID);
        if(response.equals("Comment does not exist")){
            ErrorObject errorObject = new ErrorObject(response);
            return ResponseEntity.ok(errorObject);
        }
        else {
            return ResponseEntity.ok(response);
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

    public class CommentResponse {
        private int commentID;
        private String commentBody;
        private CommentCreator commentCreator;

        public CommentResponse(int commentID, String commentBody, CommentCreator commentCreator) {
            this.commentID = commentID;
            this.commentBody = commentBody;
            this.commentCreator = commentCreator;
        }

        // Getters
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

}

