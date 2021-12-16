/*
 * MacPan version 0.1
 * SWE WS 21/22
 * Authors:
 * Janosch Lentz
 * David Rechkemmer
 */

package lost.macpan;

import lost.macpan.panel.Intro;
import lost.macpan.panel.MainMenu;
import lost.macpan.utils.ResourceHandler;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.UIManager;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

/**
 * App Class that handles the application
 */
public class App extends JFrame implements ActionListener, ResourceHandler {
    private static final int width = 960;
    private static final int height = 700;
    private static final int serializeId = 123456789;
    private Timer timer;
    public Font fontWrite;
    /**
     * Constructor method for App Class
     * sets the JFrame attributes
     */
    public App(){
        int delay = 5000; //müssen testen um auf 4 Sekunden zu kommen jz ca. 2 Sek
//importing custom font
        Font fontRead = null; //"fonts/alagard.ttf"
        {
            try {
                InputStream stream = getFileResourcesAsStream("fonts/alagard_bearbeitet.ttf");
                fontRead = Font.createFont(Font.TRUETYPE_FONT, stream);
            } catch (FontFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        fontWrite = fontRead.deriveFont(Font.PLAIN, 31);

        try {
            setIconImage(ImageIO.read(getFileResourcesAsStream("images/Pan.png")).getScaledInstance(32, 32, 0));
        } catch (IOException e) {
            e.printStackTrace();
        }

        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get (key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put (key, fontWrite);
        }
        timer = new Timer(delay, this);
        setTitle("MacPan");
        setMinimumSize(new Dimension(width, height));
        getContentPane().setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new Intro());
        setResizable(false);
        pack();
        setVisible(true);
        initStorage();
        timer.start();
    }

    /**
     * handles the timeout to change the panels when the app starts
     * overriden method from ActionListener
     * @param evt don't need to be set
     */
    @Override
    public void actionPerformed ( ActionEvent evt) {
        MainMenu mM = new MainMenu(this);
        setContentPane(mM);
        revalidate();
        timer.stop();
    }
}
