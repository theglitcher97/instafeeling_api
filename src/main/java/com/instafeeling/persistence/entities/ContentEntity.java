package com.instafeeling.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "content")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private UserEntity userEntity;

    @Column
    private String name;

    @Column
    private String type;

    @Column
    private Long size;

    @OneToMany(mappedBy = "contentEntity")
    private Set<ContentTagsEntity> tags = new HashSet<>();

    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    private void onPrePersist(){
        this.createdAt = LocalDateTime.now();
    }
}
