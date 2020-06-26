package jpabook.jpashop.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderSearch {
	
	private String memberName;
	
	private OrderStatus orderStatus;

	@Builder
	public OrderSearch(String memberName, OrderStatus orderStatus) {
		this.memberName = memberName;
		this.orderStatus = orderStatus;
	}
}
