package lost.macpan.game.sprites;

import lost.macpan.game.GameWindow;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Class for displaying and animating a player sprite
 * @author Leon Wigro
 * @version 1.0
 */
public class PlayerSprite extends Sprite {
    //attributes
    private BufferedImage player1;
    private BufferedImage player2;
    private BufferedImage player3;
    private BufferedImage player4;

    //constructor
    public PlayerSprite(GameWindow game){
        super(game);
    }

    /**
     * fetches player sprites
     */
    @Override
    public void fetchSprites() {
        try {
            player1 = ImageIO.read(getFileResourcesAsStream("images/Player-1.png.png"));
            player2 = ImageIO.read(getFileResourcesAsStream("images/Player-2.png.png"));
            player3 = ImageIO.read(getFileResourcesAsStream("images/Player-3.png.png"));
            player4 = ImageIO.read(getFileResourcesAsStream("images/Player-4.png.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                return player1;
            else
                return player2;
        }
        else if (timeWithinSecond < 750)
            return player3;
        else
            return player4;
    }
}
