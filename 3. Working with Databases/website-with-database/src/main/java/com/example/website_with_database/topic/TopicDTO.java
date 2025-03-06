package com.example.website_with_database.topic;

import com.example.website_with_database.comment.CommentDTO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.website_with_database.util.TimeFormatter.prettyFormat;

@Data
public class TopicDTO {
    private Long id;
    private String title;
    private String text;
    private long likes;
    private LocalDateTime createTime;
    private String prettyCreateTime;
    private List<CommentDTO> comments;

    public static TopicDTO fromTopic(Topic topic) {
        var topicDTO = new TopicDTO();
        BeanUtils.copyProperties(topic, topicDTO);
        topicDTO.setComments(topic.getComments().stream().map(CommentDTO::fromComment).collect(Collectors.toList()));
        topicDTO.setPrettyCreateTime(prettyFormat(topic.getCreateTime()));
        return topicDTO;
    }
}
