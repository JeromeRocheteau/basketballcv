package fr.icam.tests;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import fr.icam.gpio.Gpio;
import fr.icam.gpio.Pin;
import fr.icam.gpio.Mode;

public class StepMotor {

	private final int PUL = 78;
	private final int DIR = 77;
	private final int ENA = 51;

	private final int RES = 32;
	private final int NUM = 200;

	private Random random;
	
	private boolean dir;
	private int spd;
	private int cpt;
	private long timestamp;
	private boolean led;
	
	private Pin pulPin;
	private Pin dirPin;
	private Pin enaPin;
	private Pin ledPin;

	private boolean has_spent(long duration, long ts) {
	  return System.currentTimeMillis() - ts > duration;
	}

	public void setup() throws Exception {
		timestamp = System.currentTimeMillis();
		random = new Random(timestamp);
		pulPin = Gpio.open(PUL, Mode.OUTPUT);
		dirPin = Gpio.open(DIR, Mode.OUTPUT);
		enaPin = Gpio.open(ENA, Mode.OUTPUT);
		ledPin = Gpio.open(79, Mode.OUTPUT);
		dir = true;
		cpt = 0;
		led = false;
		this.doSpeed();
	}

	void doStep(int num, boolean dir) throws Exception {
	  for (int i = 0; i < num; i++) {
	    if (dir) {
	    	dirPin.high(); 
	    } else {
	    	dirPin.low();
	    }
	    enaPin.high();
	    pulPin.high();
	    busyWaitMicros(500);
	    pulPin.low();
	  }
	}

	private void toggle() {
		if (led) {
			led = false;
			ledPin.low();
		} else {
			led = true;
			ledPin.high();
		}
	}
	
	private void busyWaitMicros(long micros) {
        long waitUntil = System.nanoTime() + (micros);
        while (waitUntil > System.nanoTime()) {
            ;
        }
    }
	
	void doSpeed() {
		spd = random.nextInt(32) + 1;
		System.out.print("\nspeed = ");
		System.out.print(spd);
		System.out.print("\tts = ");
		System.out.print(System.currentTimeMillis());
		System.out.flush();
	}

	public void loop() throws Exception {
	  if (has_spent(1, timestamp)) {
	    timestamp = System.currentTimeMillis();
	    if (cpt + spd > RES * NUM) {
	      spd = (RES * NUM) - cpt;
	    } else if (cpt == RES * NUM) {
	      doSpeed();      
	      dir = !dir;
	      cpt = 0;
	    }
	    doStep(spd, dir);
	    toggle();
	    cpt += spd;
	  }
	}
	
	
    public static void main(String[] args) throws Exception {
    	int i = 0;
    	StepMotor app = new StepMotor();
    	app.setup();
    	while (true) {
    		app.loop();
    	}
    }
	
}
