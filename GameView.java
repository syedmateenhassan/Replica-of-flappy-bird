package com.example.flappybird;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.core.view.MotionEventCompat;

import java.util.Random;

class GameView extends View {

    public TextView scoreText;
    private Paint myPaint = new Paint();

    Handler handler; // handler is required to schedule a rumble after some delay
    Runnable runnable;
    final int UPDATE_MILLIS = 30;

    Bitmap background;
    Bitmap topTube,bottomTube;
    Bitmap egg;

    Display display;
    Point point;

    int dwidth, dheight; //device width and height respectively

    Rect rect; //lets create a bitmap array for the bird

    Bitmap[] birds; // we need an integer variable to keep track of bird image

    int birdFrame = 0;
    int velocity = 0, gravity = 3; //lets play around with these valiues //we need to keep track ofn birds position
    int birdX, birdY;

    boolean gameState = false;

    int gap = 400;
    int minTubeOffset, maxTubeOffset;
    int numberOfTubes = 300;
    int numEggs = 40;
    int minEggs,maxEggs;
    int eatenEggs = 0;
    int distanceBetweenTubes;
    int[] tubeX = new int[numberOfTubes];
    int[] topTubeY = new int[numberOfTubes];
    int[] eggs = new int[numEggs];
    int eggX,eggY;
    Random random;
    int tubeVelocity = 8;
    private int score;


    public GameView(Context context) {
        super(context);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };

        background = BitmapFactory.decodeResource(getResources(),R.drawable.bg);
        topTube = BitmapFactory.decodeResource(getResources(),R.drawable.pipe);
        bottomTube = BitmapFactory.decodeResource(getResources(),R.drawable.pipeblack);
        egg = BitmapFactory.decodeResource(getResources(),R.drawable.egg);
        myPaint.setColor(Color.BLUE);
        myPaint.setTextSize(42);

        display = ((Activity)getContext()).getWindowManager().getDefaultDisplay();
        point = new Point();
        display.getSize(point);
        dwidth = point.x;
        dheight = point.y;

        rect = new Rect(0,0,dwidth,dheight);

        birds = new Bitmap[2];
        birds[0] = BitmapFactory.decodeResource(getResources(),R.drawable.bird);
        birds[1] = BitmapFactory.decodeResource(getResources(),R.drawable.birdwhite);

        birdX = dwidth/2 - birds[0].getWidth()/2;
        birdY = getHeight()/2- birds[0].getHeight()/2;
        distanceBetweenTubes = dwidth*3/4;
        eatenEggs = 0;
        int y = 0;
        minEggs = numEggs/2;
        maxEggs = minEggs + 5;
        minTubeOffset = gap/2;
        maxTubeOffset = dheight - minTubeOffset - gap;
        random = new Random();

        for (int i = 0; i<numberOfTubes; i++)
        {
            tubeX[i] = dwidth + i*distanceBetweenTubes;
            topTubeY[i] = minTubeOffset + random.nextInt(maxTubeOffset - minTubeOffset+1);
        }

    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        scoreText = (TextView)findViewById(R.id.scoring);
        canvas.drawBitmap(background,null,rect,null);
        canvas.drawText("Score: "+ score,100,100,myPaint);
        if(birdFrame == 0)
        {
            birdFrame = 1;
        }
        else{
            birdFrame = 0;
        }
        if(gameState)
        {
            int j = 10;
            if(birdY<dheight-birds[0].getHeight()|| velocity<0 )
            {
                velocity += gravity;
                birdY += velocity;
            }
            if(hitCheck(eggX,eggY))
            {
                score += 10;
                canvas.drawText("Score: "+ score,20,60, myPaint);
                Log.d("Scores "+score, "okayaa"+scoreText);
                scoreText.setText(score);
            }
            for(int y = 0; y<numberOfTubes; y++)
            {
                tubeX [y] -= tubeVelocity;
                canvas.drawBitmap(topTube,tubeX[y],topTubeY[y] - topTube.getHeight(),null);
                canvas.drawBitmap(egg,tubeX[y]+150, topTubeY[y] + 85,null);
                canvas.drawBitmap(bottomTube,tubeX[y],topTubeY[y] + gap,null);
            }
        }

        canvas.drawBitmap(birds[birdFrame],birdX,birdY,null);
        handler.postDelayed(runnable,UPDATE_MILLIS);
    }

    public  boolean hitCheck (int xy,int yx)
    {
        if(birdX < xy && xy < (birdX + birds[1].getWidth()) && birdY < yx && yx < (birdY + birds[1].getHeight()))
        {
            return true;
        }
//            for(int z = 0; z <= 1; z++)
//        {
//            if(birdX < x && x < (birdX + birds[z].getWidth()) && birdY < y && y < (birdY + birds[z].getHeight()))
//                return true;
//        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN)
        {
            velocity = -30;
            gameState = true;
            score += 10;
        }
        return true;
    }
}