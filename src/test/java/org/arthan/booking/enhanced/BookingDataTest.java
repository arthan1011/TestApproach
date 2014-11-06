package org.arthan.booking.enhanced;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

import static junitparams.JUnitParamsRunner.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by artur.shamsiev on 09.10.2014.
 */
@RunWith(JUnitParamsRunner.class)
public class BookingDataTest {

    private Object[] parametersForRoomsWithoutSchedule() {
        return $(
                $("A3", false, 1),
                $("B14", true, 0)
        );
    }

    /**
     * Должен правильно сохранять {@link org.arthan.booking.enhanced.RoomSchedule} объекты для комнат
     * и проверять наличие сохраненных расписаний для комнат для идентификаторов комнат
     * @throws Exception
     */
    @Test
    public void shouldSetAndCheckRoomScheduleByRoomID() throws Exception {
        String idA = "A1";
        String idB = "B2";

        RoomSchedule mockA = mock(RoomSchedule.class);
        RoomSchedule mockB = mock(RoomSchedule.class);

        BookingData bookingData = new BookingData();
        bookingData.setRoomSchedule(idA, mockA);

        assertTrue("RoomSchedule was saved for room ID: " + idA + " but isn't present", bookingData.hasRoomScheduleForRoomID(idA));
        assertFalse("RoomSchedule wasn't saved for room ID: " + idB + " but is present", bookingData.hasRoomScheduleForRoomID(idB));
    }

    /**
     * Должен правильно сохранять {@link org.arthan.booking.enhanced.RoomSchedule} объекты для комнат
     * и возвращать их по соответствующему идентификатору комнаты
     * @throws Exception
     */
    @Test
    public void shouldSetAndRetrieveRoomScheduleByRoomID() throws Exception {
        String idA = "A1";
        String idB = "B2";

        RoomSchedule mockA = mock(RoomSchedule.class);
        RoomSchedule mockB = mock(RoomSchedule.class);

        BookingData bookingData = new BookingData();
        bookingData.setRoomSchedule(idA, mockA);

        assertSame("RoomSchedule was saved for roomID: " + idA + " but wasn't retrieved properly",
                mockA, bookingData.getRoomScheduleForID(idA));
        assertNull("RoomSchedule wasn't saved for roomID: " + idB + " but was retrieved",
                bookingData.getRoomScheduleForID(idB));
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
            boolean scheduleSaved,
            int roomMapPutTimes) throws Exception {

        final BookingData bookingData = spy(new BookingData());
        doReturn(scheduleSaved).when(bookingData).hasRoomScheduleForRoomID(
                argThat(CoreMatchers.equalTo(id)));
        doReturn(mock(RoomSchedule.class)).when(bookingData).getRoomScheduleForID(id);

        DayOfWeek anyDay = DayOfWeek.MONDAY;
        int anyHour = 17;
        bookingData.bookClassroom(id, anyDay, anyHour);

        verify(bookingData, times(roomMapPutTimes)).setRoomSchedule(eq(id), any(RoomSchedule.class));
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

        final BookingData bookingData = spy(new BookingData());
        doReturn(mockScheduleA).when(bookingData).getRoomScheduleForID(idA);
        doReturn(mockScheduleB).when(bookingData).getRoomScheduleForID(idB);

        bookingData.bookClassroom(
                idA,
                day,
                hour
        );

        verify(mockScheduleA).book(day, hour);
        verify(mockScheduleB, never()).book(any(DayOfWeek.class), anyInt());
    }
}

