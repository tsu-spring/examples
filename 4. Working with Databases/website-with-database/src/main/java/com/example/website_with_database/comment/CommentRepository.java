package com.example.website_with_database.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Spring Data JPA automatically generates implementations for repository interfaces that extend 
// JpaRepository (or other similar interfaces like CrudRepository or PagingAndSortingRepository).
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByTopicId(Long topicId);
}
