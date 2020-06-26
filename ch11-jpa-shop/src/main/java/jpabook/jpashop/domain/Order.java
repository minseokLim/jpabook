package jpabook.jpashop.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Member member;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList<OrderItem>();
	
	@Setter
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Delivery delivery;
	
	private Date orderDate;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	@Builder
	public Order(Member member, Delivery delivery, Date orderDate, OrderStatus status) {
		this.member = member;
		member.getOrders().add(this);
		
		this.delivery = delivery;
		delivery.setOrder(this);
		
		this.orderDate = orderDate;
		this.status = status;
	}
	
	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
		orderItem.setOrder(this);
	}
}
