package com.oop.major_assignment_twitter.Service;

import com.oop.major_assignment_twitter.entity.Comment;

public interface CommentService {
    String createComment(String commentBody, int postID, int userID);
    Comment getComment(int commentID);
    String editComment(String commentBody, int commentID);
    String deleteComment(int commentID);
}
