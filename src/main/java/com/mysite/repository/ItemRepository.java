package com.mysite.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mysite.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer>{

	Optional<Item> findById(int itemId);

}
