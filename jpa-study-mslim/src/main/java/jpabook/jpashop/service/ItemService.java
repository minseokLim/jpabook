package jpabook.jpashop.service;

import static jpabook.jpashop.domain.item.QItem.item;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;

@Service
@Transactional
public class ItemService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	public List<Item> findItemsByPrice(int low, int high) {
		Iterable<Item> items = itemRepository.findAll(item.price.between(low, high));
		return Lists.newArrayList(items);
	}
}
