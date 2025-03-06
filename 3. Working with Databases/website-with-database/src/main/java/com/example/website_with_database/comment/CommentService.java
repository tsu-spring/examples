package com.example.website_with_database.comment;

import com.example.website_with_database.topic.Topic;
import com.example.website_with_database.topic.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository repository;
    private final TopicRepository topicRepository;

    @Transactional
    public Comment saveComment(Long topicId, String author, String text) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new IllegalArgumentException("Topic not found with ID: " + topicId));

        Comment comment = new Comment();
        comment.setText(text);
        comment.setAuthor(author);
        comment.setTopic(topic);

        return repository.save(comment);
    }
}
