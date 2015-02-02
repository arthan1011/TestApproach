package org.arthan.auctionsniper;

/**
 * Created by Arthur Shamsiev on 29.01.15.
 * Using IntelliJ IDEA
 * Project - testProject
 */
public class SniperSnapshot {
    final public String itemID;
    final public int lastPrice;
    final public int lastBid;
    final public SniperState sniperState;

    public SniperSnapshot(String itemID, int lastPrice, int lastBid, SniperState sniperState) {
        this.itemID = itemID;
        this.lastPrice = lastPrice;
        this.lastBid = lastBid;
        this.sniperState = sniperState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SniperSnapshot that = (SniperSnapshot) o;

        if (lastBid != that.lastBid) return false;
        if (lastPrice != that.lastPrice) return false;
        if (!itemID.equals(that.itemID)) return false;
        if (sniperState != that.sniperState) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = itemID.hashCode();
        result = 31 * result + lastPrice;
        result = 31 * result + lastBid;
        result = 31 * result + sniperState.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "SniperSnapshot{" +
                "itemID='" + itemID + '\'' +
                ", lastPrice=" + lastPrice +
                ", lastBid=" + lastBid +
                ", sniperState=" + sniperState +
                '}';
    }

    public static SniperSnapshot joining(String itemID) {
        return new SniperSnapshot(itemID, 0, 0, SniperState.JOINING);
    }

    public SniperSnapshot winning(int price) {
        return new SniperSnapshot(
                itemID,
                price,
                price,
                SniperState.WINNING
        );
    }

    public SniperSnapshot bidding(int price, int bid) {
        return new SniperSnapshot(
                itemID,
                price,
                bid,
                SniperState.BIDDING
        );
    }

    public SniperSnapshot won() {
        return new SniperSnapshot(
                itemID,
                lastPrice,
                lastPrice,
                SniperState.WON
        );
    }

    public SniperSnapshot lost() {
        return new SniperSnapshot(
                itemID,
                lastPrice,
                lastBid,
                SniperState.LOST
        );
    }
}
