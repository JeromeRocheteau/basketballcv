package fr.icam.gpio;

import java.io.Closeable;

public class Pin implements Closeable {

    private int number;
    private Gpio.Mode mode;

    Pin(int number, Gpio.Mode mode) {
        this.number = number;
        this.mode = mode;
    }

    /**
     * Sets a pin to HIGH
     */
    public void high() {
        Sysfs.setState(number, true);
    }

    /**
     * Sets a pin to LOW
     */
    public void low() {
        Sysfs.setState(number, false);
    }

    /**
     * Queries the state of the pin
     * @return 'true' is the pin is set to HIGH, 'false' if set to LOW
     */
    public boolean isHigh() {
        return Sysfs.isHigh(number);
    }

    /**
     * Changes the mode of the pin
     * @param mode the pin mode
     */
    public void setMode(Gpio.Mode mode) {
        Sysfs.setMode(number, mode);
    }

    /**
     * GPIO ID of this pin
     * @return the ID
     */
    public int getNumber() {
        return number;
    }

    /**
     * Mode of this pin
     * @return the mode
     */
    public Gpio.Mode getMode() {
        return mode;
    }

    /**
     * Releases the pin to the OS
     */
    @Override
    public void close() {
    	Gpio.close(this);
    }
    
}

