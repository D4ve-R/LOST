package lost.macpan.utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public interface HighscoreHandler extends ResourceHandler {

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

    default void saveNewScore(int pScore, String pName) {
        List<HighscoreEntry> liste = new LinkedList<>();

        String[] allHighscores = getHighscoresAsArray();

        for(String s: allHighscores){
            String[] temp = s.split(";");
            liste.add(new HighscoreEntry(Integer.parseInt(temp[0]),temp[1]));
        }

        liste.add(new HighscoreEntry(pScore, pName));
        liste.sort(Comparator.comparing(HighscoreEntry::getScore).reversed());

        StringBuilder highscoresNew = new StringBuilder();
        for (int i = 0; i < Math.min(liste.size(), 10); i++) {
            highscoresNew.append(liste.get(i) + "\n");
        }

        writeStringToFile("Highscores.txt",highscoresNew.toString());

    }

    default String[] getHighscoresAsArray(){
        String  AllHighScoresString = "";

        try {
            AllHighScoresString = readStringFromFile("Highscores.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] scores = AllHighScoresString.split("\n");

        for(int i = 0; i < Math.min(scores.length, 10); i++){
            scores[i] = scores[i].replaceAll("\r","");
            scores[i] = scores[i].replaceAll("\n","");
        }
        return scores;

    }

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
