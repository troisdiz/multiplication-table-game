package com.troisdizaines.tablemultiplicationgame;

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

        String questionCountStr = console.promptAndGetLine("Combien de questions ? (10)\n");
        int questionCount = DEFAULT_QUESTION_COUNT;
        try {
            String trimedQuestionCountStr = questionCountStr.trim();
            if (!trimedQuestionCountStr.isEmpty()) {
                questionCount = Integer.parseInt(trimedQuestionCountStr);
            }
        } catch (NumberFormatException e) {
            console.printf(
                    "Je ne comprends pas ta réponse (%s) alors je vais poser %d questions\n\n",
                    questionCountStr,
                    DEFAULT_QUESTION_COUNT);
        }

        List<MulQuestion> log = new ArrayList<>(questionCount);

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

            log.add(new MulQuestion(n1, n2, answer, resultOK, duration));
        }

        long goodAnswers = log.stream().filter(MulQuestion::isResultOK).count();
        long totalTime = log.stream().mapToLong(MulQuestion::getTime).sum();

        double totalDurationSec = totalTime / 1000000000d;

        String conclusion = "Tu as donné %d bonnes réponses (sur %d questions) en %2.2f secondes\n";
        console.promptAndGetLine(conclusion, goodAnswers, questionCount, totalDurationSec);
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
