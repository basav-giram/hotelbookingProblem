package com.ihsmarkit.hotelbooking.bookingManager;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class BookingManagerImpl implements BookingManager {


    public boolean isRoomAvailable(Integer room, Date date) {
        return false;
    }

    public void addBooking(String guest, Integer room, Date date) {

    }

    public Iterable<Integer> getAvailableRooms(Date date) {
        return null;
    }
}
