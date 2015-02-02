package org.arthan.auctionsniper.ui;

import org.arthan.auctionsniper.SniperSnapshot;
import org.arthan.auctionsniper.SniperState;
import org.hamcrest.Matcher;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.*;

public class SnipersTableModelTest {
    private final Mockery context = new Mockery();
    private TableModelListener listener = context.mock(TableModelListener.class);
    private final SnipersTableModel model = new SnipersTableModel();

    @Before
    public void setUp() throws Exception {
        model.addTableModelListener(listener);
    }

    @Test
    public void hasEnoughColumns() throws Exception {
        assertThat("Table model don't have enough columns", model.getColumnCount(), equalTo(Column.values().length));
    }

    @Test
    public void setSniperValuesInColumns() throws Exception {
        context.checking(new Expectations() {{
            oneOf(listener).tableChanged(with(aRowChangeEvent()));
        }});

        model.sniperStatusChanged(new SniperSnapshot("item id", 555, 667, SniperState.BIDDING));

        assertColumnEquals(Column.ITEM_IDENTIFIER, "item id");
        assertColumnEquals(Column.LAST_PRICE, 555);
        assertColumnEquals(Column.LAST_BID, 667);
        assertColumnEquals(Column.SNIPER_STATE, MainWindow.STATUS_BIDDING);
    }

    private void assertColumnEquals(Column columnIdentier, Object expected) {
        final int rowIndex = 0;
        final int columnIndex = columnIdentier.getOffset();
        assertEquals(expected, model.getValueAt(rowIndex, columnIndex));
    }

    private Matcher<TableModelEvent> aRowChangeEvent() {
        return samePropertyValuesAs(new TableModelEvent(model, 0));
    }
}