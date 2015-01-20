package org.arthan.auctionsniper.util;

import com.google.common.collect.Maps;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import java.util.Map;

/**
* Created by arthan on 12/3/14.
*/
public class AuctionMessageTranslator implements MessageListener {
    private AuctionEventListener listener;

    public AuctionMessageTranslator() {
    }

    public AuctionMessageTranslator(AuctionEventListener listener) {
        this.listener = listener;
    }

    @Override
    public void processMessage(Chat chat, Message message) {
        Map<String, String> event = unpackEvent(message);
        if ("CLOSE".equals(event.get("Event"))) {
            listener.auctionClosed();
        } else {
            listener.currentPrice(
                    Integer.parseInt(event.get("CurrentPrice")),
                    Integer.parseInt(event.get("Increment"))
            );
        }
    }

    private Map<String, String> unpackEvent(Message message) {
        Map<String, String> resultEvent = Maps.newHashMap();

        for (String element : message.getBody().split(";")) {
            final String[] pair = element.split(":");
            resultEvent.put(pair[0].trim(), pair[1].trim());
        }
        return resultEvent;
    }
}
