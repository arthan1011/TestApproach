package org.arthan.auctionsniper.util;

import static org.arthan.auctionsniper.SniperListener.PriceSource;

/**
 * Created by arthan on 12/8/14.
 */
public interface AuctionEventListener {
    void auctionClosed();

    void currentPrice(int price, int increment, PriceSource priceSource);
}
