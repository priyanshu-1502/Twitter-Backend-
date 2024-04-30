package com.oop.major_assignment_twitter.Service;

import com.oop.major_assignment_twitter.entity.Comment;
import com.oop.major_assignment_twitter.entity.Post;
import com.oop.major_assignment_twitter.entity.User;
import com.oop.major_assignment_twitter.repository.CommentRepository;
import com.oop.major_assignment_twitter.repository.PostRepository;
import com.oop.major_assignment_twitter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public String createComment(String commentBody, int postID, int userID) {
        Optional<User> userOptional = userRepository.findById(userID);
        Optional<Post> postOptional = postRepository.findById(postID);
        if (userOptional.isPresent() && postOptional.isPresent()) {
            User user = userOptional.get();
            Post post = postOptional.get();
            Comment comment = new Comment();
            comment.setCommentBody(commentBody);
            comment.setUser(user);
            comment.setPost(post);
            commentRepository.save(comment);
            return "Comment created successfully";
        } else if (!userOptional.isPresent()) {
            return "User does not exist";
        } else {
            return "Post does not exist";
        }
    }

    @Override
    public Comment getComment(int commentID) {
        Optional<Comment> commentOptional = commentRepository.findById(commentID);
        return commentOptional.orElse(null);
    }

    @Override
    public String editComment(String commentBody, int commentID) {
        Optional<Comment> commentOptional = commentRepository.findById(commentID);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            comment.setCommentBody(commentBody);
            commentRepository.save(comment);
            return "Comment edited successfully";
        } else {
            return "Comment does not exist";
        }
    }

    @Override
    public String deleteComment(int commentID) {
        Optional<Comment> commentOptional = commentRepository.findById(commentID);
        if (commentOptional.isPresent()) {
            commentRepository.delete(commentOptional.get());
            return "Comment deleted";
        } else {
            return "Comment does not exist";
        }
    }

}

