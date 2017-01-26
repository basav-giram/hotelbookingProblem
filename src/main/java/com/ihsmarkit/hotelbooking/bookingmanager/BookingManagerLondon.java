package com.ihsmarkit.hotelbooking.bookingmanager;

import com.ihsmarkit.hotelbooking.exception.RoomException;
import com.ihsmarkit.hotelbooking.hotel.Hotel;

import java.util.Date;


public class BookingManagerLondon implements BookingManager {

    private final Hotel hotel;

    public BookingManagerLondon(Hotel hotel) {
        this.hotel = hotel;
    }

    public boolean isRoomAvailable(Integer room, Date date) {
        return hotel.isRoomAvailable(room, date);
    }

    public void addBooking(String guest, Integer room, Date date) throws RoomException {
        hotel.addBooking(guest, room, date);
    }

    public Iterable<Integer> getAvailableRooms(Date date) {
        return hotel.getAvailableRooms(date);
    }
}
