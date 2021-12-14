package lost.macpan.utils;

import lost.macpan.utils.ResourceHandler;

public interface HighscoreHandler {

/*
    default void saveHighscores() {

        try {
            highscores = readStringFromFile("Highscores.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }

        scores = highscores.split("\n");

        if (getScoreVal(scores[9]) < score) {
            highscores = "";
            scores[9] = score+";"+nameInput.getText();
            sortScore(scores);
            for (int i = 0; i < 10; i++) {
                highscores += scores[i]+"\n";
            }
            writeStringToFile("Highscores.txt","");
            writeStringToFile("Highscores.txt",highscores);
        }
        parentFrame.setContentPane(before);
        parentFrame.revalidate();
    }

    private int getScoreVal(String score) {
        if (score.equals("") || score.equals("\n")) return 0;
        String num = score.substring(0, score.indexOf(';'));
        int numInt = Integer.parseInt(num);
        return numInt;
    }

    private void sortScore(String[] scores) {
        int n = 10;
        for (int j = 1; j < n; j++) {
            String temp = scores[j];
            int i = j - 1;
            while ((i > -1) && (getScoreVal(scores[i]) < getScoreVal(temp))) {
                scores[i + 1] = scores[i];
                i--;
            }
            scores[i + 1] = temp;
        }
    }

 */
}
