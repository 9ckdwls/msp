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
    @Query("SELECT b FROM Box b WHERE ST_Distance(b.location, ?1) <= ?2")
    List<Box> findByLocationWithinRadius(Point point, double radius);
}