package jpabook.jpashop.repository;

import java.util.List;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderSearch;

public interface CustomOrderRepository {

	public List<Order> search(OrderSearch orderSearch); 
}
