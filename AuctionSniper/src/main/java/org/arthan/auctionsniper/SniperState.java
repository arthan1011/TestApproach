package org.arthan.auctionsniper;

/**
 * Created by Arthur Shamsiev on 31.01.15.
 * Using IntelliJ IDEA
 * Project - testProject
 */
public enum SniperState {
    JOINING("Joining"),
    BIDDING("Bidding"),
    WINNING("Winning"),
    LOST("Lost"),
    WON("Won");

    private String text;

    SniperState(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
