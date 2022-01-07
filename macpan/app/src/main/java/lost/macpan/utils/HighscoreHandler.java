/*
 * MacPan version 0.1
 * SWE WS 21/22
 * @author Sebastian
 */

package lost.macpan.utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;


/**
 * HighscoreHandler interface to handle Highscore saving/adding and loading from file
 */
public interface HighscoreHandler extends ResourceHandler {

    /**
     * Class that represents one highscore entry
     */
    class HighscoreEntry{
        private int score;
        private String name;

        public HighscoreEntry(int pScore, String pName){
            this.score = pScore;
            this.name = pName;
        }

        public int getScore() {
            return score;
        }

        public String getName() {
            return name;
        }

        public String toString (){
            return score + ";" + name;
        }
    }

    /**
     * takes a score and adds it to the Highscores file. Only the ten best Highscores are saved.
     * @param pScore the score to be saved as int
     * @param pName the name associated with the score
     */
    default void saveNewScore(int pScore, String pName) {
        if(pName.isEmpty()){
            return;
        }

        List<HighscoreEntry> highscoreListe = new LinkedList<>();

        String[] allHighscores = getHighscoresAsArray();

        for(String s: allHighscores){
            String[] temp = s.split(";");
            highscoreListe.add(new HighscoreEntry(Integer.parseInt(temp[0]),temp[1]));
        }

        highscoreListe.add(new HighscoreEntry(pScore, pName));
        highscoreListe.sort(Comparator.comparing(HighscoreEntry::getScore).reversed());

        StringBuilder highscoresNew = new StringBuilder();
        for (int i = 0; i < Math.min(highscoreListe.size(), 10); i++) {
            highscoresNew.append(highscoreListe.get(i) + "\n");
        }

        writeStringToFile("Highscores.txt",highscoresNew.toString());

    }

    /**
     * Loads the Highscore File from Disk and returns it as a String[] split at every new line.
     * @return an array of Highscore Strings
     */
    default String[] getHighscoresAsArray(){
        String  AllHighScoresString = "";

        try {
            AllHighScoresString = readStringFromFile("Highscores.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] scores = AllHighScoresString.split("\n");
        scores = Arrays.copyOfRange(scores, 0, Math.min(scores.length, 10));

        for(int i = 0; i < scores.length; i++){
            scores[i] = scores[i].replaceAll("\r","");
            scores[i] = scores[i].replaceAll("\n","");
        }
        return scores;

    }

    /**
     * Class used for limiting the amount of characters that can be typed into the text field.
     */
    class LimitJTextField extends PlainDocument
    {
        private int max;
        public LimitJTextField(int max) {
            super();
            this.max = max;
        }

        public LimitJTextField(int max, boolean upper) {
            super();
            this.max = max;
        }

        public void insertString(int offset, String text, AttributeSet attr) throws BadLocationException {
            if (text == null)
                return;
            if ((getLength() + text.length()) <= max) {
                super.insertString(offset, text, attr);
            }
        }
    }


}
