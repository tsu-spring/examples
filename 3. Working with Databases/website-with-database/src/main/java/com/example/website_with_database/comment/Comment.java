package com.example.website_with_database.comment;

import com.example.website_with_database.topic.Topic;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "COMMENTS")
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "AUTHOR", nullable = false)
    private String author;

    @Lob
    @Column(name = "TEXT", nullable = false)
    private String text;

    @Column(name = "LIKES", nullable = false)
    private long likes;

    @Column(name = "CREATE_TIME", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createTime;

    @ManyToOne(/* fetch = FetchType.LAZY */) // Fetch strategy is EAGER by default!
    @JoinColumn(name = "TOPIC_ID", nullable = false)
    private Topic topic;

    @PrePersist // This automatically sets createTime to the current timestamp when a new entity is persisted.
    protected void onCreate() {
        likes = 0;
        createTime = LocalDateTime.now();
    }
}
