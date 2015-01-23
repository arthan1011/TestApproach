package org.arthan.auctionsniper.util;

import com.google.common.collect.Maps;
import org.arthan.auctionsniper.SniperListener.PriceSource;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import java.util.Map;

/**
* Created by arthan on 12/3/14.
*/
public class AuctionMessageTranslator implements MessageListener {
    private AuctionEventListener listener;
    private String sniperID;

    public AuctionMessageTranslator(String sniperID, AuctionEventListener listener) {
        this.listener = listener;
        this.sniperID = sniperID;
    }

    @Override
    public void processMessage(Chat chat, Message message) {
        AuctionEvent auctionEvent = AuctionEvent.from(message.getBody());

        if (auctionEvent.type().equals("CLOSE")) {
            listener.auctionClosed();
        } else {
            listener.currentPrice(
                    auctionEvent.currentPrice(),
                    auctionEvent.increment(),
                    auctionEvent.isFrom(sniperID)
            );
        }
    }

    private static class AuctionEvent {
        private final Map<String, String> fields = Maps.newHashMap();

        public String type() {
            return get("Event");
        }

        public int currentPrice() {
            return getInt("CurrentPrice");
        }

        public int increment() {
            return getInt("Increment");
        }

        private String get(String fieldName) {
            return fields.get(fieldName);
        }

        private int getInt(String fieldName) {
            return Integer.parseInt(fields.get(fieldName));
        }

        public static AuctionEvent from(String message) {
            final AuctionEvent event = new AuctionEvent();
            for (String field : fieldsIn(message)) {
                event.addField(field);
            }
            return event;
        }

        private void addField(String field) {
            final String[] pair = field.split(":");
            fields.put(pair[0].trim(), pair[1].trim());
        }

        private static String[] fieldsIn(String message) {
            return message.split(";");
        }

        public PriceSource isFrom(String sniperID) {
            final boolean bidderIsSniper = sniperID.equals(get("Bidder"));
            if (bidderIsSniper) {
                return PriceSource.FromSniper;
            } else {
                return PriceSource.FromOtherBidder;
            }
        }
    }
}
