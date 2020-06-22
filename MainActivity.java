package com.example.flappybird;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    GameView gameView;
    GameViewTwo gameViewTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Startgame(View home) {
        Intent intent = new Intent(MainActivity.this, StartGame.class);
        startActivity(intent);

    }

    public void StartgameBird(View home) {
        gameView = new GameView(this);
        setContentView (gameView);

    }
    public void StartgamePiegon(View home) {
        gameViewTwo = new GameViewTwo(this);
        setContentView (gameViewTwo);
    }

}