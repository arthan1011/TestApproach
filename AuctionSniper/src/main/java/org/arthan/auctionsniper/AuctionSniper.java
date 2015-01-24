package org.arthan.auctionsniper;

import org.arthan.auctionsniper.SniperListener.PriceSource;
import org.arthan.auctionsniper.util.AuctionEventListener;

/**
 * Created by Arthur Shamsiev on 1/18/15.
 * Using IntelliJ IDEA
 * Project - ${PROJECT_NAME}
 */
public class AuctionSniper implements AuctionEventListener {
    private boolean isWinning = false;
    private final SniperListener sniperListener;
    private final Auction auction;

    public AuctionSniper(Auction auction, SniperListener sniperListener) {
        this.sniperListener = sniperListener;
        this.auction = auction;
    }

    @Override
    public void auctionClosed() {
        if (isWinning) {
            sniperListener.sniperWon();
        } else {
            sniperListener.sniperLost();
        }
    }

    @Override
    public void currentPrice(int price, int increment, PriceSource priceSource) {
        switch (priceSource) {
            case FromOtherBidder:
                auction.bid(price + increment);
                sniperListener.sniperBidding();
                isWinning = false;
                break;
            case FromSniper:
                sniperListener.sniperWinning();
                isWinning = true;
                break;
        }

    }
}
