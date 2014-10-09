package org.arthan.booking.enhanced;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.Map;

import static junitparams.JUnitParamsRunner.*;
import static org.mockito.Mockito.*;

/**
 * Created by artur.shamsiev on 09.10.2014.
 */
@RunWith(JUnitParamsRunner.class)
public class BookingDataTest {

    private Object[] parametersForRoomsWithoutSchedule() {
        return $(
                $("A3", null, 1),
                $("B14", new RoomSchedule(), 0)
        );
    }

    /**
     * При вызове метода {@link BookingData#bookClassroom(String, DayOfWeek, int)} помещает объект класса
     * {@link org.arthan.booking.enhanced.RoomSchedule} в ассоциативный массив комнат только если для комнаты нет расписания
     * испоьзуя преданный методу параметр id
     * @throws Exception
     */
    @Test
    @Parameters(method = "parametersForRoomsWithoutSchedule")
    public void shouldPutRoomScheduleObjectToRoomMapByRoomIDWhenBookingClassroomIfRoomHasNoSchedule(
            String id,
            RoomSchedule roomMapReturn,
            int roomMapPutTimes) throws Exception {

        final Map<String, RoomSchedule> mockMap = mock(Map.class);
        when(mockMap.get(id)).thenReturn(roomMapReturn);

        final BookingData bookingData = new BookingData();
        bookingData.setRoomsMap(mockMap);

        bookingData.prepareSchedule(id);
        verify(mockMap, times(roomMapPutTimes)).put(eq(id), Mockito.any(RoomSchedule.class));
    }

    /**
     * При вызове метода {@link BookingData#bookClassroom(String, DayOfWeek, int)} вызывает метод
     * {@link RoomSchedule#book(DayOfWeek, int)} объекта {@link RoomSchedule} ассоциированного с переданным id
     * и передать этому методу день недели и час
     * @throws Exception
     */
    @Test
    public void shouldPassDayAndHourToRoomScheduleObjectAssociatedWithID() throws Exception {
        final String idA = "A1";
        final String idB = "B2";

        final DayOfWeek day = DayOfWeek.WEDNESDAY;
        final int hour = 15;

        final RoomSchedule mockScheduleA = mock(RoomSchedule.class);
        final RoomSchedule mockScheduleB = mock(RoomSchedule.class);

        final Map<String, RoomSchedule> mockMap = mock(Map.class);
        when(mockMap.get(idA)).thenReturn(mockScheduleA);
        when(mockMap.get(idB)).thenReturn(mockScheduleB);

        final BookingData bookingData = new BookingData();
        bookingData.setRoomsMap(mockMap);

        bookingData.bookClassroom(
                idA,
                day,
                hour
        );

        verify(mockScheduleA).book(day, hour);
        verify(mockScheduleB, never()).book(any(DayOfWeek.class), anyInt());
    }
}
