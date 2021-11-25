package lost.macpan.game;

import lost.macpan.utils.ResourceHandler;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Class for drawing the HUD
 * @author Leon Wigro
 * @version 0.1
 */
public class HUD implements ResourceHandler {
    //attributes
    GameWindow game;
    BufferedImage fieldOpener;
    BufferedImage fieldSpacer;
    BufferedImage fieldCloser;
    BufferedImage effectField;
    BufferedImage filler;
    BufferedImage key;
    BufferedImage lifeEffect;
    BufferedImage speedEffect;
    BufferedImage attack;
    BufferedImage boostEffect;
    BufferedImage freezeEffect;
    int iterator;   //used for drawing the HUD bar

    //constructor
    public HUD (GameWindow game){
        this.game = game;
        fetchSprites();
    }

    /**
     * fetches HUD sprites
     */
    public void fetchSprites() {
        try {
            fieldOpener = ImageIO.read(getFileResourcesAsStream("images/hud sprites/HUD field opener-1.png.png"));
            fieldCloser = ImageIO.read(getFileResourcesAsStream("images/hud sprites/HUD field closer-1.png.png"));
            fieldSpacer = ImageIO.read(getFileResourcesAsStream("images/hud sprites/HUD field spacer-1.png.png"));
            effectField = ImageIO.read(getFileResourcesAsStream("images/hud sprites/HUD effect field-1.png.png"));
            filler = ImageIO.read(getFileResourcesAsStream("images/hud sprites/HUD filler-1.png.png"));
            key = ImageIO.read(getFileResourcesAsStream("images/Key.png"));
            lifeEffect = ImageIO.read(getFileResourcesAsStream("images/life effect-1.png.png"));
            speedEffect = ImageIO.read(getFileResourcesAsStream("images/speed effect-1.png.png"));
            attack = ImageIO.read(getFileResourcesAsStream("images/Sword-1.png.png"));
            boostEffect = ImageIO.read(getFileResourcesAsStream("images/boost effect-1.png.png"));
            freezeEffect = ImageIO.read(getFileResourcesAsStream("images/freeze effect-1.png.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //importing custom font
    Font fontRead; //"fonts/alagard.ttf"
    {
        try {
            InputStream stream = getFileResourcesAsStream("fonts/alagard.ttf");
            fontRead = Font.createFont(Font.TRUETYPE_FONT, stream);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    Font fontWrite = fontRead.deriveFont(Font.PLAIN, 31);

    /**
     * draw call for the HUD
     * @param g entity to be drawn to
     * @param hudHeight where the HUD is to be drawn
     */
    public void draw(Graphics2D g, int hudHeight){
        iterator = 0;
        g.setFont(fontWrite);
        g.setColor(Color.white);

        //draws HUD bar
        g.drawImage(fieldOpener, game.tileSize * iterator++, (hudHeight - 1) * game.tileSize, game.tileSize, game.tileSize, null);
        g.drawImage(fieldSpacer, game.tileSize * iterator++, (hudHeight - 1) * game.tileSize, game.tileSize, game.tileSize, null);
        g.drawImage(fieldSpacer, game.tileSize * iterator++, (hudHeight - 1) * game.tileSize, game.tileSize, game.tileSize, null);
        g.drawImage(fieldSpacer, game.tileSize * iterator++, (hudHeight - 1) * game.tileSize, game.tileSize, game.tileSize, null);
        g.drawImage(fieldSpacer, game.tileSize * iterator++, (hudHeight - 1) * game.tileSize, game.tileSize, game.tileSize, null);
        g.drawImage(fieldSpacer, game.tileSize * iterator++, (hudHeight - 1) * game.tileSize, game.tileSize, game.tileSize, null);
        g.drawImage(fieldCloser, game.tileSize * iterator++, (hudHeight - 1) * game.tileSize, game.tileSize, game.tileSize, null);
        g.drawImage(filler, game.tileSize * iterator++, (hudHeight - 1) * game.tileSize, game.tileSize, game.tileSize, null);
        g.drawImage(effectField, game.tileSize * iterator++, (hudHeight - 1) * game.tileSize, game.tileSize, game.tileSize, null);
        g.drawImage(effectField, game.tileSize * iterator++, (hudHeight - 1) * game.tileSize, game.tileSize, game.tileSize, null);
        g.drawImage(effectField, game.tileSize * iterator++, (hudHeight - 1) * game.tileSize, game.tileSize, game.tileSize, null);
        g.drawImage(effectField, game.tileSize * iterator++, (hudHeight - 1) * game.tileSize, game.tileSize, game.tileSize, null);
        g.drawImage(effectField, game.tileSize * iterator++, (hudHeight - 1) * game.tileSize, game.tileSize, game.tileSize, null);
        g.drawImage(effectField, game.tileSize * iterator++, (hudHeight - 1) * game.tileSize, game.tileSize, game.tileSize, null);
        for (int i = iterator; i < game.maxColumns; i++)
            g.drawImage(filler, game.tileSize * iterator++, (hudHeight - 1) * game.tileSize, game.tileSize, game.tileSize, null);

        //draws active effects
        if (game.level.flags[3])
            g.drawImage(key, game.tileSize * 8, (hudHeight - 1) * game.tileSize, game.tileSize, game.tileSize, null);
        if (game.level.flags[1])
            g.drawImage(lifeEffect, game.tileSize * 9 + 2, (hudHeight - 1) * game.tileSize + 1, game.tileSize-2, game.tileSize-2, null);
        if (game.level.flags[2])
            g.drawImage(speedEffect, game.tileSize * 10 + 2, (hudHeight - 1) * game.tileSize + 1, game.tileSize-2, game.tileSize-2, null);
        if (game.level.flags[4])
            g.drawImage(attack, game.tileSize * 11 + 2, (hudHeight - 1) * game.tileSize + 1, game.tileSize-2, game.tileSize-2, null);
        if (game.level.flags[5])
            g.drawImage(boostEffect, game.tileSize * 12 + 2, (hudHeight - 1) * game.tileSize + 1, game.tileSize-2, game.tileSize-2, null);
        if (game.level.flags[7])
            g.drawImage(freezeEffect, game.tileSize * 13 + 2, (hudHeight - 1) * game.tileSize + 1, game.tileSize-2, game.tileSize-2, null);

        //draws Score
        g.drawString("SCORE: " + game.level.score, 15, hudHeight * game.tileSize - 5);
    }
}
