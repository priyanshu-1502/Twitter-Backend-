package com.oop.major_assignment_twitter.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Post {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int postID;
//    private String postBody;
//    private Date date;
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
//    private List<Comment> comments;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postID;
    private String postBody;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public List<Comment> getComments() {
        return comments;
    }


    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostId(int postID) {
        this.postID = postID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // Getters and setters
}
