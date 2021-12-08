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
    private int hudHeight = 20;
    private Font font;
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


    //constructor
    public HUD (){
        fetchSprites();
        try {
            InputStream stream = getFileResourcesAsStream("fonts/alagard.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, stream);
            font = font.deriveFont(Font.PLAIN, 25);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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


    /**
     * draw call for the HUD
     * @param g entity to be drawn to
     */
    public void draw(Graphics2D g, int pScore, int pTileSize, boolean[] pFlags, int pMaxColumns){
        int iterator = 0;
        g.setFont(font);
        g.setColor(Color.white);

        g.drawImage(fieldOpener, pTileSize * iterator++, hudHeight * pTileSize, pTileSize, pTileSize, null);

        for(;iterator < 6; iterator++) {
            g.drawImage(fieldSpacer, pTileSize * iterator, hudHeight * pTileSize, pTileSize, pTileSize, null);
        }

        g.drawImage(fieldCloser, pTileSize * iterator++, hudHeight * pTileSize, pTileSize, pTileSize, null);

        g.drawImage(filler, pTileSize * iterator++, hudHeight * pTileSize, pTileSize, pTileSize, null);

        for(;iterator < 15; iterator++) {
            g.drawImage(effectField, pTileSize * iterator, hudHeight * pTileSize, pTileSize, pTileSize, null);
        }

        for (int i = iterator; i < pMaxColumns; i++)
            g.drawImage(filler, pTileSize * iterator++, hudHeight * pTileSize, pTileSize, pTileSize, null);

        //draws active effects
        if (pFlags[3])
            g.drawImage(key, pTileSize * 8, hudHeight * pTileSize, pTileSize, pTileSize, null);
        if (pFlags[1])
            g.drawImage(lifeEffect, pTileSize * 9 + 2, hudHeight * pTileSize + 1, pTileSize-2, pTileSize-2, null);
        if (pFlags[2])
            g.drawImage(speedEffect, pTileSize * 10 + 2, hudHeight * pTileSize + 1, pTileSize-2, pTileSize-2, null);
        if (pFlags[4])
            g.drawImage(attack, pTileSize * 11 + 2, hudHeight * pTileSize + 1, pTileSize-2, pTileSize-2, null);
        if (pFlags[5])
            g.drawImage(boostEffect, pTileSize * 12 + 2, hudHeight * pTileSize + 1, pTileSize-2, pTileSize-2, null);
        if (pFlags[7])
            g.drawImage(freezeEffect, pTileSize * 13 + 2, hudHeight * pTileSize + 1, pTileSize-2, pTileSize-2, null);

        //draws Score
        g.drawString("SCORE: " + pScore, 15, 665);
    }
}
