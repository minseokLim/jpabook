package jpabook.jpashop.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import jpabook.jpashop.domain.item.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Item item;
	
	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	private Order order;
	
	private int orderPrice;
	
	private int count;

	@Builder
	public OrderItem(Item item, Order order, int orderPrice, int count) {
		this.item = item;
		this.order = order;
		order.getOrderItems().add(this);
		
		this.orderPrice = orderPrice;
		this.count = count;
	}
}
