package jpabook.jpashop.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@DiscriminatorValue("M")
public class Movie extends Item {

	private String director;
	
	private String actor;

	@Builder
	public Movie(String name, int price, int stockQuantity, String director, String actor) {
		super(name, price, stockQuantity);
		this.director = director;
		this.actor = actor;
	}
}
