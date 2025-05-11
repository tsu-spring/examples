package com.example.manga;

import com.example.review.ReviewDto;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MangaDto {

    private Long id;
    private String imageUrl;
    private String title;
    private String excerpt;
    private String description;
    private String author;
    private Integer publicationYear;
    private Integer volumes;
    private Integer chapters;
    private LocalDateTime postedAt;
    private LocalDateTime updatedAt;
    private List<ReviewDto> reviews;

    public static MangaDto fromManga(Manga manga) {
        var mangaDto = new MangaDto();
        BeanUtils.copyProperties(manga, mangaDto);
        mangaDto.setExcerpt(StringUtils.abbreviate(mangaDto.description, 175));
        mangaDto.setReviews(manga.getReviews().stream().map(ReviewDto::fromReview).collect(Collectors.toList()));
        return mangaDto;
    }
}
