package fr.icam.tests;

import java.util.Random;

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
	
	private Pin pulPin;
	private Pin dirPin;
	private Pin enaPin;

	private boolean has_spent(long duration, long ts) {
	  return System.currentTimeMillis() - ts > duration;
	}

	public void setup() throws Exception {
		timestamp = System.currentTimeMillis();
		random = new Random(timestamp);
		pulPin = Gpio.open(PUL, Mode.OUTPUT);
		dirPin = Gpio.open(DIR, Mode.OUTPUT);
		enaPin = Gpio.open(ENA, Mode.OUTPUT);
		dir = true;
		cpt = 0;
		this.doSpeed();
	}

	void doStep(int num, boolean dir) {
	  for (int i = 0; i < num; i++) {
	    // digitalWrite(DIR,dir ? HIGH : LOW);
	    if (dir) {
	    	dirPin.high(); 
	    } else {
	    	dirPin.low();
	    }
	    // digitalWrite(ENA,HIGH);
	    enaPin.high();
	    // digitalWrite(PUL,HIGH);
	    pulPin.high();
	    this.busyWaitMillis(5);
	    // digitalWrite(PUL,LOW);
	    pulPin.low();
	    this.busyWaitMillis(5);
	  }
	}

	private void busyWaitMillis(long micros) {
        long waitUntil = System.currentTimeMillis() + (micros);
        while (waitUntil > System.currentTimeMillis()) {
            ;
        }
    }
	
	void doSpeed() {
	  spd = random.nextInt(32) + 1;
	  System.out.print("speed = ");
	  System.out.print(spd);
	  System.out.print("\tcounter = ");
	  System.out.println(cpt);
	}

	public void loop() {
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
