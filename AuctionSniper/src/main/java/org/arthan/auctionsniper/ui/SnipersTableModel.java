package org.arthan.auctionsniper.ui;

import org.arthan.auctionsniper.SniperSnapshot;
import org.arthan.auctionsniper.SniperState;

import javax.swing.table.AbstractTableModel;

/**
 * Created by Arthur Shamsiev on 29.01.15.
 * Using IntelliJ IDEA
 * Project - testProject
 */
public class SnipersTableModel extends AbstractTableModel {
    private final static SniperSnapshot STARTING_UP = new SniperSnapshot("", 0, 0, SniperState.JOINING);
    private String state = MainWindow.STATUS_JOINING;
    private SniperSnapshot sniperSnapshot = STARTING_UP;

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public int getColumnCount() {
        return Column.values().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (Column.at(columnIndex)) {
            case SNIPER_STATE: {
                return state;
            }
            case LAST_BID : {
                return sniperSnapshot.lastBid;
            }
            case LAST_PRICE: {
                return sniperSnapshot.lastPrice;
            }
            case ITEM_IDENTIFIER: {
                return sniperSnapshot.itemID;
            }
        }
        return state;
    }

    public void sniperStatusChanged(SniperSnapshot newSnapshot) {
        this.sniperSnapshot = newSnapshot;
        this.state = newSnapshot.sniperState.getText();
        fireTableRowsUpdated(0, 0);
    }
}
