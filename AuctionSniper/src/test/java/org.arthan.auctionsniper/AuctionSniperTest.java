package org.arthan.auctionsniper;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by arthan on 1/18/15.
 */

public class AuctionSniperTest {
    private Mockery context = new Mockery();
    private SniperListener sniperListener = context.mock(SniperListener.class);
    private Auction auction = context.mock(Auction.class);
    private AuctionSniper sniper = new AuctionSniper(auction, sniperListener);

    @Test
    public void reportsLostWhenAuctionCloses() throws Exception {
        context.checking(new Expectations() {{
            oneOf(sniperListener).sniperLost();
        }});

        sniper.auctionClosed();

        context.assertIsSatisfied();
    }

    @Test
    public void bidsHigherAndReportsBiddingWhenNewPriceArrives() throws Exception {
        final int price = 1011;
        final int increment = 25;

        context.checking(new Expectations() {{
            oneOf(auction).bid(price + increment);
            atLeast(1).of(sniperListener).sniperBidding();
        }});

        sniper.currentPrice(price, increment);

        context.assertIsSatisfied();
    }
}
