package lost.macpan.panel.game_visuals.spriteClasses;

import lost.macpan.panel.game_visuals.GameWindow;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Class for displaying and animating an enemy sprite
 * @author Leon Wigro
 * @version 1.0
 */
public class EnemySprite extends Sprite{

    public EnemySprite(GameWindow game) {
        super(game);
    }

    /**
     * draw call for the enemy sprite within the game loop
     * @param g entity to be drawn to
     * @param x x-coordinate measured in tiles
     * @param y y-coordinate measured in tiles
     */
    public void draw(Graphics2D g, int x, int y) {
        super.draw(g, x, y, game.path);             //underlays path sprite
        super.draw(g, x, y, enemySpriteSelect());   //draws enemy sprite
    }

    /**
     * selects a sprite to be used based on animation progression and returns it
     * @return player sprite
     */
    public BufferedImage enemySpriteSelect(){
        long timeWithinSecond = System.currentTimeMillis() % 1000;
        if (timeWithinSecond < 500){
            if (timeWithinSecond < 250)
                return game.enemy1;
            else
                return game.enemy2;
        }
        else if (timeWithinSecond < 750)
            return game.enemy3;
        else
            return game.enemy4;
    }
}
