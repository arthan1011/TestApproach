package org.arthan.booking.enhanced;

/**
 * Created by artur.shamsiev on 08.10.2014.
 */
public class Classroom {

    private final int capacity;
    private boolean hasProjector = false;
    private boolean hasMicro = false;

    public Classroom(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }


    public void setProjector(boolean hasProjector) {
        this.hasProjector = hasProjector;
    }

    public boolean hasProjector() {
        return hasProjector;
    }

    public void setMicrophone(boolean hasMicrophone) {
        this.hasMicro = hasMicrophone;
    }

    public boolean hasMicrophone() {
        return hasMicro;
    }
}
