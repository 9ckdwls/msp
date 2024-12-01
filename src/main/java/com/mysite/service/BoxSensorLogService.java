package com.mysite.service;

import java.util.Date;
import org.springframework.stereotype.Service;
import com.mysite.dto.IoTResponse;
import com.mysite.entity.BoxSensorLog;
import com.mysite.entity.BoxSensorLogId;
import com.mysite.repository.BoxSensorLogRepository;

@Service
public class BoxSensorLogService {

	private final BoxSensorLogRepository boxSensorLogRepository;

	public BoxSensorLogService(BoxSensorLogRepository boxSensorLogRepository) {
		this.boxSensorLogRepository = boxSensorLogRepository;
	}

	// 분리 및 수거 후 수거함 센서 데이터 업데이트
	public void addBoxSensorLog(IoTResponse iotResponse) {
		BoxSensorLog boxSensorLog = new BoxSensorLog();
		BoxSensorLogId boxSensorLogId = new BoxSensorLogId(iotResponse.getId(), new Date());
		
		
		boxSensorLog.setId(boxSensorLogId);
		boxSensorLog.setTemperature(iotResponse.getTemperature());
		boxSensorLog.setHumidity(iotResponse.getHumidity());
		boxSensorLog.setWeight(iotResponse.getWeightNow());
		boxSensorLog.setSparkStatus(iotResponse.getSparkStatus());
		boxSensorLog.setFire(iotResponse.getFire());
		boxSensorLogRepository.save(boxSensorLog);
	}
}
