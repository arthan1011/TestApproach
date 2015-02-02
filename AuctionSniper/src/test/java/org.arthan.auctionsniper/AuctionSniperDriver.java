package org.arthan.auctionsniper;

import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JTableDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;
import com.objogate.wl.swing.matcher.IterableComponentsMatcher;

import static com.objogate.wl.swing.matcher.JLabelTextMatcher.withLabelText;

/**
 * Created by Arthur Shamsiev on 11/6/14.
 * Using IntelliJ IDEA
 * Project - ${PROJECT_NAME}
 */
@SuppressWarnings("ALL")
public class AuctionSniperDriver extends JFrameDriver {
    public AuctionSniperDriver(int timeMillis) {
        super(new GesturePerformer(),
                JFrameDriver.topLevelFrame(
                        named(Main.MAIN_WINDOW_NAME),
                        showingOnScreen()),
                        new AWTEventQueueProber(timeMillis, 1000));
    }

    public void showsSniperStatus(final String itemID,
                                  final int lastPrice,
                                  final int lastBid,
                                  final String statusText) {
        new JTableDriver(this).hasRow(IterableComponentsMatcher.matching(
                withLabelText(itemID),
                withLabelText(String.valueOf(lastPrice)),
                withLabelText(String.valueOf(lastBid)),
                withLabelText(statusText)
        ));
    }
}
