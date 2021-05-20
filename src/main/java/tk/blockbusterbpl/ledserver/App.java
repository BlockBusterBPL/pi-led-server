package tk.blockbusterbpl.ledserver;

// import tk.blockbusterbpl.ledserver.LEDController;
/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        WebServer server = new WebServer();
        LEDController.init(300);
        // LEDController strip = new LEDController(300);
        // strip.clear();
    }
}
