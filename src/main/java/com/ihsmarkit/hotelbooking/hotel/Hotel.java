package com.ihsmarkit.hotelbooking.hotel;

import com.ihsmarkit.hotelbooking.exception.RoomException;

import java.util.Date;

public interface Hotel {
    boolean isRoomAvailable(Integer room, Date date);

    void addBooking(String guest, Integer room, Date date) throws RoomException;

    Iterable<Integer> getAvailableRooms(Date date);
}
