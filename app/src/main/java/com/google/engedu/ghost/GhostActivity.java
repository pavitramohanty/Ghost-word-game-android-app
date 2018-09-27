package com.google.engedu.ghost;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class GhostActivity extends AppCompatActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    TextView ghostText, gameStatus;
    Button bChallenge, bRestart;
    private GhostDictionary dictionary;
    private boolean userTurn = false;
    private Random random = new Random();
    //private SimpleDictionary simpleDictionary;
    FastDictionary simpleDictionary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        ghostText = (TextView) findViewById(R.id.ghostText);
        gameStatus = (TextView) findViewById(R.id.gameStatus);
        bChallenge = (Button) findViewById(R.id.bChallenge);
        bRestart = (Button) findViewById(R.id.bRestart);

        try {
            InputStream is = getAssets().open("words.txt");
            simpleDictionary = new FastDictionary(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                challengeMethod();
            }
        });
        bRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restartMethod();
            }
        });
        onStart(null);
    }

    private void challengeMethod() {
        String text = ghostText.getText().toString();
        if(text.length() >= 4 && simpleDictionary.isWord(text)){
            Toast.makeText(this, "You Win", Toast.LENGTH_SHORT).show();
        }else{
            String word = simpleDictionary.getAnyWordStartingWith(text);
            if(word != null){
                Toast.makeText(this, "Computer Wins. Word is "+ word, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "You Win", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void restartMethod() {
        onStart(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void computerTurn() {
        String temp_text = ghostText.getText().toString();
        if(temp_text.length() >= 4 && simpleDictionary.isWord(temp_text)){
            Toast.makeText(GhostActivity.this, "Computer Wins!!!", Toast.LENGTH_SHORT).show();
            gameStatus.setText("Computer Wins");
        }
        else{
            //String word = simpleDictionary.getAnyWordStartingWith(temp_text);
            String word = simpleDictionary.getAnyWordStartingWith(temp_text);
            if(word == null){
                Toast.makeText(GhostActivity.this, "Computer Wins!!!", Toast.LENGTH_SHORT).show();
            }
            else{
                temp_text += word.charAt(temp_text.length());
                ghostText.setText(temp_text);
            }
        }
        userTurn = true;
        gameStatus.setText(USER_TURN);
    }

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     *
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        userTurn = random.nextBoolean();
        TextView text = (TextView) findViewById(R.id.ghostText);
        text.setText("");
        TextView label = (TextView) findViewById(R.id.gameStatus);
        if (userTurn) {
            label.setText(USER_TURN);
        } else {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        char ch = (char) event.getUnicodeChar();
        if (ch >= 97 && ch <= 122) {
            String currentText = ghostText.getText().toString();
            String newText = currentText + ch;
            ghostText.setText(newText);
            gameStatus.setText(COMPUTER_TURN);
            userTurn = false;
            computerTurn();
            return true;
        }
        else{
            Toast.makeText(GhostActivity.this, "Invalid letter. Enter from a-z only", Toast.LENGTH_SHORT).show();
        }
        return super.onKeyUp(keyCode, event);
    }
}
