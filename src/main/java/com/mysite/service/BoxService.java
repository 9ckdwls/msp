package com.mysite.service;

import com.mysite.entity.Box;
import com.mysite.repository.BoxRepository;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoxService {

	private final BoxRepository repository;
	private final GeometryFactory geometryFactory;

	public BoxService(BoxRepository repository) {
		this.repository = repository;
		this.geometryFactory = new GeometryFactory();
	}

	//근처 박스 찾기
	public List<Box> findNearbyBoxes(double latitude, double longitude, double radius) {
		// 주어진 좌표에서 Point 객체 생성
		Point point = geometryFactory.createPoint(new Coordinate(longitude, latitude));

		// 위치 내의 Box를 조회
		return repository.findByLocationWithinRadius(point, radius);
	}

	//특정 id로 Box 찾기
	public Box findBoxById(int id) {
		Optional<Box> boxOptional = repository.findById(id);
		return boxOptional.get(); // 존재하지 않을 경우 null 반환
	}
	
	//수거함 무게에 따라 사용여부 변경
	public void boxUpdate(int id, int used) {
		Box box = findBoxById(id);
		box.setUsed(used); //얼마나 차 있는지
		repository.save(box);
		
		if(box.getUsed()>50) {
			//리액트로 api 요청 보내기
			List<Box> boxes = repository.findBoxesWithWeightGreaterThanOrEqual(70);
		} else if(box.getUsed()<50) {
			//리액트로 api 요청 보내기
			List<Box> boxes = repository.findBoxesWithWeightLessThan(50);
		}
	}
}
