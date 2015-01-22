package org.arthan.auctionsniper.ui;

import org.arthan.auctionsniper.Main;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Created by arthan on 11/9/14.
 */
public class MainWindow extends JFrame {

    public static final String STATUS_JOINING = "Join";
    public static final String STATUS_LOST = "Lost";
    public static final String STATUS_BIDDING = "Bidding";
    private final JLabel sniperStatus = createLabel(STATUS_JOINING);

    private JLabel createLabel(String initialText) {
        JLabel label = new JLabel(initialText);
        label.setName(Main.SNIPER_STATUS_NAME);
        label.setBorder(new LineBorder(Color.BLACK));
        return label;
    }

    public MainWindow() {
        super("Auction Sniper");
        setName(Main.MAIN_WINDOW_NAME);
        setSize(160, 20);
        setLocation(700, 500);
        add(sniperStatus);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void showStatus(String status) {
        sniperStatus.setText(status);
    }
}
