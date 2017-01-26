package com.ihsmarkit.hotelbooking.hotel;

import javafx.util.Pair;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public final class HotelImpl implements Hotel {

    private final ConcurrentHashMap<String, Pair<Integer, Date>> guestToRoomWithDate;


    public HotelImpl() {
        guestToRoomWithDate = new ConcurrentHashMap<String,Pair<Integer,Date>>();
    }
}
