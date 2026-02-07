package com.instafeeling.persistence.entities.embeddedIDs;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ContentTagID {
    @Column(name = "tag_id")
    private Long tagId;
    @Column(name = "content_id")
    private Long contentId;

    @Override
    public boolean equals(Object o){
        // check by reference
        if (this == o)
            return true;

        // check by instance
        if (!(o instanceof ContentTagID instance))
            return false;

        return Objects.equals(instance.tagId, this.tagId) &&
                Objects.equals(instance.contentId, this.contentId);
    }

    @Override
    public int hashCode(){
        return Objects.hash(tagId, contentId);
    }
}
