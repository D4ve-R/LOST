/**
 * MacPan version 0.1
 * SWE WS 21/22
 */

package lost.macpan.game.sprites;

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
    private BufferedImage exit1;
    private BufferedImage exit2;
    private BufferedImage exit3;
    private BufferedImage exit4;
    private BufferedImage exit5;
    private BufferedImage exit6;
    private BufferedImage exit7;
    private BufferedImage exit8;
    private BufferedImage wall;
    private static long animationStart = 0;

    public ExitSprite(int pTileSize) {
        super(pTileSize);
    }

    /**
     * fetches exit sprites
     */
    @Override
    public void fetchSprites() {
        try {
            exit1 = ImageIO.read(getFileResourcesAsStream("images/exit/Exit-1.png"));
            exit2 = ImageIO.read(getFileResourcesAsStream("images/exit/Exit-2.png"));
            exit3 = ImageIO.read(getFileResourcesAsStream("images/exit/Exit-3.png"));
            exit4 = ImageIO.read(getFileResourcesAsStream("images/exit/Exit-4.png"));
            exit5 = ImageIO.read(getFileResourcesAsStream("images/exit/Exit-5.png"));
            exit6 = ImageIO.read(getFileResourcesAsStream("images/exit/Exit-6.png"));
            exit7 = ImageIO.read(getFileResourcesAsStream("images/exit/Exit-7.png"));
            exit8 = ImageIO.read(getFileResourcesAsStream("images/exit/Exit-8.png"));
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
        super.draw(g, x, y, exitSpriteSelect(pFlags[3]));     //draws exit sprite
    }

    /**
     * selects a sprite to be used based on animation progression and returns it
     * @return player sprite
     */
    private BufferedImage exitSpriteSelect(boolean isUnlocked){
        long animationTimer;
        if (isUnlocked && animationStart != 0) {
            animationTimer = System.currentTimeMillis() - animationStart;
            if (animationTimer < 125) return exit1;
            if(animationTimer >= 875) return exit8;
            if(animationTimer < 250) return exit2;
            if(animationTimer < 375) return exit3;
            if(animationTimer < 500) return exit4;
            if(animationTimer < 625) return exit5;
            if(animationTimer < 750) return exit6;
            else return exit7;
        }

        if(isUnlocked) {
            animationStart = System.currentTimeMillis();
        } else animationStart = 0;

        return exit1;
    }
}
