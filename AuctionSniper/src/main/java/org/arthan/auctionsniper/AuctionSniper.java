package org.arthan.auctionsniper;

import org.arthan.auctionsniper.SniperListener.PriceSource;
import org.arthan.auctionsniper.util.AuctionEventListener;

/**
 * Created by arthan on 1/18/15.
 */
public class AuctionSniper implements AuctionEventListener {

    private final SniperListener sniperListener;
    private final Auction auction;

    public AuctionSniper(Auction auction, SniperListener sniperListener) {
        this.sniperListener = sniperListener;
        this.auction = auction;
    }

    @Override
    public void auctionClosed() {
        sniperListener.sniperLost();
    }

    @Override
    public void currentPrice(int price, int increment, PriceSource priceSource) {
        auction.bid(price + increment);
        sniperListener.sniperBidding();
    }
}
