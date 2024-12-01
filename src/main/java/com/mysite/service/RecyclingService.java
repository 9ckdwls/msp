package com.mysite.service;

import org.springframework.stereotype.Service;

import com.mysite.entity.Recycling;
import com.mysite.entity.RecyclingId;
import com.mysite.repository.RecyclingRepository;

import java.util.Date;
import java.util.List;

@Service
public class RecyclingService {

    private final RecyclingRepository recyclingRepository;

    public RecyclingService(RecyclingRepository recyclingRepository) {
        this.recyclingRepository = recyclingRepository;
    }

    //일반사용자 분리수거 로그 추가하기
    public void addLog(String userId, int boxId, int weight) {
    	RecyclingId recyclingId = new RecyclingId(userId, new Date(), boxId);
        
    	Recycling recycling = new Recycling();
        
    	recycling.setRecyclingId(recyclingId);
    	recycling.setWeight(weight);
    	recycling.setUserP(weight); //weight를 point로 변환
        
        recyclingRepository.save(recycling);
    }
    
    //내 재활용 로그 가져오기
    public List<Recycling> findCollectionByRecyclingId_UserId(String userId) {
    	return recyclingRepository.findCollectionByRecyclingId_UserId(userId);
    }
}
