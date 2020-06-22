package com.example.flappybird;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

public class StartGame2 extends Activity {
    GameViewTwo gameViewTwo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameViewTwo = new GameViewTwo(this);
        setContentView (gameViewTwo);

    }
}
