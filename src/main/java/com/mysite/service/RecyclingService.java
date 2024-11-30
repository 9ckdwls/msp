package com.mysite.service;

import org.springframework.stereotype.Service;

import com.mysite.entity.Recycling;
import com.mysite.entity.RecyclingId;
import com.mysite.repository.RecyclingRepository;

import java.util.Date;

@Service
public class RecyclingService {

    private final RecyclingRepository recyclingRepository;

    public RecyclingService(RecyclingRepository recyclingRepository) {
        this.recyclingRepository = recyclingRepository;
    }

    public void addLog(String userId, int boxId, int weight) {
    	RecyclingId recyclingId = new RecyclingId(userId, new Date(), boxId);
        
    	Recycling recycling = new Recycling();
        
    	recycling.setRecyclingId(recyclingId);
    	recycling.setWeight(weight);
        
        recyclingRepository.save(recycling);
    }
}
