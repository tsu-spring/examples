package com.example.website_with_database.topic;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TopicService {

    @Getter
    @Value("${topics.page-size:10}")
    private int pageSize;

    private final TopicRepository repository;

    public Page<TopicDTO> getTopics(int page) { // You may also pass the 'size' from the outside ;)
        // (1) Specify sorting order by field
        Sort sort = Sort.by(Sort.Order.by("createTime").with(Sort.Direction.DESC));

        // (2) Create pageable request object
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        // (3) Pass pageable to findAll() method :)
        return repository.findAll(pageable).map(TopicDTO::fromTopic);
    }

    public List<TopicDTO> getTop5ByLikes() {
        return repository.findTop5ByOrderByLikesDesc().stream()
                .map(TopicDTO::fromTopic)
                .collect(Collectors.toList());
    }

    public List<TopicDTO> getTop5HottestTopics() {
        return repository.findTop5ByMostComments(
                        PageRequest.of(0, 5) // This will give us only 5 entries
                ).stream()
                .map(TopicDTO::fromTopic)
                .collect(Collectors.toList());
    }

    public TopicDTO getTopicById(Long id) {
        return TopicDTO.fromTopic(repository.findById(id).orElseThrow());
    }

    public void deleteTopicById(long id) {
        repository.deleteById(id);
    }

    // Note: if @Transactional is on likeTopic(Long id),
    // then it is not needed on like(Long id) in the repository.
    @Transactional // Required for modifying queries since they change the database state.
    public void likeTopic(long id) {
        repository.like(id);
    }

    @Transactional // Ensures that all operations succeed or all fail!
    public TopicDTO save(String title, String text) {
        Topic topic = new Topic();
        topic.setTitle(title);
        topic.setText(text);
        return TopicDTO.fromTopic(repository.save(topic));
    }
}
