package org.arthan.auctionsniper.ui;

/**
 * Created by Arthur Shamsiev on 29.01.15.
 * Using IntelliJ IDEA
 * Project - testProject
 */
public enum Column {
    ITEM_IDENTIFIER(0),
    LAST_PRICE(1),
    LAST_BID(2),
    SNIPER_STATE(3);

    private int offset;

    public int getOffset() {
        return offset;
    }

    Column(int offset) {
        this.offset = offset;
    }

    public static Column at(int offset) {
        return values()[offset];
    }
}
