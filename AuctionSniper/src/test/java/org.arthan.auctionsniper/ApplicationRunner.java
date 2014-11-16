package org.arthan.auctionsniper;

/**
 * Created by arthan on 11/6/14.
 */

import org.arthan.auctonsniper.Main;
import org.arthan.auctonsniper.ui.MainWindow;

public class ApplicationRunner {
    public static final String SNIPER_ID = "sniper";
    public static final String SNIPER_PASSWORD = "sniper";
    public static final String XMPP_HOSTNAME = "localhost";

    private AuctionSniperDriver driver;

    public void startBiddingIn(final FakeAuctionServer auction) {
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
        driver.showsSniperStatus(MainWindow.STATUS_JOINING);
    }

    public void showSniperHasLostAuction() {
        driver.showsSniperStatus(MainWindow.STATUS_LOST);
    }


    public void stop() {
        if (driver != null) {
            driver.dispose();
        }
    }
}
