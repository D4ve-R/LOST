/*
 * MacPan version 0.1
 * SWE WS 21/22
 */

package lost.macpan;
import lost.macpan.panel.Intro;

import javax.swing.JFrame;
import java.awt.Dimension;

public class App extends JFrame{

    private static final int width = 950;
    private static final int height = 700;

    public App(){
        setTitle("MacPan");
        setMinimumSize(new Dimension(width, height));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new Intro());
        setResizable(false);
        pack();
        setVisible(true);
    }
}
