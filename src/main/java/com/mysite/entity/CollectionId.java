package com.mysite.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class CollectionId implements Serializable {

    private String userId;
    private Date time;
    private int boxId;

    public CollectionId() {}

    public CollectionId(String userId, Date time, int boxId) {
        this.userId = userId;
        this.time = time;
        this.boxId = boxId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollectionId that = (CollectionId) o;
        return boxId == that.boxId &&
                userId.equals(that.userId) &&
                time.equals(that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, time, boxId);
    }
}
