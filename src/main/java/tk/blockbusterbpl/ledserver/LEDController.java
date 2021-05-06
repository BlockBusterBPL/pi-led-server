package tk.blockbusterbpl.ledserver;

import java.lang.Thread;
import tk.blockbusterbpl.ledserver.LEDState;
import com.diozero.*;
import com.diozero.devices.LED;
import com.diozero.ws281xj.rpiws281x.WS281x;

public class LEDController extends Thread {
    WS281x led;

    LEDController(int length) {
        WS281x led = new WS281x(12, 0, length);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable(){
            public void run() {
                led.close();
            }
        }));
    }
    public void clear() {
        led.allOff();
    }
    public void run() {

    }
    public void setState(int id, LEDState state) {
        led.setPixelColourRGB(id, state.red, state.green, state.blue);
        led.render();
    }
    public void setAllStates(LEDState[] states) {
        for (int index = 0; index <= led.getNumPixels(); index++) {
            setState(index, states[index]);
        }

    }
    public void setAllStates(LEDState[] states, int offset) {
        for (int index = offset; index <= led.getNumPixels(); index++) {
            setState(index, states[index]);
        }

    }
    public void setAllStates(LEDState[] states, int start, int end) {
        //for (int index = start; index <= end && index <= WS2812.get().numPixels(); index++) {
            //setState(index, states[index]);
        //}

    }
}
