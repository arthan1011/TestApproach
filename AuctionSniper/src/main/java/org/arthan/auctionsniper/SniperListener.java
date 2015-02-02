package org.arthan.auctionsniper;

import java.util.EventListener;

/**
 * Created by Arthur Shamsiev on 1/18/15.
 * Using IntelliJ IDEA
 * Project - ${PROJECT_NAME}
 */
public interface SniperListener extends EventListener {


    enum PriceSource {
        FromSniper,
        FromOtherBidder
    }

    void sniperStateChanged(SniperSnapshot sniperSnapshot);

}
