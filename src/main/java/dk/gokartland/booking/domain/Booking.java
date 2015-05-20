package dk.gokartland.booking.domain;

import dk.gokartland.booking.domain.exceptions.ValidationException;

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

    @Column
    private boolean isPaid;

    @Column
    private String referenceNumber;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "booking_id")
    private List<FacilityBooking> facilityBookings = new ArrayList<>();

    public Booking(String customerName, String phoneNumber, boolean isPrivateClient, boolean needsPermission, String email, String comments, String createdBy, boolean isPaid, String referenceNumber, List<FacilityBooking> facilityBookings) throws ValidationException {
        changeCustomerName(customerName);
        changePhoneNumber(phoneNumber);
        this.isPrivateClient = isPrivateClient;
        this.needsPermission = needsPermission;
        this.email = email;
        this.comments = comments;
        changeCreatedBy(createdBy);
        this.facilityBookings = facilityBookings;
        this.isPaid = isPaid;
        this.referenceNumber = referenceNumber;

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

    public void changeCustomerName(String newName) throws ValidationException {
        if(newName.equals("")) throw new ValidationException("Customer name is missing");
        this.customerName = newName;
    }

    public void changePhoneNumber(String phoneNumber) throws ValidationException {
        if(phoneNumber.equals("")) throw new ValidationException("Customer phonenumber is missing");
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

    public void changeCreatedBy(String createdBy) throws ValidationException {
        if(createdBy.equals("")) throw new ValidationException("Missing 'created by' signature ");
        this.createdBy = createdBy;
    }

    public boolean getIsPaid() {
        return isPaid;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void changeReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public void changePaid(boolean isPaid) {
        this.isPaid = isPaid;
    }
}
