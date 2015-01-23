package org.arthan.auctionsniper;

import java.util.EventListener;

/**
 * Created by Arthur Shamsiev on 1/18/15.
 */
public interface SniperListener extends EventListener {
    enum PriceSource {
        FromSniper,
        FromOtherBidder
    }

    void sniperLost();

    void sniperBidding();
}
