package lost.macpan.panel.FoUND_Engine;

import lost.macpan.panel.FoUND_Engine.spriteClasses.*;
import lost.macpan.utils.ResourceHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class FoUND_Engine extends JPanel implements Runnable, ResourceHandler{
    //attributes
    private int originalTileSize = 16;
    private int scale = 2;
    public int tileSize = originalTileSize * scale;
    private int maxColumns = 32;
    private int maxRows = 24;
    private int width = maxColumns * tileSize;
    private int height = maxRows * tileSize;
    private JLabel label;
    private int framerate = 60;

    public BufferedImage boostedCoin;
    public BufferedImage normalCoin;
    public BufferedImage enemy1 = null;
    public BufferedImage enemy2 = null;
    public BufferedImage enemy3 = null;
    public BufferedImage enemy4 = null;
    public BufferedImage exit1 = null;
    public BufferedImage exit2 = null;
    public BufferedImage exit3 = null;
    public BufferedImage exit4 = null;
    public BufferedImage exit5 = null;
    public BufferedImage exit6 = null;
    public BufferedImage exit7 = null;
    public BufferedImage exit8 = null;
    public BufferedImage freeze;
    public BufferedImage key;
    public BufferedImage pan;
    public BufferedImage path;
    public BufferedImage player1 = null;
    public BufferedImage player2 = null;
    public BufferedImage player3 = null;
    public BufferedImage player4 = null;
    public BufferedImage shield;
    public BufferedImage speed;
    public BufferedImage wall;
    playerSprite playerSprite = new playerSprite(this);
    public void fetchSprites() {
        try {
            boostedCoin = ImageIO.read(getFileResourcesAsStream("images/Coin boost.png"));
            normalCoin = ImageIO.read(getFileResourcesAsStream("images/Coin normal.png"));
            enemy1 = ImageIO.read(getFileResourcesAsStream("images/enemy-1.png.png"));
            enemy2 = ImageIO.read(getFileResourcesAsStream("images/enemy-2.png.png"));
            enemy3 = ImageIO.read(getFileResourcesAsStream("images/enemy-3.png.png"));
            enemy4 = ImageIO.read(getFileResourcesAsStream("images/enemy-4.png.png"));
            exit1 = ImageIO.read(getFileResourcesAsStream("images/Exit-1.png.png"));
            exit2 = ImageIO.read(getFileResourcesAsStream("images/Exit-2.png.png"));
            exit3 = ImageIO.read(getFileResourcesAsStream("images/Exit-3.png.png"));
            exit4 = ImageIO.read(getFileResourcesAsStream("images/Exit-4.png.png"));
            exit5 = ImageIO.read(getFileResourcesAsStream("images/Exit-5.png.png"));
            exit6 = ImageIO.read(getFileResourcesAsStream("images/Exit-6.png.png"));
            exit7 = ImageIO.read(getFileResourcesAsStream("images/Exit-7.png.png"));
            exit8 = ImageIO.read(getFileResourcesAsStream("images/Exit-8.png.png"));
            freeze = ImageIO.read(getFileResourcesAsStream("images/Freeze.png"));
            key = ImageIO.read(getFileResourcesAsStream("images/Key.png"));
            pan = ImageIO.read(getFileResourcesAsStream("images/Pan.png"));
            path = ImageIO.read(getFileResourcesAsStream("images/Path.png"));
            player1 = ImageIO.read(getFileResourcesAsStream("images/Player-1.png.png"));
            player2 = ImageIO.read(getFileResourcesAsStream("images/Player-2.png.png"));
            player3 = ImageIO.read(getFileResourcesAsStream("images/Player-3.png.png"));
            player4 = ImageIO.read(getFileResourcesAsStream("images/Player-4.png.png"));
            shield = ImageIO.read(getFileResourcesAsStream("images/Shield.png"));
            speed = ImageIO.read(getFileResourcesAsStream("images/Speed.png"));
            wall = ImageIO.read(getFileResourcesAsStream("images/Wall.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int xExample = 0, yExample = 0;


    Thread tehGaem;

    public FoUND_Engine(){
        label = new JLabel("Hello there!");
        add(label);
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
    }

    /**
     * Thread starter
     */
    public void startTehGaem(){
        tehGaem = new Thread(this);
        tehGaem.start();
    }

    /**
     * Game Loop
     */
    @Override
    public void run() {
        double frametime = 1000000000 / framerate;
        double nextDrawTime = System.nanoTime() + frametime;
        while(tehGaem != null){
            gameLogic();
            xExample = (xExample + 1) % 100;
            yExample = (yExample + 1) % 100;
            repaint();
            try {
                double remainingTime = (nextDrawTime - System.nanoTime()) / 1000000;
                System.out.println("Maximum possible framerate (only up to " + framerate + " displayed): " + 1000000000 / (framerate - remainingTime));
                if (remainingTime < 0)
                    remainingTime = 0;
                Thread.sleep((long) remainingTime);
                nextDrawTime += frametime;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * generic method to be replaced
     * @return levelClass example
     */
    public levelClass gameLogic(){
        char[][] newMap = new char[32][24];
        int newScore = 42069;
        boolean[] newFlags = new boolean[8];
        double newTimer = 69.0;
        double newFreezeTimer = 1.1666666;

        for (int i = 0; i < 32; i++){
            newMap[i][0] = 'g';
            newMap[i][1] = 'h';
            newMap[i][2] = '.';
            newMap[i][3] = '*';
            newMap[i][4] = 'k';
            newMap[i][5] = 'x';
            newMap[i][6] = 'a';
            newMap[i][7] = 'b';
            newMap[i][8] = 'c';
            newMap[i][9] = 'd';
            newMap[i][10] = 'e';
            newMap[i][11] = 'p';
            for (int j = 12; j < 24; j++){
                newMap[i][j] = '*';
            }
        }
        newFlags[0] = true;
        for (int i = 1; i < 8; i++) {
            newFlags[i] = true;
        }

        return new levelClass(newMap, newScore, newFlags, newTimer, newFreezeTimer);
    };

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.CYAN);
        g2.fillRect(xExample,yExample, tileSize, tileSize);
        g2.drawImage(wall, 132, 132, tileSize, tileSize, null);
        playerSprite.draw(g2);
        g2.dispose();
    }

    public BufferedImage enemySpriteSelect(){
        long timeWithinSecond = System.currentTimeMillis() % 1000;
        if (timeWithinSecond < 500){
            if (timeWithinSecond < 250)
                return enemy1;
            else
                return enemy2;
        }
        else if (timeWithinSecond < 750)
            return enemy3;
        else
            return enemy4;
    }

    public static long animationStart = 0;
    public BufferedImage exitSpriteSelect(boolean isUnlocked){
        long animationTimer = 0;
        if (isUnlocked && animationStart != 0) {
            animationTimer = System.currentTimeMillis() - animationStart;
            if (animationTimer < 500) {
                if (animationTimer < 250) {
                    if (animationTimer < 125)
                        return exit1;
                    else
                        return exit2;
                } else if (animationTimer < 375)
                    return exit3;
                else
                    return exit4;
            } else if (animationTimer < 750)
                return exit3;
            else
                return exit4;
        }
        else if (isUnlocked && animationStart == 0)
            animationStart = System.currentTimeMillis();

        return exit1;
    }


}
