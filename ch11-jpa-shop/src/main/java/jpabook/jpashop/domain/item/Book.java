package jpabook.jpashop.domain.item;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import jpabook.jpashop.domain.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@DiscriminatorValue("B")
public class Book extends Item {

	private String author;
	
	private String isbn;

	@Builder
	public Book(String name, int price, int stockQuantity, List<Category> categories, String author, String isbn) {
		super(name, price, stockQuantity, categories);
		this.author = author;
		this.isbn = isbn;
	}
}
