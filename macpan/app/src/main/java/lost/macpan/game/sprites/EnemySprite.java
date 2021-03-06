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
 * Class for displaying and animating an enemy sprite
 * @author Leon Wigro
 * @version 1.0
 */
public class EnemySprite extends Sprite{
    private BufferedImage enemy1;
    private BufferedImage enemy2;
    private BufferedImage enemy3;
    private BufferedImage enemy4;
    private BufferedImage path;

    public EnemySprite(int pTileSize) {
        super(pTileSize);
    }

    /**
     * fetches enemy sprites
     */
    @Override
    public void fetchSprites() {
        try {
            enemy1 = ImageIO.read(getFileResourcesAsStream("images/enemy/enemy-1.png"));
            enemy2 = ImageIO.read(getFileResourcesAsStream("images/enemy/enemy-2.png"));
            enemy3 = ImageIO.read(getFileResourcesAsStream("images/enemy/enemy-3.png"));
            enemy4 = ImageIO.read(getFileResourcesAsStream("images/enemy/enemy-4.png"));
            path = ImageIO.read(getFileResourcesAsStream("images/Path.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * draw call for the enemy sprite within the game loop
     * @param g entity to be drawn to
     * @param x x-coordinate measured in tiles
     * @param y y-coordinate measured in tiles
     */
    public void draw(Graphics2D g, int x, int y) {
        super.draw(g, x, y, path);
        super.draw(g, x, y, enemySpriteSelect());
    }

    /**
     * selects a sprite to be used based on animation progression and returns it
     * @return player sprite
     */
    private BufferedImage enemySpriteSelect(){
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
}
