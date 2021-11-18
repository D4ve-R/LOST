package lost.macpan.panel;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;

public class GameWindow extends JPanel implements Runnable{
    //attributes
    private int originalTileSize = 16;
    private int scale = 2;
    private int tileSize = originalTileSize * scale;
    private int maxColumns = 32;
    private int maxRows = 24;
    private int width = maxColumns * tileSize;
    private int height = maxRows * tileSize;
    private JLabel label;

    Thread tehGaem;

    public GameWindow(){
        label = new JLabel("Hello there!");
        add(label);
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
    }

    public void startTehGaem(){
        tehGaem = new Thread(this);
        tehGaem.start();
    }

    @Override
    public void run() {
        while(tehGaem != null){
            System.out.println("running");
        }
    }
}
