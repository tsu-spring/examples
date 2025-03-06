package com.example.website_with_database.topic;

import com.example.website_with_database.comment.Comment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TOPICS")
@Getter
@Setter
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Lob // Will transform data type to "TEXT"
    @Column(name = "TEXT", nullable = false)
    private String text;

    @Column(name = "LIKES", nullable = false)
    private long likes;

    @Column(name = "CREATE_TIME", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime createTime;

    @OneToMany(mappedBy = "topic",
            cascade = CascadeType.ALL, // Cascade all operations
            orphanRemoval = true) // Remove orphans (apply remove operation to entries without relationship)
    private List<Comment> comments = new ArrayList<>();

    @PrePersist // This automatically sets createTime to the current timestamp when a new entity is persisted.
    protected void onCreate() {
        likes = 0;
        createTime = LocalDateTime.now();
    }
}
