package jpabook.jpashop.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import jpabook.jpashop.domain.item.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CATEGORY_ID")
	private Long id;
	
	private String name;
	
	@ManyToMany
	@JoinTable(name = "CATEGORY_ITEM", 
		joinColumns = @JoinColumn(name = "CATEGORY_ID"), 
		inverseJoinColumns = @JoinColumn(name = "ITEM_ID"))
	private List<Item> items = new ArrayList<Item>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_ID")
	private Category parent;
	
	@OneToMany(mappedBy = "parent")
	private List<Category> children = new ArrayList<Category>();

	@Builder
	public Category(String name, Category parent) {
		this.name = name;
		this.parent = parent;
		parent.children.add(this);
	}
	
	public void addItem(Item item) {
		items.add(item);
		item.getCategories().add(this);
	}
}
