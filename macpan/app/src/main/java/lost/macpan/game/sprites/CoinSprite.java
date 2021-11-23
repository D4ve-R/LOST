package lost.macpan.game.sprites;

import lost.macpan.game.GameWindow;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Class for selecting and displaying a coin sprite
 * @author Leon Wigro
 * @version 1.0
 */
public class CoinSprite extends Sprite{

    public CoinSprite(GameWindow game) {
        super(game);
    }

    /**
     * draw call for a coin sprite within the game loop
     * @param g entity to be drawn to
     * @param x x-coordinate measured in tiles
     * @param y y-coordinate measured in tiles
     */
    public void draw(Graphics2D g, int x, int y) {
        super.draw(g, x, y, game.path);                     //underlays path sprite
        super.draw(g, x, y, coinSpriteSelect(game.boost));  //draws coin sprite
    }

    /**
     * selects a sprite to be used based on the coin booster flag and returns it
     * @return player sprite
     */
    private BufferedImage coinSpriteSelect(boolean boost){
        if (game.boost)
            return game.boostedCoin;
        else
            return game.normalCoin;
    }
}