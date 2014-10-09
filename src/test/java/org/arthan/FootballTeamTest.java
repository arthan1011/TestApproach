package org.arthan;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junitparams.JUnitParamsRunner.*;
import static org.junit.Assert.*;

/**
 * Created by artur.shamsiev on 01.10.2014.
 */
@RunWith(JUnitParamsRunner.class)
public class FootballTeamTest {
    private final int ANY_NUMBER = 231;


    private Object[] parametersForNegativeArgumentConstructor() {
        return $(
                "-1", "-54", "-9009"
        );
    }
    private Object[] parametersForComparison() {
        return $(
                $(49, 80, -1),
                $(1, 0, 1),
                $(778, 777, 1),
                $(0, 0, 0),
                $(5, 5, 0)
        );
    }
    /**
     * Конструктор класса {@link org.arthan.FootballTeam} должен сохранить переданный ему параметр,
     * значение которого можно будет узнать с помощью метода {@link org.arthan.FootballTeam#gamesWonCount}.
     * @param inputGames
     * @param setGames
     * @throws Exception
     */
    @Test
    @Parameters({
            "3, 3",
            "9902, 9902",
            "0, 0"
    })
    public void testFootballTeamConstructor(int inputGames, int setGames) throws Exception {
        FootballTeam team = new FootballTeam(inputGames);
        assertEquals("3 won games was passed to constructor, but " + team.getGamesWonCount() +
                        " won games was returned by won games getter method",
                team.getGamesWonCount(), setGames
        );

    }

    /**
     * Конструктор класса {@link org.arthan.FootballTeam} должен бросить {@link IllegalArgumentException}
     * если ему в виде параметра передано отрицательное число
     * @param inputGames
     * @throws Exception
     */
    @Test
    @Parameters(method = "parametersForNegativeArgumentConstructor")
    public void testFootballTeamConstructor_NegativeGamesCount(int inputGames) throws Exception {
        try {
            FootballTeam team = new FootballTeam(inputGames);
            fail("IllegalArgumentException expected when negative values are passed to FootballTeam constructor");
        } catch (IllegalArgumentException e) {
            assertEquals("IllegalArgumentException expected when negative values are passed to FootballTeam constructor " +
                    "should be like\n\"Negative number: ${inputValue} cannot be passed to FootballTeam constructor\"",
                    e.getMessage(), "Negative number: " + inputGames + " cannot be passed to FootballTeam constructor");
        }

    }

    /**
     * Класс {@link org.arthan.FootballTeam} должен имплементировать интерфейс {@link java.lang.Comparable}
     * @throws Exception
     */
    @Test
    public void shouldImplementComparable() throws Exception {
        final FootballTeam team = new FootballTeam(ANY_NUMBER);
        assertTrue("FootballTeam class doesn't implement Comparable interface", team instanceof Comparable<?>);
    }

    /**
     * Класс {@link org.arthan.FootballTeam} должен поддерживать natural ordering comparison.
     * @param firstTeamWonGames
     * @param secondTeamWonGames
     * @param comparisonResult
     */
    @Test
    @Parameters(method = "parametersForComparison")
    public void instancesOfFootballTeamClassWithMoreWonGamesShouldBeGreaterInNaturalOrdering(
            int firstTeamWonGames,
            int secondTeamWonGames,
            int comparisonResult) {
        final FootballTeam firstTeam = new FootballTeam(firstTeamWonGames);
        final FootballTeam secondTeam = new FootballTeam(secondTeamWonGames);

        assertEquals("Natural ordering comparison for instances of class FootballTeam doesn't work properly\n" +
                        "first team games parameter = " + firstTeamWonGames + "\n" +
                        "second team games parameter = " + secondTeamWonGames,
                firstTeam.compareTo(secondTeam),
                comparisonResult);
    }


}
