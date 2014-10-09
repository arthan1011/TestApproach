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
     * @param roomId
     * @param day
     * @param hour
     */
    public void bookClassroom(String roomId, DayOfWeek day, int hour) {
        prepareSchedule(roomId);
        roomMap.get(roomId).book(day, hour);
    }

    /**
     * Назначает расписание для комнаты если у нее нет расписания
     * @param roomId
     */
    @VisibleForTesting
    void prepareSchedule(String roomId) {
        if (roomMap.get(roomId) == null) {
            roomMap.put(roomId, new RoomSchedule());
        }
    }

    @VisibleForTesting
    void setRoomsMap(Map<String, RoomSchedule> roomMap) {
        this.roomMap = roomMap;
    }
}
