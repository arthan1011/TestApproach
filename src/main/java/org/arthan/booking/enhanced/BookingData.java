package org.arthan.booking.enhanced;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by artur.shamsiev on 09.10.2014.
 */
public class BookingData {

    Map<String, RoomSchedule> roomMap;

    public BookingData() {
        roomMap = Maps.newHashMap();
    }

    /**
     * Бронирует опеределенную комнату на определенный час в определенный день недели.
     * @param roomID
     * @param day
     * @param hour
     */
    public void bookClassroom(String roomID, DayOfWeek day, int hour) {
        // при попытке забронировать комнату, у которой нет расписания создает новое расписание и ассоциирует
        // его с номером комнаты
        if (!hasRoomScheduleForRoomID(roomID)) {
            setRoomSchedule(roomID, new RoomSchedule());
        }
        getRoomScheduleForID(roomID).book(day, hour);
    }

    @VisibleForTesting
    RoomSchedule getRoomScheduleForID(String roomID) {
        return roomMap.get(roomID);
    }

    @VisibleForTesting
    void setRoomSchedule(String roomId, RoomSchedule roomSchedule) {
        roomMap.put(roomId, roomSchedule);
    }

    @VisibleForTesting
    boolean hasRoomScheduleForRoomID(String roomId) {
        return roomMap.get(roomId) != null;
    }

}
