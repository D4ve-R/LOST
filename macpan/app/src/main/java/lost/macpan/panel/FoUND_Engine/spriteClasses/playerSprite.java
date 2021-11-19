package lost.macpan.panel.FoUND_Engine.spriteClasses;

import lost.macpan.panel.FoUND_Engine.FoUND_Engine;

import java.awt.*;
import java.awt.image.BufferedImage;

public class playerSprite extends sprite{
    FoUND_Engine fnd;

    public playerSprite (FoUND_Engine fnd){
        this.fnd = fnd;
    }

    public void draw(Graphics2D g){
        g.setColor(Color.white);
        g.fillRect(100, 132, fnd.tileSize, fnd.tileSize);
        g.drawImage(playerSpriteSelect(), 132, 100, fnd.tileSize, fnd.tileSize, null);
    }

    /**
     * selects a sprite to b used based on animation cycle and returns it
     * @return player sprite
     */
    public BufferedImage playerSpriteSelect(){
        long timeWithinSecond = System.currentTimeMillis() % 1000;
        if (timeWithinSecond < 500){
            if (timeWithinSecond < 250)
                return fnd.player1;
            else
                return fnd.player2;
        }
        else if (timeWithinSecond < 750)
            return fnd.player3;
        else
            return fnd.player4;
    }
}
