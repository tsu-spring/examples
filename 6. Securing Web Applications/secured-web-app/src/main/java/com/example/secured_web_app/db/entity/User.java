package com.example.secured_web_app.db.entity;

import com.example.secured_web_app.post.Post;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(length = 50, nullable = false, unique = true)
    private String username;

    @Column(length = 500, nullable = false) // Encoded password will be longer than the real one! keep in mind.
    private String password;

    @Column(nullable = false)
    private boolean enabled;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude // Tell Lombok to not include in the generate toString()!
    private Set<Authority> authorities;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Post> posts; // User's posts
}
