package com.mysite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mysite.entity.Collection;
import com.mysite.entity.CollectionId;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, CollectionId>{
	List<Collection> findCollectionByCollectionId_UserId(String userId);
}
