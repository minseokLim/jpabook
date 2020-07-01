package jpabook.jpashop.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@DiscriminatorValue("A")
public class Album extends Item {

	private String artist;
	
	private String etc;

	@Builder
	public Album(String name, int price, int stockQuantity, String artist, String etc) {
		super(name, price, stockQuantity);
		this.artist = artist;
		this.etc = etc;
	}
}
