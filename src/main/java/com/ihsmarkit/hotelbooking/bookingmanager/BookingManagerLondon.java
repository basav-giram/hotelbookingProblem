package com.ihsmarkit.hotelbooking.bookingmanager;

import com.ihsmarkit.hotelbooking.exception.RoomException;
import com.ihsmarkit.hotelbooking.hotel.Hotel;

import java.time.LocalDate;


public class BookingManagerLondon implements BookingManager {

    private final Hotel hotel;

    public BookingManagerLondon(Hotel hotel) {
        this.hotel = hotel;
    }

    public synchronized boolean isRoomAvailable(Integer room, LocalDate date) {
        return hotel.isRoomAvailable(room, date);
    }

    public synchronized void addBooking(String guest, Integer room, LocalDate date) throws RoomException {
        hotel.addBooking(guest, room, date);
    }

    public synchronized Iterable<Integer> getAvailableRooms(LocalDate date) {
        return hotel.getAvailableRooms(date);
    }
}
