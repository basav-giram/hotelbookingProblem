package com.ihsmarkit.hotelbooking.hotel;

import com.ihsmarkit.hotelbooking.exception.InvalidRoomNumberException;
import com.ihsmarkit.hotelbooking.exception.RoomException;
import com.ihsmarkit.hotelbooking.exception.RoomNotAvailableException;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class HotelLondon implements Hotel {

    //Single source for all booking information implemented with thread-safe ConcurrentHashMap
    private final ConcurrentHashMap<String, List<Pair<Integer, LocalDate>>> guestToRoomWithDate;

    //Immutable set represents all rooms in the hotel
    private final Set<Integer> allRooms;

    public HotelLondon(Set<Integer> allRooms, ConcurrentHashMap<String, List<Pair<Integer, LocalDate>>> guestToRoomWithDate) {
        this.allRooms = Collections.unmodifiableSet(allRooms);
        this.guestToRoomWithDate = guestToRoomWithDate;
    }

    @Override
    public synchronized void addBooking(String guest, Integer room, LocalDate date) throws RoomException {
        if (!allRooms.contains(room))
            throw new InvalidRoomNumberException("Invalid room number: " + room);
        if (isRoomAvailable(room, date)) {
            List<Pair<Integer, LocalDate>> roomWithDateList;
            if (guestToRoomWithDate.containsKey(guest)) {
                roomWithDateList = guestToRoomWithDate.get(guest);
            } else {
                roomWithDateList = new ArrayList<>();
            }
            roomWithDateList.add(new Pair<>(room, date));
            guestToRoomWithDate.put(guest, roomWithDateList);
        } else
            throw new RoomNotAvailableException(String.format("Room: %s is not available on %s", room, date));
    }

    @Override
    public synchronized boolean isRoomAvailable(Integer room, LocalDate date) {
        for (List<Pair<Integer, LocalDate>> roomWithDateList : guestToRoomWithDate.values()) {
            if (roomWithDateList.contains(new Pair<>(room, date)))
                return false;
        }
        return true;
    }

    @Override
    public synchronized Iterable<Integer> getAvailableRooms(LocalDate date) {
        Set<Integer> availableRooms = new HashSet<>(allRooms);

        for (List<Pair<Integer, LocalDate>> roomWithDateList : guestToRoomWithDate.values())
            for (Pair<Integer, LocalDate> roomWithDate : roomWithDateList) {
                if (roomWithDate.getValue().equals(date))
                    availableRooms.remove(roomWithDate.getKey());
            }
        return availableRooms;
    }
}
