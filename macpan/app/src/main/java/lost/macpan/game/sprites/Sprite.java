package lost.macpan.game.sprites;

import lost.macpan.game.GameWindow;
import lost.macpan.utils.ResourceHandler;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Class for drawing single frame sprites
 * @author Leon Wigro
 * @version 1.0
 */
public class Sprite implements ResourceHandler {
    //attributes
    GameWindow game; //used to access the sprites fetched within the GameWindow
    private BufferedImage boostItem;
    private BufferedImage freeze;
    private BufferedImage key;
    private BufferedImage pan;
    private BufferedImage shield;
    private BufferedImage speed;

    //constructor
    public Sprite(GameWindow game){
        this.game = game;
        fetchSprites();
    }

    /**
     * fetches static sprites
     */
    public void fetchSprites() {
        try {
            boostItem = ImageIO.read(getFileResourcesAsStream("images/Boost-1.png.png"));
            freeze = ImageIO.read(getFileResourcesAsStream("images/Freeze.png"));
            key = ImageIO.read(getFileResourcesAsStream("images/Key.png"));
            pan = ImageIO.read(getFileResourcesAsStream("images/Pan.png"));
            shield = ImageIO.read(getFileResourcesAsStream("images/Shield.png"));
            speed = ImageIO.read(getFileResourcesAsStream("images/Speed.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * draw call for the specified sprite within the game loop
     * @param g entity to be drawn to
     * @param x x-coordinate measured in tiles
     * @param y y-coordinate measured in tiles
     * @param c identifier for sprite to be drawn
     */
    public void draw(Graphics2D g, int x, int y, char c){
        if (c == 'h' || c == '\0')
            g.drawImage(game.wall, game.tileSize * x, game.tileSize * y, game.tileSize, game.tileSize, null);    //draws wall sprite
        else if (c == 'k') {
            g.drawImage(game.path, game.tileSize * x, game.tileSize * y, game.tileSize, game.tileSize, null);    //underlays path sprite
            g.drawImage(key, game.tileSize * x, game.tileSize * y, game.tileSize, game.tileSize, null);     //draws key sprite
        }
        else if (c == 'a'){
            g.drawImage(game.path, game.tileSize * x, game.tileSize * y, game.tileSize, game.tileSize, null);    //underlays path sprite
            g.drawImage(speed, game.tileSize * x, game.tileSize * y, game.tileSize, game.tileSize, null);   //draws speed boost sprite
        }
        else if (c == 'b') {
            g.drawImage(game.path, game.tileSize * x, game.tileSize * y, game.tileSize, game.tileSize, null);    //underlays path sprite
            g.drawImage(freeze, game.tileSize * x, game.tileSize * y, game.tileSize, game.tileSize, null);  //draws freeze sprite
        }
        else if (c == 'c') {
            g.drawImage(game.path, game.tileSize * x, game.tileSize * y, game.tileSize, game.tileSize, null);    //underlays path sprite
            g.drawImage(boostItem, game.tileSize * x, game.tileSize * y, game.tileSize, game.tileSize, null);//draws coin boost sprite
        }
        else if (c == 'd') {
            g.drawImage(game.path, game.tileSize * x, game.tileSize * y, game.tileSize, game.tileSize, null);    //underlays path sprite
            g.drawImage(shield, game.tileSize * x, game.tileSize * y, game.tileSize, game.tileSize, null);  //draws shield sprite
        }
        else if (c == 'e') {
            g.drawImage(game.path, game.tileSize * x, game.tileSize * y, game.tileSize, game.tileSize, null);    //underlays path sprite
            g.drawImage(pan, game.tileSize * x, game.tileSize * y, game.tileSize, game.tileSize, null);     //draws pan sprite
        }
        else if (c == '0')
            g.drawImage(game.path, game.tileSize * x, game.tileSize * y, game.tileSize, game.tileSize, null);    //draws path sprite
    }

    /**
     * draw call for the specified sprite within the game loop
     * @param g entity to be drawn to
     * @param x x-coordinate measured in tiles
     * @param y y-coordinate measured in tiles
     * @param spriteToUse for sprite to be drawn
     */
    public void draw(Graphics2D g, int x, int y, BufferedImage spriteToUse){
        g.drawImage(spriteToUse, x * game.tileSize, y * game.tileSize, game.tileSize, game.tileSize, null);
    }
}
