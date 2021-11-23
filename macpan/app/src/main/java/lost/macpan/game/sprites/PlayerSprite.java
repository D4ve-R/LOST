package lost.macpan.game.sprites;

import lost.macpan.game.GameWindow;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Class for displaying and animating a player sprite
 * @author Leon Wigro
 * @version 1.0
 */
public class PlayerSprite extends Sprite {

    public PlayerSprite(GameWindow game){
        super(game);
    }

    /**
     * draw call for the player sprite within the game loop
     * @param g entity to be drawn to
     * @param x x-coordinate measured in tiles
     * @param y y-coordinate measured in tiles
     */
    public void draw(Graphics2D g, int x, int y){
        super.draw(g, x, y, game.path);                 //underlays path sprite
        super.draw(g, x, y, playerSpriteSelect());      //draws player sprite
    }

    /**
     * selects a sprite to be used based on animation progression and returns it
     * @return player sprite
     */
    public BufferedImage playerSpriteSelect(){
        long timeWithinSecond = System.currentTimeMillis() % 1000;
        if (timeWithinSecond < 500){
            if (timeWithinSecond < 250)
                return game.player1;
            else
                return game.player2;
        }
        else if (timeWithinSecond < 750)
            return game.player3;
        else
            return game.player4;
    }
}
