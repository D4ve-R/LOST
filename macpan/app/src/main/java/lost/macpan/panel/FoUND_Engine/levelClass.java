package lost.macpan.panel.FoUND_Engine;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for level data handover between logic and rendering
 * @author Leon Wigro
 */
public class levelClass {
    /**
     * Array is built as [X-axis, left to right] [Y-axis, top to bottom]. <br>
     * If the map is smaller than 32 * 24 tiles, empty array fields have to be filled with '0'.
     */
    public char[][] map = new char[32][24];
    public int score = 0;
    /**
     * Flag array is built as follows: <br>
     * [0] = player____[true]>alive____[false]>dead <br>
     * [1] = armor (extra life)____[true]>collected____[false]>not collected <br>
     * [2] = speed boost____[true]>active____[false]>inactive <br>
     * [3] = key____[true]>collected____[false]>not collected <br>
     * [4] = pan (death touch)____[true]>active____[false]>inactive <br>
     * [5] = coin booster____[true]>active____[false]>inactive <br>
     * [6] = exit unlock____[true]>locked____[false]>unlocked <br>
     * [7] = tbd____[true]>____[false]>   <br>
     */
    public boolean[] flags = new boolean[8];
    public double timer = 0;
    public double freezeTimer = 0;

    public levelClass(char[][] map, int score, boolean[] flags, double timer, double freezeTimer){
        this.map = map;
        this.score = score;
        this.flags = flags;
        this.timer = timer;
        this.freezeTimer = freezeTimer;
    }
}
