package jpabook.jpashop.domain;

import static jpabook.jpashop.domain.OrderSpec.memberNameLike;
import static jpabook.jpashop.domain.OrderSpec.orderStatusEq;
import static org.springframework.data.jpa.domain.Specification.where;

import org.springframework.data.jpa.domain.Specification;

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
	
	public Specification<Order> toSpecification() {
		return where(memberNameLike(memberName)).and(orderStatusEq(orderStatus));
	}
}
