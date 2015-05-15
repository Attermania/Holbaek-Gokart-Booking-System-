package dk.gokartland.booking.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Booking {

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String customerName;

    @Column
    private String phoneNumber;

    @Column
    private boolean isPrivateClient;

    @Column
    private boolean needsPermission;

    @Column
    private String email;

    @Column
    private String comments;

    @Column
    private String createdBy;

    @OneToMany(targetEntity = FacilityBooking.class, cascade = CascadeType.PERSIST)
    private List<FacilityBooking> facilityBookings = new ArrayList<>();

    public Booking(String customerName, String phoneNumber, boolean isPrivateClient, boolean needsPermission, String email, String comments, String createdBy, List<FacilityBooking> facilityBookings) {
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.isPrivateClient = isPrivateClient;
        this.needsPermission = needsPermission;
        this.email = email;
        this.comments = comments;
        this.createdBy = createdBy;
        this.facilityBookings = facilityBookings;
    }

    protected Booking() {
    }

    public void addFacilityBooking(FacilityBooking facilityBooking) {
        facilityBookings.add(facilityBooking);
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<FacilityBooking> getFacilityBookings() {
        return facilityBookings;
    }

    public int getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isPrivateClient() {
        return isPrivateClient;
    }

    public boolean isNeedsPermission() {
        return needsPermission;
    }

    public String getEmail() {
        return email;
    }

    public String getComments() {
        return comments;
    }

    public String getCreatedBy() {
        return createdBy;
    }
}
