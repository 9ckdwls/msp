package com.mysite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mysite.entity.BoxSensorLog;
import com.mysite.entity.BoxSensorLogId;

@Repository
public interface BoxSensorLogRepository extends JpaRepository<BoxSensorLog, BoxSensorLogId> {
	
}
