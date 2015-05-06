package dk.gokartland.booking.domain;

import java.util.Date;

public class GokartBooking extends FacilityBooking {

	private int adultCarts;

	private int childrenCarts;

	private boolean champagne;

	private boolean medals;

	public GokartBooking(Date from, Date to, String comments, int adultCarts, int childrenCarts, BookablePlace bookablePlace, boolean champagne, boolean medals) {
		super(from, to, comments, (short) (adultCarts + childrenCarts), bookablePlace);
		this.adultCarts = adultCarts;
		this.childrenCarts = childrenCarts;
		this.champagne = champagne;
		this.medals = medals;
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