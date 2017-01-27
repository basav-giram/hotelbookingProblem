package com.ihsmarkit.hotelbooking.hotel;

import com.ihsmarkit.hotelbooking.exception.RoomException;

import java.time.LocalDate;

public interface Hotel {
    boolean isRoomAvailable(Integer room, LocalDate date);

    void addBooking(String guest, Integer room, LocalDate date) throws RoomException;

    Iterable<Integer> getAvailableRooms(LocalDate date);
}
