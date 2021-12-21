/**
 * MacPan version 0.1
 * SWE WS 21/22
 */

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

    public HUD (){
        fetchSprites();
    }

    /**
     * fetches HUD sprites
     */
    public void fetchSprites() {
        try {
            fieldOpener = ImageIO.read(getFileResourcesAsStream("images/hud sprites/HUD field opener.png"));
            fieldCloser = ImageIO.read(getFileResourcesAsStream("images/hud sprites/HUD field closer.png"));
            fieldSpacer = ImageIO.read(getFileResourcesAsStream("images/hud sprites/HUD field spacer.png"));
            effectField = ImageIO.read(getFileResourcesAsStream("images/hud sprites/HUD effect field.png"));
            filler = ImageIO.read(getFileResourcesAsStream("images/hud sprites/HUD filler.png"));
            key = ImageIO.read(getFileResourcesAsStream("images/Key.png"));
            lifeEffect = ImageIO.read(getFileResourcesAsStream("images/life effect.png"));
            speedEffect = ImageIO.read(getFileResourcesAsStream("images/speed effect.png"));
            attack = ImageIO.read(getFileResourcesAsStream("images/Sword.png"));
            boostEffect = ImageIO.read(getFileResourcesAsStream("images/boost effect.png"));
            freezeEffect = ImageIO.read(getFileResourcesAsStream("images/freeze effect.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Font fontRead;
    {
        try {
            InputStream stream = getFileResourcesAsStream("fonts/alagard_bearbeitet.ttf");
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
    public void draw(Graphics2D g, int hudHeight,int pScore, int pTileSize, boolean[] pFlags, int pMaxColumns){
        int iterator = 0;
        g.setFont(fontWrite);
        g.setColor(Color.white);

        //draws HUD bar
        g.drawImage(fieldOpener, pTileSize * iterator++, (hudHeight - 1) * pTileSize, pTileSize, pTileSize, null);
        g.drawImage(fieldSpacer, pTileSize * iterator++, (hudHeight - 1) * pTileSize, pTileSize, pTileSize, null);
        g.drawImage(fieldSpacer, pTileSize * iterator++, (hudHeight - 1) * pTileSize, pTileSize, pTileSize, null);
        g.drawImage(fieldSpacer, pTileSize * iterator++, (hudHeight - 1) * pTileSize, pTileSize, pTileSize, null);
        g.drawImage(fieldSpacer, pTileSize * iterator++, (hudHeight - 1) * pTileSize, pTileSize, pTileSize, null);
        g.drawImage(fieldSpacer, pTileSize * iterator++, (hudHeight - 1) * pTileSize, pTileSize, pTileSize, null);
        g.drawImage(fieldCloser, pTileSize * iterator++, (hudHeight - 1) * pTileSize, pTileSize, pTileSize, null);
        g.drawImage(filler, pTileSize * iterator++, (hudHeight - 1) * pTileSize, pTileSize, pTileSize, null);
        g.drawImage(effectField, pTileSize * iterator++, (hudHeight - 1) * pTileSize, pTileSize, pTileSize, null);
        g.drawImage(effectField, pTileSize * iterator++, (hudHeight - 1) * pTileSize, pTileSize, pTileSize, null);
        g.drawImage(effectField, pTileSize * iterator++, (hudHeight - 1) * pTileSize, pTileSize, pTileSize, null);
        g.drawImage(effectField, pTileSize * iterator++, (hudHeight - 1) * pTileSize, pTileSize, pTileSize, null);
        g.drawImage(effectField, pTileSize * iterator++, (hudHeight - 1) * pTileSize, pTileSize, pTileSize, null);
        g.drawImage(effectField, pTileSize * iterator++, (hudHeight - 1) * pTileSize, pTileSize, pTileSize, null);
        for (int i = iterator; i < pMaxColumns; i++)
            g.drawImage(filler, pTileSize * iterator++, (hudHeight - 1) * pTileSize, pTileSize, pTileSize, null);

        //draws active effects
        if (pFlags[3])
            g.drawImage(key, pTileSize * 8, (hudHeight - 1) * pTileSize, pTileSize, pTileSize, null);
        if (pFlags[1])
            g.drawImage(lifeEffect, pTileSize * 9 + 2, (hudHeight - 1) * pTileSize + 1, pTileSize-2, pTileSize-2, null);
        if (pFlags[2])
            g.drawImage(speedEffect, pTileSize * 10 + 2, (hudHeight - 1) * pTileSize + 1, pTileSize-2, pTileSize-2, null);
        if (pFlags[4])
            g.drawImage(attack, pTileSize * 11 + 2, (hudHeight - 1) * pTileSize + 1, pTileSize-2, pTileSize-2, null);
        if (pFlags[5])
            g.drawImage(boostEffect, pTileSize * 12 + 2, (hudHeight - 1) * pTileSize + 1, pTileSize-2, pTileSize-2, null);
        if (pFlags[7])
            g.drawImage(freezeEffect, pTileSize * 13 + 2, (hudHeight - 1) * pTileSize + 1, pTileSize-2, pTileSize-2, null);

        //draws Score
        g.drawString("SCORE: " + pScore, 15, hudHeight * pTileSize - 5);
    }
}
