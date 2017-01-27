package com.ihsmarkit.hotelbooking.hotel;

import com.ihsmarkit.hotelbooking.exception.InvalidRoomNumberException;
import com.ihsmarkit.hotelbooking.exception.RoomException;
import com.ihsmarkit.hotelbooking.exception.RoomNotAvailableException;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class HotelLondon implements Hotel {

    private final ConcurrentHashMap<String, List<Pair<Integer, LocalDate>>> guestToRoomWithDate;
    private Set<Integer> allRooms = Collections.synchronizedSet(new HashSet<Integer>());

    public HotelLondon(Set<Integer> allRooms, ConcurrentHashMap<String, List<Pair<Integer, LocalDate>>> guestToRoomWithDate) {
        this.allRooms = allRooms;
        this.guestToRoomWithDate = guestToRoomWithDate;
    }

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

    public synchronized boolean isRoomAvailable(Integer room, LocalDate date) {
        for (List<Pair<Integer, LocalDate>> roomWithDateList : guestToRoomWithDate.values()) {
            if (roomWithDateList.contains(new Pair<>(room, date)))
                return false;
        }
        return true;
    }

    public synchronized Iterable<Integer> getAvailableRooms(LocalDate date) {
        Set<Integer> availableRooms = allRooms;

        for (List<Pair<Integer, LocalDate>> roomWithDateList : guestToRoomWithDate.values())
            for (Pair<Integer, LocalDate> roomWithDate : roomWithDateList) {
                if (roomWithDate.getValue().equals(date))
                    availableRooms.remove(roomWithDate.getKey());
            }
        return availableRooms;
    }
}
