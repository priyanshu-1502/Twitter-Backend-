package com.oop.major_assignment_twitter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Comment {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int commentID;
//    private String commentBody;
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "post_id")
//    @JsonIgnore
//    private Post post;


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int commentID;
        private String commentBody;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;

        @ManyToOne
        @JoinColumn(name = "post_id")
        @JsonIgnore
        private Post post;

    public int getCommentID() {
        return commentID;
    }

    public void setCommentId(int commentID) {
        this.commentID = commentID;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
    // Getters and setters
}
