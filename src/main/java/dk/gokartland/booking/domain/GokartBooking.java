package dk.gokartland.booking.domain;

import dk.gokartland.booking.domain.exceptions.ValidationException;

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

	public GokartBooking(Calendar from, Calendar to, String comments, int adultCarts, int childrenCarts, BookablePlace bookablePlace, boolean champagne, boolean medals) throws ValidationException {
		super(from, to, comments, bookablePlace);
		changeCarts(adultCarts, childrenCarts);
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

    public void changeCarts(int adultCarts, int childrenCarts) throws ValidationException {
        if(adultCarts == 0 && childrenCarts == 0) throw new ValidationException("Number of adualtCarts and childrenCarts is 0");
        this.adultCarts = adultCarts;
        this.childrenCarts = childrenCarts;
    }

    public void changeChampagne(boolean champagne) {
        this.champagne = champagne;
    }

    public void changeMedals(boolean medals) {
        this.medals = medals;
    }


}