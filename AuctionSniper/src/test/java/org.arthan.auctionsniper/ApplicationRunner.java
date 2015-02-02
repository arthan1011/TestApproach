package org.arthan.auctionsniper;

/**
 * Created by Arthur Shamsiev on 11/6/14.
 * Using IntelliJ IDEA
 * Project - ${PROJECT_NAME}
 */
import org.arthan.auctionsniper.ui.MainWindow;

public class ApplicationRunner {
    public static final String SNIPER_ID = "sniper";
    public static final String SNIPER_PASSWORD = "sniper";
    public static final String XMPP_HOSTNAME = "localhost";
    public static final String SNIPER_XMPP_ID = "sniper@arthan-pc/Auction";

    private AuctionSniperDriver driver;
    private String itemID;

    public void startBiddingIn(final FakeAuctionServer auction) {
        itemID = auction.getItemID();
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Main.main(XMPP_HOSTNAME, SNIPER_ID, SNIPER_PASSWORD, auction.getItemID());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
        driver = new AuctionSniperDriver(1000);
        driver.showsSniperStatus("", 0, 0, MainWindow.STATUS_JOINING);
    }

    public void showSniperHasLostAuction(int lastPrice, int lastBid) {
        driver.showsSniperStatus(itemID, lastPrice, lastBid, MainWindow.STATUS_LOST);
    }


    public void stop() {
        if (driver != null) {
            driver.dispose();
        }
    }

    public void hasShownSniperIsBidding(int lastPrice, int lastBid) {
        driver.showsSniperStatus(
                itemID,
                lastPrice,
                lastBid,
                MainWindow.STATUS_BIDDING);
    }

    public void showSniperIsWinning(int winningBid) {
        driver.showsSniperStatus(itemID, winningBid, winningBid, MainWindow.STATUS_WINNING);
    }

    public void showSniperHasWonAuction(int lastPrice) {
        driver.showsSniperStatus(itemID, lastPrice, lastPrice, MainWindow.STATUS_WON);
    }
}
