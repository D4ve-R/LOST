package lost.macpan.panel.game_visuals.spriteClasses;

import lost.macpan.panel.game_visuals.GameWindow;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Class for displaying and animating the exit sprite
 * @author Leon Wigro
 * @version 1.0
 */
public class ExitSprite extends Sprite{

    public ExitSprite(GameWindow game) {
        super(game);
    }

    /**
     * draw call for the exit sprite within the game loop
     * @param g entity to be drawn to
     * @param x x-coordinate measured in tiles
     * @param y y-coordinate measured in tiles
     */
    public void draw(Graphics2D g, int x, int y) {
        super.draw(g, x, y, game.wall);                             //underlays wall sprite
        super.draw(g, x, y, exitSpriteSelect(game.isUnlocked));     //draws exit sprite
    }

    public static long animationStart = 0; //used for keeping track of exit animation progression
    /**
     * selects a sprite to be used based on animation progression and returns it
     * @return player sprite
     */
    public BufferedImage exitSpriteSelect(boolean isUnlocked){
        long animationTimer;
        if (isUnlocked && animationStart != 0) {
            animationTimer = System.currentTimeMillis() - animationStart;
            if (animationTimer < 500) {
                if (animationTimer < 250) {
                    if (animationTimer < 125)
                        return game.exit1;
                    else
                        return game.exit2;
                } else if (animationTimer < 375)
                    return game.exit3;
                else
                    return game.exit4;
            } else if (animationTimer < 750) {
                if (animationTimer < 625)
                    return game.exit5;
                else
                    return game.exit6;
            }else if (animationTimer < 875)
                return game.exit7;
            else
                return game.exit8;

        }
        else if (isUnlocked)
            animationStart = System.currentTimeMillis();

        return game.exit1;
    }
}
