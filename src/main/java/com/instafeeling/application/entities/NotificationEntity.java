package com.instafeeling.application.entities;

import com.instafeeling.persistence.entities.ContentEntity;
import com.instafeeling.persistence.entities.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "notifications", uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_actor_content_recipient",
                columnNames = {"actor_id", "content_id", "recipient_id"}
        )
})
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(name = "recipient_id", updatable = false)
    private Long recipientId;

    @ManyToOne
    @JoinColumn(name = "recipient_id", insertable = false, updatable = false)
    private UserEntity recipient;

    @Column(name = "actor_id", updatable = false)
    private Long actorId;

    @ManyToOne
    @JoinColumn(name = "actor_id", insertable = false, updatable = false)
    private UserEntity actor;

    @Column(name = "content_id", updatable = false)
    private Long contentId;

    @ManyToOne
    @JoinColumn(name = "content_id", insertable = false, updatable = false)
    private ContentEntity content;

    @Column(updatable = true)
    private boolean read;

    @Column(updatable = false)
    private String type;

    @Column(name = "createdAt", updatable = false)
    private LocalDateTime createdAt;

    public NotificationEntity(Long recipientId, Long contentReference, Long actorId, String type){
        this.recipientId = recipientId;
        this.contentId = contentReference;
        this.actorId = actorId;
        this.type = type;

        this.createdAt = LocalDateTime.now();
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}


