package org.arthan.booking.enhanced;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by artur.shamsiev on 09.10.2014.
 */

/**
 * Представляет собой расписание для комнаты. Содержит в себе дни {@link DayOfWeek} с ассоциированными им
 * дневными расписаниями {@link DaySchedule}
 */
public class RoomSchedule {

    private Map<DayOfWeek, DaySchedule> dayMap = Maps.newHashMap();

    public void book(DayOfWeek day, int hour) {
        getScheduleForDay(day).book(hour);
    }

    @VisibleForTesting
    DaySchedule getScheduleForDay(DayOfWeek day) {
        return dayMap.get(day);
    }

    @VisibleForTesting
    void setDaySchedule(DayOfWeek day, DaySchedule daySchedule) {
        dayMap.put(day, daySchedule);
    }
}
