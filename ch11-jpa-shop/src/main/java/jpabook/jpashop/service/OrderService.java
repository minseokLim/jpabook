package jpabook.jpashop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.DeliveryStatus;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderSearch;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;

@Service
@Transactional
public class OrderService {
	
	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ItemService itemService;
	
	public Long order(Long memberId, Long itemId, int count) {
		
		Member member = memberRepository.findOne(memberId);
		Item item = itemService.findOne(itemId);
		
		Delivery delivery = Delivery.builder().address(member.getAddress()).status(DeliveryStatus.READY).build();
		OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
		Order order = Order.createOrder(member, delivery, orderItem);
		
		orderRepository.save(order);
		return order.getId();
	}
	
	public void cancelOrder(Long orderId) {
		Order order = orderRepository.findOne(orderId);
		order.cancel();
	}
	
	public List<Order> findOrders(OrderSearch orderSearch) {
		return orderRepository.findAll(orderSearch);
	}
}
