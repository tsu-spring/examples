package com.example.review;

import com.example.db.model.UserDto;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

import static com.example.util.TimeFormatter.prettyFormat;

@Data
public class ReviewDto {
    private Long id;
    private UserDto user;
    private String text;
    private Integer stars;
    private LocalDateTime createdAt;
    private String prettyCreatedAt;
    private LocalDateTime updatedAt;
    private String prettyUpdatedAt;

    public static ReviewDto fromReview(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        BeanUtils.copyProperties(review, reviewDto);
        reviewDto.setPrettyCreatedAt(prettyFormat(review.getCreatedAt()));
        reviewDto.setPrettyUpdatedAt(prettyFormat(review.getUpdatedAt()));
        reviewDto.setUser(UserDto.fromUser(review.getUser()));
        return reviewDto;
    }
}
