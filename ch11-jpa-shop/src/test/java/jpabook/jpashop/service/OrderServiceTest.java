package jpabook.jpashop.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:appConfig.xml")
@Transactional
public class OrderServiceTest {
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Test
	public void 상품주문() {
		Member member = createMember();
		int price = 10000;
		int stockQuantity = 10;
		Item item = createBook("시골 JPA", price, stockQuantity);
		
		int orderCount = 2;
		Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
		
		Order order = orderRepository.findOne(orderId);
		
		assertEquals("상품 주문 시 상태는 ORDER", OrderStatus.ORDER, order.getStatus());
		assertEquals("주문한 상품 종류 수가 정확해야한다.", 1, order.getOrderItems().size());
		assertEquals("주문한 가격은 가격 * 수량이다.", price * orderCount, order.getTotalPrice());
		assertEquals("주문한 만큼 재고가 줄어야한다.", stockQuantity - orderCount, item.getStockQuantity());
	}
	
	@Test(expected = NotEnoughStockException.class)
	public void 상품주문_재고수량초과() {
		Member member = createMember();
		int price = 10000;
		int stockQuantity = 10;
		Item item = createBook("시골 JPA", price, stockQuantity);

		int orderCount = 11;
		orderService.order(member.getId(), item.getId(), orderCount);
		
		fail("재고 수량 부족 예외가 발생해야 한다.");
	}
	
	@Test
	public void 주문취소() {
		Member member = createMember();
		int price = 10000;
		int stockQuantity = 10;
		Item item = createBook("시골 JPA", price, stockQuantity);
		
		int orderCount = 2;
		Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
		
		orderService.cancelOrder(orderId);
		
		Order order = orderRepository.findOne(orderId);
		
		assertEquals("주문 취소 시 상태는 CANCEL이다.", OrderStatus.CANCEL, order.getStatus());
		assertEquals("주문이 취소된 만큼 재고가 증가해야한다.", stockQuantity, item.getStockQuantity());
	}
	
	private Member createMember() {
		Member member = Member.builder()
							.name("회원1")
							.address(Address.builder().city("서울").street("강가").zipcode("123-123").build())
							.build();
		em.persist(member);
		
		return member;
	}
	
	private Book createBook(String name, int price, int stockQuantity) {
		Book book = Book.builder().name(name).price(price).stockQuantity(stockQuantity).build();
		em.persist(book);
		return book;
	}
}
