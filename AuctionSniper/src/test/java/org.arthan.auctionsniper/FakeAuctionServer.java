package org.arthan.auctionsniper;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;
import org.junit.Assert;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.isNotNull;

/**
 * Created by arthan on 11/8/14.
 */
public class FakeAuctionServer {
    public static final String ITEM_ID_AS_LOGIN = "auction-%s";
    public static final String AUCTION_RESOURCE = "Auction";
    public static final String XMPP_HOSTNAME = "localhost";
    public static final String AUCTION_PASSWORD = "auction";

    private final String itemID;
    private final XMPPConnection connection;
    private Chat currentChat;
    private final SingleMessageListener messageListener = new SingleMessageListener();

    public FakeAuctionServer(String itemID) {
        this.itemID = itemID;
        connection = new XMPPConnection(XMPP_HOSTNAME);
    }

    public void startSellingItem() throws XMPPException{
        connection.connect();
        connection.login(String.format(ITEM_ID_AS_LOGIN, itemID), AUCTION_PASSWORD, AUCTION_RESOURCE);
        connection.getChatManager().addChatListener(
                new ChatManagerListener() {
                    @Override
                    public void chatCreated(Chat chat, boolean b) {
                        currentChat = chat;
                        chat.addMessageListener(messageListener);
                    }
                }
        );
    }

    public void announceClosed() throws XMPPException {
        currentChat.sendMessage(new Message());
    }

    public void hasReceivedJoinRequestFromSniper() throws InterruptedException {
        messageListener.receivesAMessage();
    }

    public String getItemID() {
        return itemID;
    }

    public void stop() {
        connection.disconnect();
    }

    public class SingleMessageListener implements MessageListener {

        private final ArrayBlockingQueue<Message> messages = new ArrayBlockingQueue<>(1);

        @Override
        public void processMessage(Chat chat, Message message) {
            messages.add(message);
        }

        public void receivesAMessage() throws InterruptedException {
            Assert.assertThat("Join message is not received", messages.poll(5, TimeUnit.SECONDS), is(notNullValue()));
        }
    }
}
