package lost.macpan.game;

/**
 * Class for level data handover between logic and rendering
 * @author Leon Wigro
 * @version 1.0
 */
public class LevelClass {
    /**
     * Array is built as [X-axis, left to right] [Y-axis, top to bottom]. <br>
     * Empty path tiles (where the coin has already been collected) have to be filled with '.'. <br>
     * Any arrays bigger than "maximumColumns" * "maximumRows" (as defined in GameWindow) will not be displayed.
     */
    public char[][] map;
    public int score;
    /**
     * Flag array is built as follows: <br>
     * [0] = player____[true]>alive____[false]>dead <br>
     * [1] = armor (extra life)____[true]>collected____[false]>not collected <br>
     * [2] = speed boost____[true]>active____[false]>inactive <br>
     * [3] = key____[true]>collected____[false]>not collected <br>
     * [4] = pan (death touch)____[true]>active____[false]>inactive <br>
     * [5] = coin booster____[true]>active____[false]>inactive <br>
     * [6] = exit unlock____[true]>locked____[false]>unlocked <br>
     * [7] = enemy freeze____[true]>active____[false]>inactive   <br>
     */
    public boolean[] flags;
    public double timer; //in case a timer is to be displayed, not yet implemented in GameWindow
    public double freezeTimer; //in case a timer for an ongoing freeze timer is to be displayed

    public LevelClass(char[][] map, int score, boolean[] flags, double timer, double freezeTimer){
        this.map = map;
        this.score = score;
        this.flags = flags;
        this.timer = timer;
        this.freezeTimer = freezeTimer;
    }
}
