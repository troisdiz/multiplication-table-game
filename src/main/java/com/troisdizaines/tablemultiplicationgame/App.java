package com.troisdizaines.tablemultiplicationgame;

import com.troisdizaines.tablemultiplicationgame.scores.QuestionLog;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class App {

    public static void main(String[] args ) {

        ConsoleAbstraction console = new RealConsole();
        App app = new App(console);
        app.play();
    }

    static final int DEFAULT_QUESTION_COUNT = 10;

    private Random random;
    private ConsoleAbstraction console;

    public App(ConsoleAbstraction console) {
        random = new Random();
        this.console = console;
    }

    public void play() {

        String introductionMessage =
                "Bienvenue dans le jeu des tables de multiplication !\n"
                + "À vous de jouer !\n\n";

        console.printf(introductionMessage);

        String nameQuestionPrompt = "Quel est on nom ?\n";
        String playerName = console.promptAndGetLine(nameQuestionPrompt);

        String questionCountStr = console.promptAndGetLine("À combien de questions veux-tu répondre ? (10)\n");
        int questionCount = DEFAULT_QUESTION_COUNT;
        try {
            String trimedQuestionCountStr = questionCountStr.trim();
            if (!trimedQuestionCountStr.isEmpty()) {
                questionCount = Integer.parseInt(trimedQuestionCountStr);
            }
        } catch (NumberFormatException e) {
            console.printf(
                    "%s, Je ne comprends pas ta réponse (%s) alors je vais te poser %d questions\n\n",
                    playerName,
                    questionCountStr,
                    DEFAULT_QUESTION_COUNT);
        }

        String readyMessage = "Prêt ? Appuie sur Entrée pour commencer !\n";
        console.promptAndGetLine(readyMessage);

        LocalDateTime startDateTime = LocalDateTime.now();

        List<QuestionLog> log = new ArrayList<>(questionCount);

        for (int i = 0; i < questionCount; i++) {

            // random numbers
            int n1 = randInt(1, 10);
            int n2 = randInt(1, 10);

            // timer start
            long start = System.nanoTime();

            // ask question
            String fmt  = "%2d x %2d\n> ";
            String answer = console.promptAndGetLine(fmt, n1, n2);

            // timer end
            long end = System.nanoTime();

            // check result
            boolean resultOK = false;
            String displayResultOK = null;
            try {
                int result = Integer.parseInt(answer.trim());
                resultOK = (n1*n2 == result);
                displayResultOK = resultOK ? "VRAI" : "FAUX";
            } catch (NumberFormatException e) {
                displayResultOK = "Je ne comprends pas ta réponse donc c'est FAUX";
            }

            // accumulate log
            long duration = end - start;

            console.printf(displayResultOK + "\n\n");

            log.add(new QuestionLog(n1, n2, answer, resultOK, duration));
        }

        long goodAnswers = log.stream().filter(QuestionLog::isResultOK).count();
        long totalTime = log.stream().mapToLong(QuestionLog::getTime).sum();

        double totalDurationSec = totalTime / 1000000000d;

        String conclusion = "%s, tu as donné %d bonnes réponses (sur %d questions) en %2.2f secondes\n";
        console.promptAndGetLine(conclusion, playerName, goodAnswers, questionCount, totalDurationSec);
    }

    private int randInt(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }



    private int[] generateNumbers(int max) {
        int n1 = randInt(0, max);
        int n2 = randInt(0, 10);
        return new int[] { n1, n2 };
    }


}
