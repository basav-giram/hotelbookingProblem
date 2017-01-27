package com.ihsmarkit.hotelbooking.bookingmanager;

import com.ihsmarkit.hotelbooking.exception.RoomException;
import net.jodah.concurrentunit.Waiter;

import java.time.LocalDate;

public class BookingManagerThread implements Runnable {

    static int successfulBooking = 0;
    private final String guest;
    private final Integer room;
    private final LocalDate date;
    private final Waiter waiter;
    private BookingManager bookingManager;

    public BookingManagerThread(BookingManager bookingManager, String guest, Integer room, LocalDate date, Waiter waiter) {
        this.bookingManager = bookingManager;
        this.guest = guest;
        this.room = room;
        this.date = date;
        this.waiter = waiter;
    }

    @Override
    public void run() {
        try {
            bookingManager.addBooking(guest, room, date);
            successfulBooking++;
            System.out.println("Successful booking by thread: " + Thread.currentThread().getName());
            waiter.resume();
        } catch (RoomException e) {
            System.out.println("Room Exception while addBooking by thread: " + Thread.currentThread().getName());
            waiter.resume();
        }
    }
}
