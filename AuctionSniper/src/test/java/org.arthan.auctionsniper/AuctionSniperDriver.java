package org.arthan.auctionsniper;

import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JLabelDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;
import org.arthan.auctonsniper.Main;
import org.hamcrest.Matchers;

/**
 * Created by arthan on 11/6/14.
 */
public class AuctionSniperDriver extends JFrameDriver {
    public AuctionSniperDriver(int timeMillis) {
        super(new GesturePerformer(),
                JFrameDriver.topLevelFrame(
                        named(Main.MAIN_WINDOW_NAME),
                        showingOnScreen()),
                        new AWTEventQueueProber(timeMillis, 1000));
    }

    public void showsSniperStatus(final String statusText) {
        new JLabelDriver(
                this, named(Main.SNIPER_STATUS_NAME)).hasText(Matchers.equalTo(statusText));
    }
}
