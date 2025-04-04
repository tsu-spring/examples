package com.example.secured_web_app.db.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authorities", uniqueConstraints = {@UniqueConstraint(columnNames = {"username", "authority"})})
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    @ToString.Exclude  // Prevents recursion issues when printing
    private User user;

    @Column(length = 50, nullable = false)
    private String authority;
}
