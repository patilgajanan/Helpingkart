package com.appsplanet.helpingkart.Activity.DrawerFragments.Booking;

public class BookingItem {
    String booking_type, booking_date;
    //, booking_cost, booking_status;

    public BookingItem(String booking_type, String booking_date) {
        this.booking_type = booking_type;
        this.booking_date = booking_date;
        /*this.booking_cost = booking_cost;
        this.booking_status = booking_status;*/
    }

    public String getBooking_type() {
        return booking_type;
    }

    public void setBooking_type(String booking_type) {
        this.booking_type = booking_type;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(String booking_date) {
        this.booking_date = booking_date;
    }

  /*  public String getBooking_cost() {
        return booking_cost;
    }

    public void setBooking_cost(String booking_cost) {
        this.booking_cost = booking_cost;
    }

    public String getBooking_status() {
        return booking_status;
    }

    public void setBooking_status(String booking_status) {
        this.booking_status = booking_status;
    }*/
}
