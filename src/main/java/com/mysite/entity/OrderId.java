package com.mysite.entity;

import java.util.Objects;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class OrderId {
	
	private String userId;
	
	private int itemId;
	
	private int id;
	
	public OrderId() {
	}
	
	public OrderId(String userId, int itemId, int id) {
		this.userId = userId;
		this.itemId = itemId;
		this.id = id;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderId orderId = (OrderId) o;
        return itemId == orderId.itemId &&
               id == orderId.id &&
               Objects.equals(userId, orderId.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, itemId, id);
    }
}
