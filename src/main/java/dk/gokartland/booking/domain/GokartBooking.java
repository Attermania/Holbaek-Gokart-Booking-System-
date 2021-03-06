package dk.gokartland.booking.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.Date;

@Entity
@PrimaryKeyJoinColumn
public class GokartBooking extends FacilityBooking {

	@Column
	private int adultCarts;

	@Column
	private int childrenCarts;

	@Column
	private boolean champagne;

	@Column
	private boolean medals;

	public GokartBooking(Date from, Date to, String comments, int adultCarts, int childrenCarts, BookablePlace bookablePlace, boolean champagne, boolean medals) {
		super(from, to, comments, (short) (adultCarts + childrenCarts), bookablePlace);
		this.adultCarts = adultCarts;
		this.childrenCarts = childrenCarts;
		this.champagne = champagne;
		this.medals = medals;
	}

	protected GokartBooking() {
	}

	public int getAdultCarts() {
		return adultCarts;
	}

	public int getChildrenCarts() {
		return childrenCarts;
	}

	public boolean wantsChampagne() {
		return champagne;
	}

	public boolean wantsMedals() {
		return medals;
	}
}