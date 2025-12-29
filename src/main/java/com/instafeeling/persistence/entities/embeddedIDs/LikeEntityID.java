package com.instafeeling.persistence.entities.embeddedIDs;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class LikeEntityID {
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "content_id")
    private Long contentId;

    @Override
    public boolean equals(Object o){
        // check by reference
        if (this == o)
            return true;

        // check by instance
        if (!(o instanceof LikeEntityID instance))
            return false;

        return Objects.equals(instance.userId, this.userId) &&
                Objects.equals(instance.contentId, this.contentId);
    }

    @Override
    public int hashCode(){
        return Objects.hash(userId, contentId);
    }
}
