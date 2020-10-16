package fr.icam.gpio;

import java.io.IOException;

public class Gpio {

    /**
     * Print out debug info?
     */
    public static boolean DEBUG = true;

    /**
     * Pin modes
     */
    public enum Mode {
        /**
         * Used to read the state of a pin
         */
        INPUT("in"),
        /**
         * Used to set the state of a pin
         */
        OUTPUT("out");

        private String id;

        Mode(String id) {
            this.id = id;
        }

        /**
         * ID used by Linux to know how to configure the pin
         * @return the ID used to configure the pin
         */
        public String id() {
            return this.id;
        }
    }

    /**
     * Opens a given GPIO pin in a given mode
     * @param number the GPIO pin number
     * @param mode the mode to configure
     * @return the pin
     * @see Pins
     * @throws IOException thrown if there is an error while opening the pin
     */
    public static Pin open(int number, Gpio.Mode mode) throws IOException {
        if(Sysfs.export(number)) {
            Sysfs.setMode(number, mode);
            return new Pin(number, mode);
        }
        throw new IOException("Failed to export pin "+number);
    }

    /**
     * Releases the given pin
     * @param pin the pin to release
     */
    public static void close(Pin pin) {
        Sysfs.unexport(pin.getNumber());
    }

	
}
