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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "booking_id")
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

        for(FacilityBooking facilityBooking : facilityBookings) {
            facilityBooking.setBooking(this);
        }
    }

    protected Booking() {
    }

    public void addFacilityBooking(FacilityBooking facilityBooking) {
        // Return if the facility booking already exists
        for(FacilityBooking fb : facilityBookings) {
            if(fb == facilityBooking) return;
        }

        facilityBooking.setBooking(this);

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

    public void changeCustomerName(String newName) {
        this.customerName = newName;
    }

    public void changePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void changePrivateClient(boolean isPrivateClient) {
        this.isPrivateClient = isPrivateClient;
    }

    public void changeNeedsPermission(boolean needsPermission) {
        this.needsPermission = needsPermission;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changeComments(String comments) {
        this.comments = comments;
    }

    public void changeCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
