package fr.icam.gpio;

import java.io.IOException;

public class LedBlinking {

    public void testLed() throws IOException {
        try {
        	Pin pin = Gpio.open(79, Gpio.Mode.OUTPUT);
            for (int i = 0; i < 60; i++) {
                if(i % 2 == 0) {
                    pin.high();
                } else {
                    pin.low();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            pin.close();
        } catch (IOException e) {
            throw e;
        }
    }
    
    public static void main(String[] args) throws Exception {
    	LedBlinking app = new LedBlinking();
    	app.testLed();
    }
	
}
