package dk.gokartland.booking.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class Booking {

    private int id;

    private String customerName;

    private String phoneNumber;

    private boolean isBusiness;

    private boolean needsPermission;

    private String email;

    private String comments;

    private String createdBy;

    private List<FacilityBooking> facilityBookings = new ArrayList<>();

    public Booking(String customerName, String phoneNumber, boolean isBusiness, boolean needsPermission, String email, String comments, String createdBy, List<FacilityBooking> facilityBookings) {
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.isBusiness = isBusiness;
        this.needsPermission = needsPermission;
        this.email = email;
        this.comments = comments;
        this.createdBy = createdBy;
    }

    public void addFacilityBooking(FacilityBooking facilityBooking) {
        facilityBookings.add(facilityBooking);
    }

}
