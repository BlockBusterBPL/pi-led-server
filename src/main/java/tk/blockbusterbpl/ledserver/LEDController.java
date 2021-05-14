package tk.blockbusterbpl.ledserver;

import java.lang.Thread;
import com.diozero.ws281xj.rpiws281x.WS281x;
import java.awt.Color;

public class LEDController extends Thread {
    static WS281x led;
    static LEDState tempState;
    static boolean isOn;

    public static void init(int length) {
        try {

            WS281x led = new WS281x(18, 0, length);
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                public void run() {
                    led.close();
                }
            }));
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public static void setOnState(boolean on) {
        if (on) {
            setStateOn();
        } else {
            setStateOff();
        }
    }

    public static boolean getOnState() {
        return isOn;
    }

    public static void setStateOn() {
        setAllSingleStates(tempState);
        isOn = true;
    }

    public static void setStateOff() {
        if (isOn){
        tempState = getLedState();
        }
        isOn = false;
        clear();

    }

    public static WS281x instance() {
        return led;
    }

    public static void clear() {
        led.allOff();
    }

    public void run() {

    }

    public static void setState(int id, LEDState state) {
        led.setPixelColourHSB(id, state.hue, state.sat, state.val);
        led.render();
    }

    public static LEDState getLedState() {
        float hcpt = 0; // Hue Component
        float scpt = 0; // Saturation Component
        float bcpt = 0; // Brightness Component
        // float hsbvals[] = { (float) 0.5, (float) 0.5, (float) 0.5 };
        float hsbvals[] = {0, 0, 0};
        Color.RGBtoHSB( led.getRedComponent(0), led.getGreenComponent(0), led.getBlueComponent(0), hsbvals);
        hcpt = hsbvals[0];
        scpt = hsbvals[1];
        bcpt = hsbvals[2];
        return new LEDState(hcpt, scpt, bcpt);
    }

    public static float getLedHue() {
        return (float) (getLedState().hue * 360);
    }

    public static float getLedSat() {
        return (float) (getLedState().sat * 100);
    }

    public static float getLedVal() {
        return (float) (getLedState().val * 100);
    }

    public static void setLedHue(float hue) {
        setAllSingleStates(new LEDState(hue / 360, getLedSat() / 100, getLedVal() / 100));
    }

    public static void setLedSat(float sat) {
        setAllSingleStates(new LEDState(getLedHue() / 360, sat / 100, getLedVal() / 100));
    }

    public static void setLedVal(float val) {
        setAllSingleStates(new LEDState(getLedHue() / 360, getLedSat() / 100, val / 100));
    }

    public static void setAllStates(LEDState[] states) {
        for (int index = 0; index <= led.getNumPixels(); index++) {
            setState(index, states[index]);
        }

    }

    public static void setAllStates(LEDState[] states, int offset) {
        for (int index = offset; index <= led.getNumPixels(); index++) {
            setState(index, states[index]);
        }

    }

    public static void setAllSingleStates(LEDState state) {
        for (int index = 1; index <= led.getNumPixels(); index++) {
            setState(index, state);
        }
    }

    public static void setAllStates(LEDState[] states, int start, int end) {
        // for (int index = start; index <= end && index <= WS2812.get().numPixels();
        // index++) {
        // setState(index, states[index]);
        // }

    }
}
