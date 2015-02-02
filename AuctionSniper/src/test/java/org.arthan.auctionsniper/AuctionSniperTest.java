package org.arthan.auctionsniper;

import org.arthan.auctionsniper.SniperListener.PriceSource;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.States;
import org.junit.Test;

import static org.arthan.auctionsniper.Main.ITEM_ID;
import static org.arthan.auctionsniper.SniperState.*;

/**
 * Created by Arthur Shamsiev on 1/18/15.
 * Using IntelliJ IDEA
 * Project - ${PROJECT_NAME}
 */
public class AuctionSniperTest {
    private Mockery context = new Mockery();
    private SniperListener sniperListener = context.mock(SniperListener.class);
    private Auction auction = context.mock(Auction.class);
    private AuctionSniper sniper = new AuctionSniper(auction, sniperListener, ITEM_ID);
    private final States sniperState = context.states("sniper");

    @Test
    public void reportsLostWhenAuctionCloses() throws Exception {
        context.checking(new Expectations() {{
            oneOf(sniperListener).sniperStateChanged(with(aSniperThatIs(LOST)));
        }});

        sniper.auctionClosed();

        context.assertIsSatisfied();
    }

    @Test
    public void bidsHigherAndReportsBiddingWhenNewPriceArrives() throws Exception {

        context.checking(new Expectations() {{
            oneOf(auction).bid(1036);
            atLeast(1).of(sniperListener).sniperStateChanged(new SniperSnapshot(ITEM_ID, 1011, 1036, BIDDING));
        }});

        sniper.currentPrice(1011, 25, PriceSource.FromOtherBidder);

        context.assertIsSatisfied();
    }

    @Test
    public void reportsIsWinningWhenCurrentPriceComesFromSniper() throws Exception {
        context.checking(new Expectations() {{
            ignoring(auction);
            allowing(sniperListener).sniperStateChanged(with(aSniperThatIs(BIDDING)));
                then(sniperState.is("bidding"));
            atLeast(1).of(sniperListener).sniperStateChanged(new SniperSnapshot(ITEM_ID, 1011, 1011, WINNING));
                when(sniperState.is("bidding"));
        }});

        sniper.currentPrice(1000, 11, PriceSource.FromOtherBidder);
        sniper.currentPrice(1011, 25, PriceSource.FromSniper);
        context.assertIsSatisfied();
    }

    @Test
    public void reportsLostIfAuctionClosesWhenBidding() throws Exception {
        context.checking(new Expectations() {{
            ignoring(auction);
            allowing(sniperListener).sniperStateChanged(with(aSniperThatIs(BIDDING)));
                then(sniperState.is("bidding"));
            atLeast(1).of(sniperListener).sniperStateChanged(with(aSniperThatIs(LOST)));
                when(sniperState.is("bidding"));
        }});

        sniper.currentPrice(193, 21, PriceSource.FromOtherBidder);
        sniper.auctionClosed();
        context.assertIsSatisfied();
    }

    private Matcher<SniperSnapshot> aSniperThatIs(SniperState sniperState) {
        return new FeatureMatcher<SniperSnapshot, SniperState>(
                Matchers.equalTo(sniperState),
                "sniper that is",
                " was") {
            @Override
            protected SniperState featureValueOf(SniperSnapshot actual) {
                return actual.sniperState;
            }
        };
    }

    @Test
    public void reportsWonIfAuctionClosesWhenWinning() throws Exception {
        context.checking(new Expectations() {{
            ignoring(auction);
            allowing(sniperListener).sniperStateChanged(with(aSniperThatIs(WINNING)));
                then(sniperState.is("winning"));
            atLeast(1).of(sniperListener).sniperStateChanged(with(aSniperThatIs(WON)));
                when(sniperState.is("winning"));
        }});

        sniper.currentPrice(193, 21, PriceSource.FromSniper);
        sniper.auctionClosed();
        context.assertIsSatisfied();
    }
}
