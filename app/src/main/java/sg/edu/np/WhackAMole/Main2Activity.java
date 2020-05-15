package sg.edu.np.WhackAMole;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Main2Activity extends AppCompatActivity {
  /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 8.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The functions readTimer() and placeMoleTimer() are to inform the user X seconds before starting and loading new mole.
        - Feel free to modify the function to suit your program.
    */
  private static final String TAG = "Whack-A-Mole 2.0";
  int advancedScore;
  boolean flag;
  TextView scoreView;
  Button mole;

  private void readyTimer() {
    /*  HINT:
            The "Get Ready" Timer.
            Log.v(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
            Toast message -"Get Ready In X seconds"
            Log.v(TAG, "Ready CountDown Complete!");
            Toast message - "GO!"
            belongs here.
            This timer countdown from 10 seconds to 0 seconds and stops after "GO!" is shown.
         */

    new CountDownTimer(10000, 1000) {

      @Override
      public void onTick(long millisUntilFinished) {
        Log.v(TAG, "Ready CountDown!" + millisUntilFinished / 1000);
        Toast
          .makeText(
            getApplicationContext(),
            String.valueOf(millisUntilFinished / 1000),
            Toast.LENGTH_SHORT
          )
          .show();
      }

      @Override
      public void onFinish() {
        Log.v(TAG, "Ready CountDown Complete!");
        Toast
          .makeText(getApplicationContext(), "GO!", Toast.LENGTH_SHORT)
          .show();

        placeMoleTimer();
        flag = true;
      }
    }
    .start();

  }

  private void placeMoleTimer() {
    /* HINT:
           Creates new mole location each second.
           Log.v(TAG, "New Mole Location!");
           setNewMole();
           belongs here.
           This is an infinite countdown timer.
         */
    new CountDownTimer(Long.MAX_VALUE, 1000) {

      @Override
      public void onTick(long millisUntilFinished) {
        Log.v(TAG, "New Mole Location!");
        setNewMole();
      }

      @Override
      public void onFinish() {}
    }
    .start();
  }

  private static final int[] BUTTON_IDS = {
    R.id.a_button1,
    R.id.a_button2,
    R.id.a_button3,
    R.id.a_button4,
    R.id.a_button5,
    R.id.a_button6,
    R.id.a_button7,
    R.id.a_button8,
    R.id.a_button9,
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    /*Hint:
            This starts the countdown timers one at a time and prepares the user.
            This also prepares the existing score brought over.
            It also prepares the button listeners to each button.
            You may wish to use the for loop to populate all 9 buttons with listeners.
         */

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main2);

    advancedScore = getIntent().getExtras().getInt("Score");
    scoreView = findViewById(R.id.textView);
    scoreView.setText(String.valueOf(advancedScore));
    Log.v(TAG, "Current User Score: " + String.valueOf(advancedScore));

    readyTimer();
    setNewMole();
    flag = false;
    for (final int id : BUTTON_IDS) {
      final Button button = findViewById(id);
      button.setOnClickListener(
        new View.OnClickListener() {

          @Override
          public void onClick(View view) {
            if (flag) {
              doCheck(button);
            } else {
              Toast
                .makeText(
                  getApplicationContext(),
                  "Please wait till countdown",
                  Toast.LENGTH_SHORT
                )
                .show();
            }
          }
        }
      );
    }
  }

  @Override
  protected void onStart() {
    super.onStart();
  }

  private void doCheck(Button checkButton) {
    /* Hint:
            Checks for hit or miss
            Log.v(TAG, "Hit, score added!");
            Log.v(TAG, "Missed, point deducted!");
            belongs here.
        */
    if (mole == checkButton) {
      advancedScore++;
      Log.v(TAG, "Hit, score added!");
    } else {
      if (advancedScore > 0) {
        advancedScore--;
      }
      Log.v(TAG, "Missed, score deducted!");
    }
    scoreView.setText(String.valueOf(advancedScore));
  }

  public void setNewMole() {
    /* Hint:
            Clears the previous mole location and gets a new random location of the next mole location.
            Sets the new location of the mole.
         */
    Random ran = new Random();
    int randomLocation = ran.nextInt(9);
    Log.v(TAG, String.valueOf(randomLocation));
    mole = findViewById(BUTTON_IDS[randomLocation]);
    mole.setText("*");

    //Set the empty in the buttons
    for (final int i : BUTTON_IDS) {
      final Button button = findViewById(i);
      if (button != mole) {
        button.setText("O");
      }
    }
  }
}
