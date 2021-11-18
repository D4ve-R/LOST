/*
 * MacPan version 0.1
 * SWE WS 21/22
 */

package lost.macpan;
import lost.macpan.panel.Intro;
import lost.macpan.panel.MainMenu;

import javax.swing.Timer;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends JFrame implements ActionListener{

    private static final int width = 950;
    private static final int height = 700;
    private static final int serializeId = 123456789;
    private Timer timer;

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

    @Override
    public void actionPerformed ( ActionEvent evt) {
        setContentPane(new MainMenu(this));
        revalidate();
        timer.stop();
    }

}
