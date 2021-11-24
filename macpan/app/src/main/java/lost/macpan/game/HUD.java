package lost.macpan.game;

import lost.macpan.utils.ResourceHandler;

import java.awt.*;
import java.io.*;

public class HUD implements ResourceHandler {
    //attributes
    GameWindow game;


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
    Font fontWrite = fontRead.deriveFont(Font.PLAIN, 32);

    public HUD (GameWindow game){
        this.game = game;
    }

    public void draw(Graphics2D g, int hudHeight){
        g.setFont(fontWrite);
        g.setColor(Color.white);
        g.drawString("SCORE: " + game.level.score, 15, hudHeight * game.tileSize - 4);
    }
}
