package org.arthan.auctionsniper;

import java.util.EventListener;

/**
 * Created by arthan on 1/18/15.
 */
public interface SniperListener extends EventListener {
    void sniperLost();

    void sniperBidding();
}
