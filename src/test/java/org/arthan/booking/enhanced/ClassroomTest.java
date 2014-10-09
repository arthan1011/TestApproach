package org.arthan.booking.enhanced;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by artur.shamsiev on 08.10.2014.
 */
public class ClassroomTest {

    public static final int ANY_CAPACITY = 38;

    /**
     * У класса должен быть конструктор, который принимает значение вместимости комнаты
     * @throws Exception
     */
    @Test
    public void shouldHaveConstructorWithCapacity() throws Exception {
        final int CAPACITY = 25;
        Classroom room = new Classroom(CAPACITY);

        assertEquals(
                "Capacity value " + CAPACITY + " passed to constructor was not preserved",
                CAPACITY, room.getCapacity());
    }

    /**
     * В комнате может быть проектор а может и не быть. Должен быть способ обозначить его наличие либо отсутствие
     * @throws Exception
     */
    @Test
    public void mayContainProjector() throws Exception {
        Classroom roomWithProjector = new Classroom(ANY_CAPACITY);
        Classroom roomWithoutProjector = new Classroom(ANY_CAPACITY);

        roomWithProjector.setProjector(true);
        roomWithoutProjector.setProjector(false);

        assertFalse(
                "The room for which we identified the lack of projector have a projector",
                roomWithoutProjector.hasProjector());
        assertTrue(
                "The room for which we identified the presence of projector haven't a projector",
                roomWithProjector.hasProjector());
    }

    /**
     * По умолчанию в комнате не должно быть проектора
     * @throws Exception
     */
    @Test
    public void shouldNotContainProjectorByDefault() throws Exception {
        Classroom room = new Classroom(ANY_CAPACITY);
        assertFalse(
                "Classroom has a projector by default. It shouldn't.",
                room.hasProjector());
    }

    /**
     * В комнате может быть микрофон а может и не быть. Должен быть способ обозначить его наличие или отсутствие
     * @throws Exception
     */
    @Test
    public void mayContainMicrophone() throws Exception {
        Classroom roomWithMicro = new Classroom(ANY_CAPACITY);
        Classroom roomWithoutMicro = new Classroom(ANY_CAPACITY);

        roomWithMicro.setMicrophone(true);
        roomWithoutMicro.setMicrophone(false);

        assertFalse(
                "The room for which we identified the lack of a microphone have a microphone",
                roomWithoutMicro.hasMicrophone()
        );
        assertTrue(
                "The room for which we identified the presence of a microphone haven't a microphone",
                roomWithMicro.hasMicrophone()
        );
    }

    /**
     * По умолчанию в комнате не должно быть проектора
     * @throws Exception
     */
    @Test
    public void shouldNotContainMicrophoneByDefault() throws Exception {
        Classroom room = new Classroom(ANY_CAPACITY);
        assertFalse(
                "Classroom has a microphone by default. It shouldn't.",
                room.hasMicrophone());
    }
}
