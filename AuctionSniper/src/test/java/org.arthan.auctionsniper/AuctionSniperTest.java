package org.arthan.auctionsniper;

import org.arthan.auctionsniper.SniperListener.PriceSource;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.States;
import org.junit.Test;

/**
 * Created by Arthur Shamsiev on 1/18/15.
 * Using IntelliJ IDEA
 * Project - ${PROJECT_NAME}
 */
public class AuctionSniperTest {
    private Mockery context = new Mockery();
    private SniperListener sniperListener = context.mock(SniperListener.class);
    private Auction auction = context.mock(Auction.class);
    private AuctionSniper sniper = new AuctionSniper(auction, sniperListener);
    private final States sniperState = context.states("sniper");

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

        sniper.currentPrice(price, increment, PriceSource.FromOtherBidder);

        context.assertIsSatisfied();
    }

    @Test
    public void reportsIsWinningWhenCurrentPriceComesFromSniper() throws Exception {
        final int price = 1011;
        final int increment = 25;

        context.checking(new Expectations() {{
            atLeast(1).of(sniperListener).sniperWinning();
        }});

        sniper.currentPrice(price, increment, PriceSource.FromSniper);
        context.assertIsSatisfied();
    }

    @Test
    public void reportsLostIfAuctionClosesWhenBidding() throws Exception {
        context.checking(new Expectations() {{
            ignoring(auction);
            allowing(sniperListener).sniperBidding();
                then(sniperState.is("bidding"));
            atLeast(1).of(sniperListener).sniperLost();
                when(sniperState.is("bidding"));
        }});

        sniper.currentPrice(193, 21, PriceSource.FromOtherBidder);
        sniper.auctionClosed();
        context.assertIsSatisfied();
    }

    @Test
    public void reportsWonIfAuctionClosesWhenWinning() throws Exception {
        context.checking(new Expectations() {{
            ignoring(auction);
            allowing(sniperListener).sniperWinning();
                then(sniperState.is("winning"));
            atLeast(1).of(sniperListener).sniperWon();
                when(sniperState.is("winning"));
        }});

        sniper.currentPrice(193, 21, PriceSource.FromSniper);
        sniper.auctionClosed();
        context.assertIsSatisfied();
    }
}
