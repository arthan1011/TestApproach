package org.arthan;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by artur.shamsiev on 30.09.2014.
 */
public class ArrayListTest {

    @Test
    public void testArrayListCapacity() {
        List<String> list = Lists.newArrayList();
        for (int i = 0; i < 40000; i++) {
            list.add(Integer.toString(i));
        }

        List<String> twoCapacityArrayList = new ArrayList<>();
        List<String> fourCapacityArrayList = new ArrayList<>(40000);

        Stopwatch timer = Stopwatch.createStarted();
        for (String s : list) {
            fourCapacityArrayList.add(s);
        }
        timer.stop();
        System.out.println("Four Capacity Array: " + timer.elapsed(TimeUnit.MICROSECONDS));

        timer = Stopwatch.createStarted();
        for (String s : list) {
            twoCapacityArrayList.add(s);
        }
        timer.stop();
        System.out.println("Two Capacity Array: " + timer.elapsed(TimeUnit.MICROSECONDS));


    }
}
