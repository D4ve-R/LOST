/**
 * MacPan version 0.1
 * SWE WS 21/22
 * @authors dave & janosch
 */

package lost.macpan;


import javax.swing.UIManager;

/**
 * Main entry point for app
 */
public class Main {
   public static void main(String [] args){
        // to invoke Swing thread safe

       try {
           // Set System L&F
           UIManager.setLookAndFeel(
                   UIManager.getSystemLookAndFeelClassName());
       }
       catch (Exception e) {
            e.printStackTrace();
       }
       java.awt.EventQueue.invokeLater(new Runnable(){
           public void run() {
               App app = new App();
           }
       });
   }
}
