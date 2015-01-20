package org.arthan.auctionsniper.util;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by arthan on 12/3/14.
 */

public class AuctionMessageTranslatorTest {
    public static final Chat UNUSED_CHAT = null;
    private final Mockery context = new Mockery();
    private final AuctionEventListener listener = context.mock(AuctionEventListener.class);
    private final AuctionMessageTranslator translator = new AuctionMessageTranslator(listener);

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
    public void notifiesBidDetailsWhenCurrentPriceMessageReceived() {
        context.checking(new Expectations() {{
            final int CURRENT_PRICE = 192;
            final int INCREMENT = 7;
            exactly(1).of(listener).currentPrice(CURRENT_PRICE, INCREMENT);
        }});

        Message priceMessage = new Message();
        final String PRICE_MESSAGE_STRING = "SOLVersion: 1.1; Event: PRICE; CurrentPrice: 192; Increment: 7; Bidder: Someone else";
        priceMessage.setBody(PRICE_MESSAGE_STRING);

        translator.processMessage(UNUSED_CHAT, priceMessage);
    }
}
