package org.arthan.auctionsniper;

import org.arthan.auctionsniper.SniperListener.PriceSource;
import org.arthan.auctionsniper.util.AuctionEventListener;

/**
 * Created by Arthur Shamsiev on 1/18/15.
 * Using IntelliJ IDEA
 * Project - ${PROJECT_NAME}
 */
public class AuctionSniper implements AuctionEventListener {
    private SniperSnapshot snapshot;
    private boolean isWinning = false;
    private final SniperListener sniperListener;
    private final Auction auction;

    public AuctionSniper(Auction auction,
                         SniperListener sniperListener,
                         String itemID) {
        this.sniperListener = sniperListener;
        this.auction = auction;
        this.snapshot = SniperSnapshot.joining(itemID);
    }

    @Override
    public void auctionClosed() {
        if (isWinning) {
            snapshot = snapshot.won();
        } else {
            snapshot = snapshot.lost();
        }
        sniperListener.sniperStateChanged(snapshot);
    }

    @Override
    public void currentPrice(int price, int increment, PriceSource priceSource) {
        isWinning = priceSource == PriceSource.FromSniper;
        if (isWinning) {
            snapshot = snapshot.winning(price);
        } else {
            final int bid = price + increment;
            auction.bid(bid);
            snapshot = snapshot.bidding(price, bid);
        }
        sniperListener.sniperStateChanged(snapshot);
    }
}
