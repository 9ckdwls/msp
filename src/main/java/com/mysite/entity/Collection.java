package com.mysite.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.EmbeddedId;

@Entity
@Getter
@Setter
public class Collection {

    @EmbeddedId
    private CollectionId collectionId;

    private int weight;
}
