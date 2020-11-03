package fr.icam.gpio;

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