package tk.blockbusterbpl.ledserver;

import de.pi3g.pi.ws2812.WS2812;

public class LEDController {
    public int length;
    LEDController(int length) {
        WS2812.get().init(length);
    }
    public void clear() {
        WS2812.get().clear();
    }
}
