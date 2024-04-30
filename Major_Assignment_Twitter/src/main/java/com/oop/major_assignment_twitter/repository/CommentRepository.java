package com.oop.major_assignment_twitter.repository;

import com.oop.major_assignment_twitter.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
  //  Comment findbyId(int id);
}