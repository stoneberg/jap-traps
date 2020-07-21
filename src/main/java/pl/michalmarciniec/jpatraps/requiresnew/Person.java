package pl.michalmarciniec.jpatraps.requiresnew;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class Person {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@OneToOne
	private Wallet wallet;

	protected Person() {
	}

	public Person(String name) {
		this.name = name;
	}

	public Person(String name, Wallet wallet) {
		this.name = name;
		this.wallet = wallet;
	}


}
