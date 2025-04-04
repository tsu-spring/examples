package com.example.secured_web_app.post;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

import static com.example.secured_web_app.util.TimeFormatter.prettyFormat;

@Data
public class PostDto {

    private Long id;
    private String title;
    private String excerpt;
    private String content;
    private LocalDateTime createTime;
    private String prettyCreateTime;
    private String author;

    public static PostDto fromPost(Post post) {
        var postDto = new PostDto();
        BeanUtils.copyProperties(post, postDto);
        postDto.setAuthor(post.getAuthor().getUsername());
        // Excerpt will have maximum of 30 symbols
        postDto.setExcerpt(StringUtils.abbreviate(postDto.content, 100));
        postDto.setCreateTime(post.getCreatedAt());
        postDto.setPrettyCreateTime(prettyFormat(post.getCreatedAt()));
        return postDto;
    }
}
