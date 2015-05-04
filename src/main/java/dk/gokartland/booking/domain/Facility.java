package dk.gokartland.booking.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "facilities")
public class Facility {

	@Id
	private int id;

}
