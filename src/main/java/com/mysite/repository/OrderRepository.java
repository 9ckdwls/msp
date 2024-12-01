package com.mysite.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mysite.entity.OrderDetails;
import com.mysite.entity.OrderId;

@Repository
public interface OrderRepository extends JpaRepository<OrderDetails, OrderId> {

	List<OrderDetails> findByOrderIdUserIdAndState(String userId, int state);

	//Order 객체의 OrderId에서 최대 id 찾기
	Optional<OrderDetails> findTopByOrderByOrderIdIdDesc();

}
