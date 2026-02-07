package com.instafeeling.persistence.entities;

import com.instafeeling.persistence.entities.embeddedIDs.ContentTagID;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "content_tags")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContentTagsEntity implements Serializable {
    @EmbeddedId
    private ContentTagID id;

    @ManyToOne
    @JoinColumn(name = "content_id", insertable = false, updatable = false)
    @MapsId("contentId")
    private ContentEntity contentEntity;

    @ManyToOne
    @JoinColumn(name = "tag_id", insertable = false, updatable = false)
    @MapsId("tagId")
    private TagEntity tagEntity;

    public ContentTagsEntity(ContentEntity contentEntity, TagEntity tagEntity){
        this.id = new ContentTagID(tagEntity.getId(), contentEntity.getId());
        this.contentEntity = contentEntity;
        this.tagEntity = tagEntity;
    }
}
