package com.ihsmarkit.hotelbooking.bookingmanager;

import com.ihsmarkit.hotelbooking.exception.InvalidRoomNumberException;
import com.ihsmarkit.hotelbooking.exception.RoomException;
import com.ihsmarkit.hotelbooking.exception.RoomNotAvailableException;
import com.ihsmarkit.hotelbooking.hotel.Hotel;
import com.ihsmarkit.hotelbooking.hotel.HotelLondon;
import net.jodah.concurrentunit.Waiter;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class BookingManagerTest {
    private BookingManager bookingManager;
    private LocalDate today = LocalDate.of(2017, 01, 26);
    private LocalDate tmrw = LocalDate.of(2017, 01, 27);

    @Before
    public void setUp() throws Exception {
        Hotel hotel = new HotelLondon(Stream.of(101, 102, 201, 203).collect(Collectors.toCollection(HashSet::new)), new ConcurrentHashMap<>());
        bookingManager = new BookingManagerImpl(hotel);
        bookingManager.addBooking("Smith", 101, today);
        bookingManager.addBooking("Jones", 102, today);
        bookingManager.addBooking("Patel", 201, today);
        bookingManager.addBooking("Perry", 203, today);
    }

    @Test(expected = RoomNotAvailableException.class)
    public void roomWillNotBeAvailable() throws RoomException {
        bookingManager.addBooking("James", 101, today);
    }

    @Test(expected = InvalidRoomNumberException.class)
    public void roomNumberWillBeInvalid() throws RoomException {
        bookingManager.addBooking("James", 103, today);
    }

    @Test
    public void thereWillBeNoAvailableRoomsForToday() {
        assertEquals(new HashSet<Integer>(), bookingManager.getAvailableRooms(today));
    }

    @Test
    public void thereWillBeThreeAvailableRoomsForTomorrowWithNewGuestBooking() throws RoomException {
        bookingManager.addBooking("James", 203, tmrw);
        assertEquals(Stream.of(101, 102, 201).collect(Collectors.toCollection(HashSet::new)), bookingManager.getAvailableRooms(tmrw));
    }

    @Test
    public void thereWillBeThreeAvailableRoomsForTomorrowWithExistingGuestBooking() throws RoomException {
        bookingManager.addBooking("Perry", 203, tmrw);
        assertEquals(Stream.of(101, 102, 201).collect(Collectors.toCollection(HashSet::new)), bookingManager.getAvailableRooms(tmrw));
    }

    @Test
    public void multipleBookingsAtTheSameTimeWillResultInOneSuccessfulBooking() throws TimeoutException, InterruptedException {
        Waiter waiter = new Waiter();
        BookingManagerThread bookingManagerThread = new BookingManagerThread(bookingManager, "Perry", 203, tmrw, waiter);
        Thread thread1 = new Thread(bookingManagerThread, "Thread 1");
        Thread thread2 = new Thread(bookingManagerThread, "Thread 2");
        Thread thread3 = new Thread(bookingManagerThread, "Thread 3");
        thread1.start();
        thread2.start();
        thread3.start();
        waiter.await(10, 3);
        assertEquals(1, BookingManagerThread.successfulBooking);
    }
}