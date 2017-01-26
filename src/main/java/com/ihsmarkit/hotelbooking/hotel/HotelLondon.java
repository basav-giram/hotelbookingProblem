package com.ihsmarkit.hotelbooking.hotel;

import com.ihsmarkit.hotelbooking.exception.InvalidRoomNumberException;
import com.ihsmarkit.hotelbooking.exception.RoomException;
import com.ihsmarkit.hotelbooking.exception.RoomNotAvailableException;
import javafx.util.Pair;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class HotelLondon implements Hotel {

    private final ConcurrentHashMap<String, List<Pair<Integer, Date>>> guestToRoomWithDate;
    private Set<Integer> allRooms = Collections.synchronizedSet(new HashSet<Integer>());

    public HotelLondon(Set<Integer> allRooms, ConcurrentHashMap<String, List<Pair<Integer, Date>>> guestToRoomWithDate) {
        this.allRooms = allRooms;
        this.guestToRoomWithDate = guestToRoomWithDate;
    }

    public void addBooking(String guest, Integer room, Date date) throws RoomException {
        if (!allRooms.contains(room))
            throw new InvalidRoomNumberException("Invalid room number: " + room);
        if (isRoomAvailable(room, date)) {
            List<Pair<Integer, Date>> roomWithDateList;
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

    public boolean isRoomAvailable(Integer room, Date date) {
        for (List<Pair<Integer, Date>> roomWithDateList : guestToRoomWithDate.values()) {
            if (roomWithDateList.contains(new Pair<>(room, date)))
                return false;
        }
        return true;
    }

    public Iterable<Integer> getAvailableRooms(Date date) {
        Set<Integer> availableRooms = allRooms;

        for (List<Pair<Integer, Date>> roomWithDateList : guestToRoomWithDate.values())
            for (Pair<Integer, Date> roomWithDate : roomWithDateList) {
                if (roomWithDate.getValue().equals(date))
                    availableRooms.remove(roomWithDate.getKey());
            }
        return availableRooms;
    }
}
