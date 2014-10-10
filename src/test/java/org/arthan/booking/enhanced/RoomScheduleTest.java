package org.arthan.booking.enhanced;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

/**
 * Created by artur.shamsiev on 09.10.2014.
 */
public class RoomScheduleTest {

    /**
     * Должен найти {@link DaySchedule} для определенного {@link DayOfWeek} и вызвать метод
     * {@link org.arthan.booking.enhanced.DaySchedule#book(int)} для найденного дневного расписания
     * передав методу book час
     *
     * @throws Exception
     */
    @Test
    public void shouldBookSpecifiedHourForSpecifiedDay() throws Exception {
        final DayOfWeek day = DayOfWeek.WEDNESDAY;
        final int hour = 15;

        DaySchedule daySchedMock = mock(DaySchedule.class);

        RoomSchedule roomSchedule = spy(new RoomSchedule());
        doReturn(daySchedMock).when(roomSchedule).getScheduleForDay(day);

        roomSchedule.book(day, hour);

        verify(roomSchedule).getScheduleForDay(day);
        verify(daySchedMock).book(hour);
    }

    /**
     * Должна быть возможность извлекать сохраненных дневные расписания для определенного дня
     * @throws Exception
     */
    @Test
    public void shouldGetScheduleForSpecifiedDay() throws Exception {
        DayOfWeek dayA = DayOfWeek.WEDNESDAY;
        DaySchedule dayScheduleA = mock(DaySchedule.class);
        DayOfWeek dayB = DayOfWeek.MONDAY;
        DaySchedule dayScheduleB = mock(DaySchedule.class);

        RoomSchedule roomSchedule = new RoomSchedule();
        roomSchedule.setDaySchedule(dayA, dayScheduleA);
        roomSchedule.setDaySchedule(dayB, dayScheduleB);

        DaySchedule existingDaySchedule = roomSchedule.getScheduleForDay(dayB);
        Assert.assertSame(
                "DaySchedule object wasn't properly retrieved for day: " + dayB,
                dayScheduleB,
                existingDaySchedule
        );
    }

    /**
     * Должен создавать новый объект дневного расписания {@link DaySchedule} и ассоциировать его с днем,
     * если день, переданный как параметр функции {@link RoomSchedule#book(DayOfWeek, int)} не имеет ассоциированного
     * с ним расписания
     * @throws Exception
     */
    @Test
    public void shouldNewDayScheduleForDayThatHasNotAssociatedDaySchedule() throws Exception {
        DayOfWeek dayA = DayOfWeek.WEDNESDAY;
        DaySchedule dayScheduleA = mock(DaySchedule.class);
        RoomSchedule roomSchedule = new RoomSchedule();

        // todo
    }

    /**
     * Не должен содержать никаких дней, ассоциированных с определенными дневными расписания по умолчанию
     * @throws Exception
     */
    @Test
    public void shouldNotHaveAnyDaysAssociatedWithDaySchedulesByDefault() throws Exception {
        // todo
    }
}
