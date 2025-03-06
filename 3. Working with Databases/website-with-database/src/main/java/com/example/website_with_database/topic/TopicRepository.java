package com.example.website_with_database.topic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TopicRepository extends CrudRepository<Topic, Long> {

    Page<Topic> findAll(Pageable pageable);

    List<Topic> findTop5ByOrderByLikesDesc();

    // Returns number of comments for a given topic
    long countByCommentsTopicId(Long topicId);

    @Query("SELECT t FROM Topic t LEFT JOIN t.comments c GROUP BY t.id ORDER BY COUNT(c) DESC")
    List<Topic> findTop5ByMostComments(Pageable pageable);

    @Modifying
    @Query("UPDATE Topic t SET t.likes = t.likes + 1 WHERE t.id = :id")
    void like(Long id);
}
