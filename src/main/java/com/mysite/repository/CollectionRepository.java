package com.mysite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mysite.entity.Collection;
import com.mysite.entity.CollectionId;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, CollectionId>{

}
