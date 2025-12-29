package com.instafeeling.persistence.entities;

import com.instafeeling.persistence.entities.embeddedIDs.LikeEntityID;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Table(name = "likes")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LikeEntity implements Serializable {
    @EmbeddedId
    private LikeEntityID id;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @MapsId("userId")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "content_id", insertable = false, updatable = false)
    @MapsId("contentId")
    private ContentEntity contentEntity;

    public LikeEntity(UserEntity userEntity, ContentEntity contentEntity){
        this.userEntity = userEntity;
        this.contentEntity = contentEntity;
        this.id = new LikeEntityID(userEntity.getId(), contentEntity.getId());
    }
}
