package org.arthan.booking.enhanced;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Created by artur.shamsiev on 08.10.2014.
 * Предоставляет API для бронирования комнат
 */
public class ClassroomBookingSystem {

    private BookingData bookingData;
    private final Map<String, Classroom> registeredClassrooms = Maps.newHashMap();

    public ClassroomBookingSystem() {
        setBookingData(new BookingData());
    }

    public void registerClassroom(String roomID, Classroom classroom) {
        registeredClassrooms.put(roomID, classroom);
    }

    public Classroom getRoomByID(String roomID) {
        return registeredClassrooms.get(roomID);
    }

    public Collection<String> getRegisteredClassroomsIDs() {
        return registeredClassrooms.keySet();
    }

    public void bookClassroom(String roomId, DayOfWeek wednesday, int hour) {
        bookingData.bookClassroom(roomId, wednesday, hour);
    }

    @VisibleForTesting
    void setBookingData(BookingData bookingData) {
        this.bookingData = bookingData;
    }
}
