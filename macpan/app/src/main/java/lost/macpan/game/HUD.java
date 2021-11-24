package lost.macpan.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class HUD {
    //attributes
    GameWindow game;
    Font hudFont = new Font("Arial", Font.BOLD, 28);

    public HUD (GameWindow game){
        this.game = game;
    }

    public void draw(Graphics2D g){
        g.setFont(hudFont);
        g.setColor(Color.white);
        g.drawString("SCORE: " + game.level.score, 20, 20);
    }
}
