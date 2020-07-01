package jpabook.jpashop.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import jpabook.jpashop.domain.item.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Table(name = "ORDER_ITEM")
@NoArgsConstructor
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_ITEM_ID")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ITEM_ID")
	private Item item;
	
	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_ID")
	private Order order;
	
	private int orderPrice;
	
	private int count;

	@Builder
	public OrderItem(Item item, Order order, int orderPrice, int count) {
		this.item = item;
		this.order = order;
		if(order != null) order.getOrderItems().add(this);
		
		this.orderPrice = orderPrice;
		this.count = count;
	}

	public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
		OrderItem orderItem = OrderItem.builder().item(item).orderPrice(orderPrice).count(count).build();
		item.removeStock(count);

		return orderItem;
	}
	
	public void cancel() {
		item.addStock(count);
	}
	
	public int getTotalPrice() {
		return orderPrice * count;
	}
}
