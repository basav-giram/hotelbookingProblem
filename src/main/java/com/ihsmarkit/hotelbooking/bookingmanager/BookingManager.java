package com.ihsmarkit.hotelbooking.bookingmanager;

import com.ihsmarkit.hotelbooking.exception.RoomException;

import java.time.LocalDate;

public interface BookingManager {
    /**
     * Return true if there is no booking for the given room on the date,
     * otherwise false
     */
    boolean isRoomAvailable(Integer room, LocalDate date);

    /**
     * Add a booking for the given guest in the given room on the given
     * date. If the room is not available, throw a suitable Exception.
     */
    void addBooking(String guest, Integer room, LocalDate date) throws RoomException;

    /**
     * Return a list of all the available room numbers for the given date
     * @param date
     */
    Iterable<Integer> getAvailableRooms(LocalDate date);
}
