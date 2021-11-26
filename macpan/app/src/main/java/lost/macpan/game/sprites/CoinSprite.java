package lost.macpan.game.sprites;

import lost.macpan.game.GameWindow;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Class for selecting and displaying a coin sprite
 * @author Leon Wigro
 * @version 1.0
 */
public class CoinSprite extends Sprite{
    //attributes
    private BufferedImage boostedCoin;
    private BufferedImage normalCoin;

    //constructor
    public CoinSprite(GameWindow game) {
        super(game);
    }

    /**
     * fetches coin sprites
     */
    @Override
    public void fetchSprites() {
        try {
            boostedCoin = ImageIO.read(getFileResourcesAsStream("images/Coin boost.png"));
            normalCoin = ImageIO.read(getFileResourcesAsStream("images/Coin normal.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * draw call for a coin sprite within the game loop
     * @param g entity to be drawn to
     * @param x x-coordinate measured in tiles
     * @param y y-coordinate measured in tiles
     */
    public void draw(Graphics2D g, int x, int y) {
        super.draw(g, x, y, game.path);                     //underlays path sprite
        super.draw(g, x, y, coinSpriteSelect(game.flags[5]));  //draws coin sprite
    }

    /**
     * selects a sprite to be used based on the coin booster flag and returns it
     * @return player sprite
     */
    private BufferedImage coinSpriteSelect(boolean boost){
        if (boost)
            return boostedCoin;
        else
            return normalCoin;
    }
}