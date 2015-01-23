package org.arthan.auctionsniper.util;

import org.arthan.auctionsniper.ApplicationRunner;
import org.arthan.auctionsniper.SniperListener.PriceSource;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

/**
 * Created by arthan on 12/3/14.
 */

public class AuctionMessageTranslatorTest {
    public static final Chat UNUSED_CHAT = null;
    private final Mockery context = new Mockery();
    private final AuctionEventListener listener = context.mock(AuctionEventListener.class);
    private final AuctionMessageTranslator translator = new AuctionMessageTranslator(ApplicationRunner.SNIPER_ID, listener);

    @Test
    public void notifiesAuctionClosedWhenCloseMessageReceived() throws Exception {
        context.checking(new Expectations() {{
            oneOf(listener).auctionClosed();
        }});

        Message closeMessage = new Message();
        final String CLOSE_METHOD_STRING = "SOLVersion: 1.1; Event: CLOSE;";
        closeMessage.setBody(CLOSE_METHOD_STRING);

        translator.processMessage(UNUSED_CHAT, closeMessage);

        context.assertIsSatisfied();
    }

    @Test
    public void notifiesBidDetailsWhenCurrentPriceMessageReceivedFromOtherBidder() {
        context.checking(new Expectations() {{
            final int CURRENT_PRICE = 192;
            final int INCREMENT = 7;
            exactly(1).of(listener).currentPrice(CURRENT_PRICE, INCREMENT, PriceSource.FromOtherBidder);
        }});

        Message priceMessage = new Message();
        final String PRICE_MESSAGE_STRING = "SOLVersion: 1.1; Event: PRICE; CurrentPrice: 192; Increment: 7; Bidder: Someone else";
        priceMessage.setBody(PRICE_MESSAGE_STRING);

        translator.processMessage(UNUSED_CHAT, priceMessage);
    }

    @Test
    public void notifiesBidDetailsWhenCurrentPriceMessageReceivedFromSniperItself() throws Exception {
        context.checking(new Expectations() {{
            final int CURRENT_PRICE = 193;
            final int INCREMENT = 8;
            exactly(1).of(listener).currentPrice(CURRENT_PRICE, INCREMENT, PriceSource.FromSniper);
        }});

        Message priceMessage = new Message();
        final String PRICE_MESSAGE_STRING = "SOLVersion: 1.1; Event: PRICE; CurrentPrice: 193; Increment: 8; Bidder: " + ApplicationRunner.SNIPER_ID + ";";
        priceMessage.setBody(PRICE_MESSAGE_STRING);

        translator.processMessage(UNUSED_CHAT, priceMessage);
    }
}
