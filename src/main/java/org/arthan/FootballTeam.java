package org.arthan;

import com.google.common.base.Preconditions;

/**
 * Created by artur.shamsiev on 01.10.2014.
 */
public class FootballTeam implements Comparable<FootballTeam> {
    private int gamesWonCount;

    public FootballTeam(int gamesWonCount) {
        Preconditions.checkArgument(
                gamesWonCount >= 0,
                "Negative number: " + gamesWonCount + " cannot be passed to FootballTeam constructor");
        this.gamesWonCount = gamesWonCount;
    }

    public int getGamesWonCount() {
        return gamesWonCount;
    }

    @Override
    public int compareTo(FootballTeam o) {
        if (this == o) {
            return 0;
        }
        if (this.gamesWonCount > o.gamesWonCount) {
            return 1;
        }
        if (this.gamesWonCount < o.gamesWonCount) {
            return -1;
        }
        return 0;
    }
}
