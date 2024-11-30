package com.mysite.service;

import org.springframework.stereotype.Service;
import java.util.Date;

import com.mysite.repository.CollectionRepository;
import com.mysite.entity.Collection;
import com.mysite.entity.CollectionId;

@Service
public class CollectionService {

    private final CollectionRepository collectionRepository;

    public CollectionService(CollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    public void addLog(String userId, int boxId, int weight) {
        CollectionId collectionId = new CollectionId(userId, new Date(), boxId);
        
        Collection collection = new Collection();
        
        collection.setCollectionId(collectionId);
        collection.setWeight(weight);
        
        collectionRepository.save(collection);
    }
}
