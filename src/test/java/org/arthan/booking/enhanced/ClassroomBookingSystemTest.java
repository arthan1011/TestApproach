package org.arthan.booking.enhanced;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.Collection;

import static junitparams.JUnitParamsRunner.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by artur.shamsiev on 08.10.2014.
 */

@RunWith(JUnitParamsRunner.class)
public class ClassroomBookingSystemTest {


    private ClassroomBookingSystem bookingSystem;
    private ClassroomBookingSystem preparedBookingSystem;

    @Before
    public void setUp() throws Exception {
        bookingSystem = new ClassroomBookingSystem();

        preparedBookingSystem = new ClassroomBookingSystem();
        final String FIRST_ID = "A1";
        final String SECOND_ID = "B3";
        final String THIRD_ID = "G9";

        final Classroom FIRST_ROOM = mock(Classroom.class);
        final Classroom SECOND_ROOM = mock(Classroom.class);
        final Classroom THIRD_ROOM = mock(Classroom.class);

        preparedBookingSystem.registerClassroom(FIRST_ID, FIRST_ROOM);
        preparedBookingSystem.registerClassroom(SECOND_ID, SECOND_ROOM);
        preparedBookingSystem.registerClassroom(THIRD_ID, THIRD_ROOM);

    }

    /**
     * Должен регистрировать комнату с помощью строкового идентификатора, чтобы потом по этому идентификатору можно было
     * зарегистрированную комнату вытащить
     * @throws Exception
     */
    @Test
    public void shouldRegisterClassroomsByID() throws Exception {
        final String ROOM_ID = "A1";
        Classroom classroom = mock(Classroom.class);

        bookingSystem.registerClassroom(ROOM_ID, classroom);

        Classroom registeredClassroom = bookingSystem.getRoomByID(ROOM_ID);

        assertSame(
                "Classroom wasn't registered by ID: " + ROOM_ID,
                classroom, registeredClassroom);

    }

    /**
     * Должен регистрировать несколько комнат с помощью строкового идинтификатора, чтобы потом по этим идентификаторам
     * вытаскивать соответствующие им комнаты
     * @throws Exception
     */
    @Test
    public void shouldRegisterMultipleClassroomsByID() throws Exception {
        final String FIRST_ID = "A2";
        final String SECOND_ID = "B2";

        final Classroom FIRST_ROOM = mock(Classroom.class);
        final Classroom SECOND_ROOM = mock(Classroom.class);
        bookingSystem.registerClassroom(FIRST_ID, FIRST_ROOM);
        bookingSystem.registerClassroom(SECOND_ID, SECOND_ROOM);

        assertSame(
                "Room with ID " + FIRST_ID + " was not registered\n",
                FIRST_ROOM,
                bookingSystem.getRoomByID(FIRST_ID)
        );
        assertSame(
                "Room with ID " + SECOND_ID + " was not registered\n",
                SECOND_ROOM,
                bookingSystem.getRoomByID(SECOND_ID)
        );
    }

    /**
     * Должен возвращать коллекцию идентификаторов зарегистрированных элементов
     * @throws Exception
     */
    @Test
    public void shouldReturnCollectionOfRegisteredClassroomsIDs() throws Exception {
        final String FIRST_ID = "A1";
        final String SECOND_ID = "B3";
        final String THIRD_ID = "G9";

        final Classroom FIRST_ROOM = mock(Classroom.class);
        final Classroom THIRD_ROOM = mock(Classroom.class);

        bookingSystem.registerClassroom(FIRST_ID, FIRST_ROOM);
        bookingSystem.registerClassroom(THIRD_ID, THIRD_ROOM);

        Collection<String> registeredRooms = bookingSystem.getRegisteredClassroomsIDs();

        assertTrue(
                "Collection of registered rooms IDs doesn't contain ID: " + FIRST_ID + " of registered room",
                registeredRooms.contains(FIRST_ID));
        assertFalse(
                "Collection of registered rooms IDs contains ID: " + SECOND_ID + " of not registered room",
                registeredRooms.contains(SECOND_ID));
        assertTrue(
                "Collection of registered rooms IDs doesn't contain ID: " + THIRD_ID + " of registered room",
                registeredRooms.contains(THIRD_ID));

    }

    /**
     * Должен возвращать коллекцию идентификаторов зарегистрированных элементов, размер которой
     * равен количеству добавленных комнат.
     * @throws Exception
     */
    @Test
    public void shouldReturnCollectionOfRegisteredClassroomsIDsThatHasExactAmountOfElementsAsNumberOfAddedRooms() throws Exception {
        final String FIRST_ID = "A1";
        final String THIRD_ID = "G9";

        final Classroom FIRST_ROOM = mock(Classroom.class);
        final Classroom THIRD_ROOM = mock(Classroom.class);

        final int NUMBER_OF_REGISTERED_ROOMS = 2;

        bookingSystem.registerClassroom(FIRST_ID, FIRST_ROOM);
        bookingSystem.registerClassroom(THIRD_ID, THIRD_ROOM);

        Collection<String> registeredRooms = bookingSystem.getRegisteredClassroomsIDs();

        assertFalse(
                "Collection of registered rooms IDs contain redundant elements",
                registeredRooms.size() > NUMBER_OF_REGISTERED_ROOMS
        );
        assertFalse(
                "Collection of registered rooms IDs contains lesser amount than added rooms",
                registeredRooms.size() < NUMBER_OF_REGISTERED_ROOMS
        );
    }

    /**
     * Передается ли запрос на бронирование внутреннему объекту {@link org.arthan.booking.enhanced.BookingData}
     * @throws Exception
     */
    @Test
    public void shouldDelegateBookingRequestToBookingDataObject() throws Exception {
        final BookingData mockData = mock(BookingData.class);

        final String roomId = "A1";
        final DayOfWeek day = DayOfWeek.WEDNESDAY;
        final int hour = 13;

        preparedBookingSystem.setBookingData(mockData);
        preparedBookingSystem.bookClassroom(roomId, day, hour);

        verify(mockData).bookClassroom(roomId, day, hour);
    }
}
