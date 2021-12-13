package lost.macpan.game.sprites;

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
    private int tileSize;
    private BufferedImage boostItem;
    private BufferedImage freeze;
    private BufferedImage key;
    private BufferedImage pan;
    private BufferedImage shield;
    private BufferedImage speed;
    private BufferedImage wall;
    private BufferedImage path;

    //constructor
    public Sprite(int pTileSize){
        tileSize = pTileSize;
        fetchSprites();
    }

    /**
     * fetches static sprites
     */
    public void fetchSprites() {
        try {
            boostItem = ImageIO.read(getFileResourcesAsStream("images/Boost.png"));
            freeze = ImageIO.read(getFileResourcesAsStream("images/Freeze.png"));
            key = ImageIO.read(getFileResourcesAsStream("images/Key.png"));
            pan = ImageIO.read(getFileResourcesAsStream("images/Pan.png"));
            shield = ImageIO.read(getFileResourcesAsStream("images/Shield.png"));
            speed = ImageIO.read(getFileResourcesAsStream("images/Speed.png"));
            path = ImageIO.read(getFileResourcesAsStream("images/Path.png"));
            wall = ImageIO.read(getFileResourcesAsStream("images/Wall.png"));
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
        if (c == 'h' || c == '\0' || c == '\r')
            g.drawImage(wall, tileSize * x, tileSize * y, tileSize, tileSize, null);    //draws wall sprite
        else if (c == 'k') {
            g.drawImage(path, tileSize * x, tileSize * y, tileSize, tileSize, null);    //underlays path sprite
            g.drawImage(key, tileSize * x, tileSize * y, tileSize, tileSize, null);     //draws key sprite
        }
        else if (c == 'a'){
            g.drawImage(path, tileSize * x, tileSize * y, tileSize, tileSize, null);    //underlays path sprite
            g.drawImage(speed, tileSize * x, tileSize * y, tileSize, tileSize, null);   //draws speed boost sprite
        }
        else if (c == 'b') {
            g.drawImage(path, tileSize * x, tileSize * y, tileSize, tileSize, null);    //underlays path sprite
            g.drawImage(freeze, tileSize * x, tileSize * y, tileSize, tileSize, null);  //draws freeze sprite
        }
        else if (c == 'c') {
            g.drawImage(path, tileSize * x, tileSize * y, tileSize, tileSize, null);    //underlays path sprite
            g.drawImage(boostItem, tileSize * x, tileSize * y, tileSize, tileSize, null);//draws coin boost sprite
        }
        else if (c == 'd') {
            g.drawImage(path, tileSize * x, tileSize * y, tileSize, tileSize, null);    //underlays path sprite
            g.drawImage(shield, tileSize * x, tileSize * y, tileSize, tileSize, null);  //draws shield sprite
        }
        else if (c == 'e') {
            g.drawImage(path, tileSize * x, tileSize * y, tileSize, tileSize, null);    //underlays path sprite
            g.drawImage(pan, tileSize * x, tileSize * y, tileSize, tileSize, null);     //draws pan sprite
        }
        else if (c == '.')
            g.drawImage(path, tileSize * x, tileSize * y, tileSize, tileSize, null);    //draws path sprite
    }

    /**
     * draw call for the specified sprite within the game loop
     * @param g entity to be drawn to
     * @param x x-coordinate measured in tiles
     * @param y y-coordinate measured in tiles
     * @param spriteToUse for sprite to be drawn
     */
    public void draw(Graphics2D g, int x, int y, BufferedImage spriteToUse){
        g.drawImage(spriteToUse, x * tileSize, y * tileSize, tileSize, tileSize, null);
    }
}
