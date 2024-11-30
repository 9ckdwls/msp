package com.mysite.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.entity.Recycling;
import com.mysite.entity.RecyclingId;


public interface RecyclingRepository extends JpaRepository<Recycling, RecyclingId> {

}
