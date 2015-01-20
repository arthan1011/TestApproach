package org.arthan.auctionsniper;

import org.junit.After;
import org.junit.Test;

/**
 * Created by arthan on 11/6/14.
 */

public class AuctionSniperEndToEndTest {
    private final FakeAuctionServer auction = new FakeAuctionServer("item_33292");
    private final ApplicationRunner application = new ApplicationRunner();

    @Test
    public void sniperJoinsAuctionUntilAuctionClosed() throws Exception {
        auction.startSellingItem();
        application.startBiddingIn(auction);
        auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID);
        auction.announceClosed();
        application.showSniperHasLostAuction();
    }

    @Test
    public void sniperMakesAHigherBidButLoses() throws Exception {
        auction.startSellingItem();

        application.startBiddingIn(auction);
        auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID);

        auction.reportPrice(1000, 98, "Other bidder");
        application.hasShownSniperIsBidding();

        auction.hasReceivedBid(1098, ApplicationRunner.SNIPER_XMPP_ID);

        auction.announceClosed();
        application.showSniperHasLostAuction();
    }

    @After
    public void stopAuction() throws Exception {
        auction.stop();
    }

    @After
    public void stopApplication() throws Exception {
        application.stop();
    }
}
