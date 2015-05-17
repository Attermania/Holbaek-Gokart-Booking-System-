package dk.gokartland.booking.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.Calendar;

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

	public GokartBooking(Calendar from, Calendar to, String comments, int adultCarts, int childrenCarts, BookablePlace bookablePlace, boolean champagne, boolean medals) {
		super(from, to, comments, bookablePlace);
		this.adultCarts = adultCarts;
		this.childrenCarts = childrenCarts;
		this.champagne = champagne;
		this.medals = medals;
	}

	protected GokartBooking() {
	}

	@Override
	public int getNumberOfPeople() {
		return adultCarts + childrenCarts;
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

    public void changeAdultCarts(int adultCarts) {
        this.adultCarts = adultCarts;
    }

    public void changeChildrenCarts(int childrenCarts) {
        this.childrenCarts = childrenCarts;
    }

    public void changeChampagne(boolean champagne) {
        this.champagne = champagne;
    }

    public void changeMedals(boolean medals) {
        this.medals = medals;
    }
}