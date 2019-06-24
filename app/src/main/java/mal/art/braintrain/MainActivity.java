package mal.art.braintrain;

import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout welcomeLayout;
    ConstraintLayout gameLayout;
    TextView descriptionTextView;
    Button playButton;
    TextView answersTextView;
    TextView taskTextView;
    TextView timerTextView;
    TextView resultTextView;
    Button answerButton1;
    Button answerButton2;
    Button answerButton3;
    Button answerButton4;
    Button playAgainButton;
    CountDownTimer gameTimer;
    int locationOfCorrectAnswer;
    int correctAnswers;
    int numberOfAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameLayout = findViewById(R.id.gameLayout);

        for(int i = 0; i < gameLayout.getChildCount(); i++) {
            View child = gameLayout.getChildAt(i);
            child.setEnabled(false);
        }

        descriptionTextView = findViewById(R.id.descriptionTextView);
        playButton = findViewById(R.id.playButton);

        String description = "You have 20 seconds. You need to answer as much answers as you can. Count window will show number of your correct answers.";

        descriptionTextView.setText(description);

        playButton.setText("GO!");

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playGame();
            }
        });
    }

    public void playGame() {
        correctAnswers = 0;

        answerButton1 = findViewById(R.id.answerButton1);
        answerButton2 = findViewById(R.id.answerButton2);
        answerButton3 = findViewById(R.id.answerButton3);
        answerButton4 = findViewById(R.id.answerButton4);

        answerButton1.setEnabled(true);
        answerButton2.setEnabled(true);
        answerButton3.setEnabled(true);
        answerButton4.setEnabled(true);

        timerTextView = findViewById(R.id.timerTextView);
        playAgainButton = findViewById(R.id.playAgainButton);
        resultTextView = findViewById(R.id.resultTextView);

        welcomeLayout = findViewById(R.id.welcomeLayout);
        for(int i = 0; i < welcomeLayout.getChildCount(); i++) {
            View child = welcomeLayout.getChildAt(i);
            child.setEnabled(false);
        }
        welcomeLayout.setVisibility(View.INVISIBLE);

        gameTimer = new CountDownTimer(20100,  1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int timeLeft = (int) millisUntilFinished / 1000 % 60;
                timerTextView.setText(Integer.toString(timeLeft) + "s");

                if(timeLeft <= 0)
                    onFinish();
            }

            @Override
            public void onFinish() {
                timerTextView.setText("Finish");
                playAgainButton.setText("Play again!");
                playAgainButton.setVisibility(View.VISIBLE);
                playAgainButton.setEnabled(true);
                resultTextView.setVisibility(View.VISIBLE);
                resultTextView.setText("Congratulations, your score is " + correctAnswers);
                answerButton1.setEnabled(false);
                answerButton2.setEnabled(false);
                answerButton3.setEnabled(false);
                answerButton4.setEnabled(false);
            }
        }.start();

        singleTask();
    }

    public void singleTask() {

        ArrayList<Integer> answers = new ArrayList<>();

        Random random = new Random();

        int a = random.nextInt(21);
        int b = random.nextInt(21);

        taskTextView = findViewById(R.id.taskTextView);
        taskTextView.setText(a + " + " + b);

        locationOfCorrectAnswer = random.nextInt(4);

        for(int i = 0; i < 4; i++) {
            if(locationOfCorrectAnswer == i) {
                answers.add(a + b);
            } else {
                int wrongAnswer = random.nextInt(41);
                if(wrongAnswer == a + b) {
                    wrongAnswer = random.nextInt(41);
                }
                answers.add(wrongAnswer);
            }
        }

        answerButton1.setText(Integer.toString(answers.get(0)));
        answerButton2.setText(Integer.toString(answers.get(1)));
        answerButton3.setText(Integer.toString(answers.get(2)));
        answerButton4.setText(Integer.toString(answers.get(3)));
    }

    public void chooseAnswer(View view) {

        answersTextView = findViewById(R.id.answersTextView);

        if(Integer.toString(locationOfCorrectAnswer).equals(view.getTag().toString())) {
            correctAnswers++;
            numberOfAnswers++;
        } else {
            numberOfAnswers++;
        }
        answersTextView.setText(correctAnswers + "/" + numberOfAnswers);

        singleTask();
    }

    public void playAgain(View view) {
        answersTextView.setText("0/0");
        playAgainButton.setVisibility(View.INVISIBLE);
        resultTextView.setVisibility(View.INVISIBLE);
        playGame();
    }
}
