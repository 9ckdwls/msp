package com.mysite.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import org.locationtech.jts.geom.Point;

import jakarta.persistence.EmbeddedId;

@Entity
@Getter
@Setter
public class Collection {

    @EmbeddedId
    private CollectionId collectionId;

    private int weight;
}
