package dk.gokartland.booking.controllers;

import dk.gokartland.booking.domain.FacilityBooking;

public interface EditableController {
    public void setupForEdit(FacilityBooking facilityBooking);
}
