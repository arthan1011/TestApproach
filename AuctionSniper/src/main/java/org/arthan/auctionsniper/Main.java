package org.arthan.auctionsniper;

import org.arthan.auctionsniper.ui.MainWindow;
import org.arthan.auctionsniper.util.AuctionMessageTranslator;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by arthan on 11/6/14.
 */
public class Main {
    public static final String BID_COMMAND_FORMAT = "SOLVersion: 1.1; Command: BID; Price %d";
    public static final String JOIN_COMMAND_FORMAT = "SOLVersion: 1.1; Command: JOIN";
    @SuppressWarnings("unused") private Chat notToBeCGd;

    public static final String MAIN_WINDOW_NAME = "Auction Sniper Main";
    public static final String SNIPER_STATUS_NAME = "Sniper Status";

    public static final String AUCTION_RESOURCE = "Auction";
    public static final String ITEM_ID_AS_LOGIN = "auction-%s";
    public static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%s/" + AUCTION_RESOURCE;

    public static final int ARG_HOSTNAME = 0;
    public static final int ARG_USERNAME = 1;
    public static final int ARG_PASSWORD = 2;
    public static final int ARG_ITEM_ID  = 3;

    private MainWindow ui;

    public Main() throws Exception {
        startUserInterface();
    }

    private void startUserInterface() throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                ui = new MainWindow();
            }
        });
    }

    public static void main(String... args) throws Exception {
        Main main = new Main();

        main.joinAuction(
                connection(args[ARG_HOSTNAME], args[ARG_USERNAME], args[ARG_PASSWORD]),
                args[ARG_ITEM_ID]
        );
    }

    private void joinAuction(XMPPConnection connection, String itemID) throws XMPPException {
        final Chat chat = connection.getChatManager().createChat(
                auctionID(itemID, connection), null);

        Auction auction = new XMPPAuction(chat);

        disconnectWhenUICloses(connection);
        this.notToBeCGd = chat;

        chat.addMessageListener(new AuctionMessageTranslator(new AuctionSniper(auction, new SniperStateDisplayer())));

        auction.join();
    }

    private void disconnectWhenUICloses(final XMPPConnection connection) {
        ui.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                connection.disconnect();
            }
        });
    }


    private static String auctionID(String itemID, XMPPConnection connection) {
        return String.format(AUCTION_ID_FORMAT, itemID, connection.getServiceName());
    }

    private static XMPPConnection connection(String hostname, String username, String password) throws XMPPException {
        XMPPConnection connection = new XMPPConnection(hostname);
        connection.connect();
        connection.login(username, password, AUCTION_RESOURCE);

        return connection;
    }

    private class XMPPAuction implements Auction {

        private Chat chat;

        public XMPPAuction(Chat chat) {
            this.chat = chat;
        }

        @Override
        public void bid(int newPrice) {
            sendMessage(String.format(BID_COMMAND_FORMAT, newPrice));
        }

        @Override
        public void join() {
            sendMessage(JOIN_COMMAND_FORMAT);
        }

        private void sendMessage(String message) {
            try {
                chat.sendMessage(message);
            } catch (XMPPException e) {
                e.printStackTrace();
            }
        }
    }

    private class SniperStateDisplayer implements SniperListener {
        @Override
        public void sniperLost() {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    ui.showStatus(MainWindow.STATUS_LOST);
                }
            });
        }

        @Override
        public void sniperBidding() {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    ui.showStatus(MainWindow.STATUS_BIDDING);
                }
            });
        }
    }
}
