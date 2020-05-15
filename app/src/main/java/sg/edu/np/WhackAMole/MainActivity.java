package sg.edu.np.WhackAMole;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final  String TAG = "Whack-A-Mole 1.0";
    private  static  String[]  buttonLogs = {"Button Left CLicked" , "Button Middle CLicked" , "Button Right CLicked"} ;
    private static int[] basicButtons = {R.id.button1 , R.id.button2 ,R.id.button3};
    int score = 0;
    TextView scoreView;
    Button mole;
    int randomLocation;
    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 2.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The function doCheck() also decides if the user qualifies for the advance level and triggers for a dialog box to ask for user to decide.
        - The function nextLevelQuery() builds the dialog box and shows. It also triggers the nextLevel() if user selects Yes or return to normal state if user select No.
        - The function nextLevel() launches the new advanced page.
        - Feel free to modify the function to suit your program.
    */
    // put listener here
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreView = (TextView) findViewById(R.id.textView1);
        scoreView.setText(String.valueOf(score));

        Log.v(TAG, "Finished Pre-Initialisation!");

        for (final int i : basicButtons){
            final Button button = findViewById(i);
            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    doCheck(button);
                }
            });
        }


    }
    @Override
    protected void onStart(){
        super.onStart();
        setNewMole();
        Log.v(TAG, "Starting GUI!");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.v(TAG, "Paused Whack-A-Mole!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "Stopped Whack-A-Mole!");
        finish();
    }

    private void doCheck(Button checkButton) {
        /* Checks for hit or miss and if user qualify for advanced page.
            Triggers nextLevelQuery().
         */
        if(mole == checkButton){
            score++;
            Log.v(TAG, "Hit, score added!");
        }else{
            if (score > 0){
                score--;
            }
            Log.v(TAG, "Missed, score deducted!");
        }

        Log.v(TAG, buttonLogs[randomLocation]);
        scoreView.setText(String.valueOf(score));

        if(score%10 == 0 && score != 0){
            nextLevelQuery();
        }else{
            setNewMole();
        }
    }

    private void nextLevelQuery(){
        /*
        Builds dialog box here.
        Log.v(TAG, "User accepts!");
        Log.v(TAG, "User decline!");
        Log.v(TAG, "Advance option given to user!");
        belongs here*/
        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        //Set dialog Characteristic
        builder.setTitle("Warning! Insane Whack-A-Mole Incoming!").setMessage("Would you like to advance to advanced mode?");

        //Set Yes buttons
        builder.setPositiveButton("YES" , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.v(TAG, "User accepts!");
                nextLevel();
            }
        });

        //Set No buttons
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.v(TAG, "User decline!");
                setNewMole();
            }
        });

        //Create alert Dialog
        AlertDialog dialog = builder.create();

        dialog.show();
    }

    private void nextLevel(){
        /* Launch advanced page */
        Intent advanceLevel = new Intent(this,Main2Activity.class);
        advanceLevel.putExtra("Score",score);

        startActivity(advanceLevel);
    }

    private void setNewMole() {
        Random ran = new Random();
        randomLocation = ran.nextInt(3);
        mole = findViewById(basicButtons[randomLocation]);
        mole.setText("*");

        for (final int i:
                basicButtons) {
            final Button button = findViewById(i);
            if(button != mole){
                button.setText("O");
            }

        }


    }
}