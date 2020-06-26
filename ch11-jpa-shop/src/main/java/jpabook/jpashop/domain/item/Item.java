package jpabook.jpashop.domain.item;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;

import jpabook.jpashop.domain.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor
@DiscriminatorColumn(name = "DTYPE")
public abstract class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ITEM_ID")
	private Long id;
	
	private String name;
	
	private int price;
	
	private int stockQuantity;
	
	@ManyToMany(mappedBy = "items")
	private List<Category> categories = new ArrayList<Category>();

	public Item(String name, int price, int stockQuantity) {
		this.name = name;
		this.price = price;
		this.stockQuantity = stockQuantity;
	}
	
	public void addCategory(Category category) {
		categories.add(category);
		category.getItems().add(this);
	}
}
