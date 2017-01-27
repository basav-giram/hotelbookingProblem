package com.ihsmarkit.hotelbooking.bookingmanager;

import com.ihsmarkit.hotelbooking.exception.InvalidRoomNumberException;
import com.ihsmarkit.hotelbooking.exception.RoomNotAvailableException;
import com.ihsmarkit.hotelbooking.hotel.Hotel;
import com.ihsmarkit.hotelbooking.hotel.HotelLondon;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class BookingManagerTest {
    BookingManager bookingManager;

    LocalDate today = LocalDate.of(2017, 01, 26);
    LocalDate tmrw = LocalDate.of(2017, 01, 27);

    public BookingManagerTest() throws ParseException {
    }

    @Before
    public void setUp() throws Exception {
        Hotel hotel = new HotelLondon(Stream.of(101, 102, 201, 203).collect(Collectors.toCollection(HashSet::new)), new ConcurrentHashMap<>());
        bookingManager = new BookingManagerLondon(hotel);
        bookingManager.addBooking("Smith", 101, today);
        bookingManager.addBooking("Jones", 102, today);
        bookingManager.addBooking("Patel", 201, today);
        bookingManager.addBooking("Perry", 203, today);
        bookingManager.addBooking("Perry", 203, tmrw);
    }

    @Test(expected = RoomNotAvailableException.class)
    public void roomWillNotBeAvailable() throws Exception {
        bookingManager.addBooking("James", 101, today);
    }

    @Test(expected = InvalidRoomNumberException.class)
    public void roomNumberWillBeInvalid() throws Exception {
        bookingManager.addBooking("James", 103, today);
    }

    @Test
    public void thereWillBeNoAvailableRoomsForToday() throws Exception {
        assertEquals(new HashSet<Integer>(), bookingManager.getAvailableRooms(today));
    }

    @Test
    public void thereWillBeThreeAvailableRoomsForTomorrow() throws Exception {
        assertEquals(Stream.of(101, 102, 201).collect(Collectors.toCollection(HashSet::new)), bookingManager.getAvailableRooms(tmrw));

    }

}