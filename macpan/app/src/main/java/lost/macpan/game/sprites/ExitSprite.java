package lost.macpan.game.sprites;

import lost.macpan.game.GameWindow;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Class for displaying and animating the exit sprite
 * @author Leon Wigro
 * @version 1.0
 */
public class ExitSprite extends Sprite{
    //attributes
    private BufferedImage exit1;
    private BufferedImage exit2;
    private BufferedImage exit3;
    private BufferedImage exit4;
    private BufferedImage exit5;
    private BufferedImage exit6;
    private BufferedImage exit7;
    private BufferedImage exit8;
    private BufferedImage wall;
    private static long animationStart = 0; //used for keeping track of exit animation progression

    //constructor
    public ExitSprite(int pTileSize) {
        super(pTileSize);
    }

    /**
     * fetches exit sprites
     */
    @Override
    public void fetchSprites() {
        try {
            exit1 = ImageIO.read(getFileResourcesAsStream("images/exit/Exit-1.png.png"));
            exit2 = ImageIO.read(getFileResourcesAsStream("images/exit/Exit-2.png.png"));
            exit3 = ImageIO.read(getFileResourcesAsStream("images/exit/Exit-3.png.png"));
            exit4 = ImageIO.read(getFileResourcesAsStream("images/exit/Exit-4.png.png"));
            exit5 = ImageIO.read(getFileResourcesAsStream("images/exit/Exit-5.png.png"));
            exit6 = ImageIO.read(getFileResourcesAsStream("images/exit/Exit-6.png.png"));
            exit7 = ImageIO.read(getFileResourcesAsStream("images/exit/Exit-7.png.png"));
            exit8 = ImageIO.read(getFileResourcesAsStream("images/exit/Exit-8.png.png"));
            wall = ImageIO.read(getFileResourcesAsStream("images/Wall.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * draw call for the exit sprite within the game loop
     * @param g entity to be drawn to
     * @param x x-coordinate measured in tiles
     * @param y y-coordinate measured in tiles
     */
    public void draw(Graphics2D g, int x, int y, boolean[] pFlags) {
        super.draw(g, x, y, wall);                             //underlays wall sprite
        super.draw(g, x, y, exitSpriteSelect(pFlags[6]));     //draws exit sprite
    }

    /**
     * selects a sprite to be used based on animation progression and returns it
     * @return player sprite
     */
    private BufferedImage exitSpriteSelect(boolean isUnlocked){
        long animationTimer;
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
            } else if (animationTimer < 750) {
                if (animationTimer < 625)
                    return exit5;
                else
                    return exit6;
            }else if (animationTimer < 875)
                return exit7;
            else
                return exit8;

        }
        else if (isUnlocked)
            animationStart = System.currentTimeMillis();

        return exit1;
    }
}
