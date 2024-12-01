package com.mysite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.entity.Recycling;
import com.mysite.entity.RecyclingId;


public interface RecyclingRepository extends JpaRepository<Recycling, RecyclingId> {
	List<Recycling> findCollectionByRecyclingId_UserId(String userId);
}
