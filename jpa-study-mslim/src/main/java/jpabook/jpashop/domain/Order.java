package jpabook.jpashop.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Table(name = "ORDERS")
@NoArgsConstructor
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_ID")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEMBER_ID")
	private Member member;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList<OrderItem>();
	
	@Setter
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "DELIVERY_ID")
	private Delivery delivery;
	
	private Date orderDate;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	@Builder
	public Order(Member member, Delivery delivery, Date orderDate, OrderStatus status) {
		this.member = member;
		if(member != null) member.getOrders().add(this);
		
		this.delivery = delivery;
		if(delivery != null) delivery.setOrder(this);
		
		this.orderDate = orderDate;
		this.status = status;
	}
	
	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
		if(orderItem != null) orderItem.setOrder(this);
	}
	
	public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
		
		Order order = Order.builder().member(member).delivery(delivery).orderDate(new Date()).status(OrderStatus.ORDER).build();
		
		for(OrderItem orderItem : orderItems) {
			order.addOrderItem(orderItem);
		}
		
		return order;
	}
	
	public void cancel() {
		if(delivery.getStatus() == DeliveryStatus.COMP) {
			throw new RuntimeException("이미 배송 완료된 상품은 취소가 불가능합니다.");
		}
		
		this.status = OrderStatus.CANCEL;
		
		for(OrderItem orderItem : orderItems) {
			orderItem.cancel();
		}
	}
	
	public int getTotalPrice() {
		int totalPrice = 0;
		
		for(OrderItem orderItem : orderItems) {
			totalPrice += orderItem.getTotalPrice();
		}
		
		return totalPrice;
	}
}
