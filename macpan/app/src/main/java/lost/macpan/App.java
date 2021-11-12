/*
 * MacPan version 0.1
 * SWE WS 21/22
 */

package lost.macpan;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
    }
}
