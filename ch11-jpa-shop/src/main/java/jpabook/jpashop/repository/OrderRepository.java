package jpabook.jpashop.repository;

import static jpabook.jpashop.domain.QOrder.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderSearch;

@Repository
public class OrderRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }
    
    public List<Order> findAll(OrderSearch orderSearch) {
    	JPAQueryFactory queryFactory = new JPAQueryFactory(em);
    	JPAQuery<Order> query = queryFactory.selectFrom(order);
    	
    	List<Predicate> predicates = new ArrayList<Predicate>(2);
    	
    	if(orderSearch.getOrderStatus() != null) {
    		predicates.add(order.status.eq(orderSearch.getOrderStatus()));
    	}
    	
    	if(StringUtils.hasText(orderSearch.getMemberName())) {
    		predicates.add(order.member.name.contains(orderSearch.getMemberName()));
    		query = query.join(order.member);
    	}
    	
    	return query.where(predicates.toArray(new Predicate[predicates.size()])).fetch();
    }
}
