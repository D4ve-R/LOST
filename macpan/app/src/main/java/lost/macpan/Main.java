/*
 * MacPan version 0.1
 * SWE WS 21/22
 * Authors:
 * Janosch Lentz
 * David Rechkemmer
 */

package lost.macpan;

/**
 * Main entry point for app
 */
public class Main {
   public static void main(String [] args){
        // to invoke Swing thread safe
       java.awt.EventQueue.invokeLater(new Runnable(){
           public void run() {
               App app = new App();
           }
       });
   }
}
