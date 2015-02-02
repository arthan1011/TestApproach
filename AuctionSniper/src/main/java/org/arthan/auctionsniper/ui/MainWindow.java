package org.arthan.auctionsniper.ui;

import org.arthan.auctionsniper.Main;
import org.arthan.auctionsniper.SniperSnapshot;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Arthur Shamsiev on 11/9/14.
 * Using IntelliJ IDEA
 * Project - ${PROJECT_NAME}
 */
public class MainWindow extends JFrame {

    public static final String STATUS_JOINING = "Join";
    public static final String STATUS_LOST = "Lost";
    public static final String STATUS_BIDDING = "Bidding";
    public static final String STATUS_WINNING = "Winning";
    public static final String STATUS_WON = "Won";
    private final SnipersTableModel snipers = new SnipersTableModel();

    public MainWindow() {
        super("Auction Sniper");
        setName(Main.MAIN_WINDOW_NAME);
        fillContentPane(makeSnipersTable());
        pack();
        setSize(300, 100);
        setLocation(700, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void fillContentPane(JTable snipersTable) {
        final Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        contentPane.add(new JScrollPane(snipersTable), BorderLayout.CENTER);
    }

    private JTable makeSnipersTable() {
        final JTable snipersTable = new JTable(snipers);
        snipersTable.setName(Main.SNIPERS_TABLE_NAME);
        return snipersTable;
    }

    public void showStatusText(SniperSnapshot sniperSnapshot) {
        snipers.sniperStatusChanged(sniperSnapshot);
    }
}
