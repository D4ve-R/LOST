package lost.macpan.game.sprites;

import lost.macpan.game.GameWindow;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Class for drawing single frame sprites
 * @author Leon Wigro
 * @version 1.0
 */
public class Sprite {
    GameWindow game; //used to access the sprites fetched within the GameWindow

    public Sprite(GameWindow game){
        this.game = game;
    }

    /**
     * draw call for the sprite specified in spriteToUse within the game loop
     * @param g entity to be drawn to
     * @param x x-coordinate measured in tiles
     * @param y y-coordinate measured in tiles
     * @param spriteToUse sprite to be drawn
     */
    public void draw(Graphics2D g, int x, int y, BufferedImage spriteToUse){
        g.drawImage(spriteToUse, game.tileSize * x, game.tileSize * y, game.tileSize, game.tileSize, null);
    }
}
