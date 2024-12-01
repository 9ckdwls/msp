package com.mysite.repository;

import com.mysite.entity.Box;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoxRepository extends JpaRepository<Box, Integer> {

    // 주어진 Point에서 지정된 반경 내의 Box를 찾기 위한 메서드
    @Query("SELECT b FROM Box b WHERE ST_Distance_Sphere(b.location, ?1) <= ?2")
    List<Box> findByLocationWithinRadius(Point point, double radius);
    
    // 주어진 무게보다 큰 박스 구하기
    @Query("SELECT b FROM Box b WHERE b.used >= ?1")
    List<Box> findBoxesWithWeightGreaterThanOrEqual(int used);
    
    // 주어진 무게보다 작은 박스 구하기
    @Query("SELECT b FROM Box b WHERE b.used < ?1")
    List<Box> findBoxesWithWeightLessThan(int used);
}
