/*
 * MacPan version 0.1
 * SWE WS 21/22
 * Authors:
 * Janosch Lentz
 * David Rechkemmer
 */

package lost.macpan;
import lost.macpan.panel.FoUND_Engine.FoUND_Engine;
import lost.macpan.panel.Intro;

import javax.swing.Timer;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * App Class that handles the application
 */
public class App extends JFrame implements ActionListener{
    private static final int width = 950;
    private static final int height = 700;
    private static final int serializeId = 123456789;
    private Timer timer;

    /**
     * Constructor method for App Class
     * sets the JFrame attributes
     */
    public App(){
        int delay = 5500; //müssen testen um auf 4 Sekunden zu kommen jz ca. 2 Sek

        timer = new Timer(delay, this);

        setTitle("MacPan");
        setMinimumSize(new Dimension(width, height));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new Intro());
        setResizable(false);
        pack();
        setVisible(true);

        timer.start();
    }

    /**
     * handles the timeout to change the panels when the app starts
     * overriden method from ActionListener
     * @param evt don't need to be set
     */
    @Override
    public void actionPerformed ( ActionEvent evt) {
        FoUND_Engine gw = null;
        try {
            gw = new FoUND_Engine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setContentPane(gw);
        pack();
        gw.startTehGaem();
        revalidate();
        timer.stop();
    }

}
