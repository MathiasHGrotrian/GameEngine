package dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;

import java.util.concurrent.TimeUnit;
import dk.kea.class2019january.mathiasg.gameengine.GameEngine;
import dk.kea.class2019january.mathiasg.gameengine.Music;
import dk.kea.class2019january.mathiasg.gameengine.Screen;

public class EndScreen extends Screen
{
    Bitmap background;
    float passedTime = 0;
    Music menuMusic;
    Typeface font;

    public EndScreen(GameEngine gameEngine)
    {
        super(gameEngine);
        background = gameEngine.loadBitmap("ExamGame/Menu/endScreen.png");

        this.menuMusic = gameEngine.loadMusic("ExamGame/Sounds/menuMusic.wav");
        this.menuMusic.setLooping(true);
        this.gameEngine.loadFont("ExamGame/font.ttf");

        gameEngine.setSecondSnapshot(System.nanoTime());

        System.out.println(gameEngine.getFirstSnapshot());
        System.out.println(gameEngine.getSecondSnapshot());
    }

    long timeElapsed = gameEngine.getSecondSnapshot() - gameEngine.getFirstSnapshot();

    /*

        SHOULD BE USED TO FORMAT TIME ELAPSED TO A NICE FORMAT

    String finalTime = String.format("%02d:%02d:%02d",
            TimeUnit.MILLISECONDS.toHours(timeElapsed),
            TimeUnit.MILLISECONDS.toMinutes(timeElapsed) -
                    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeElapsed)),
            TimeUnit.MILLISECONDS.toSeconds(timeElapsed) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeElapsed)));
       */


    @Override
    public void update(float deltaTime)
    {
        menuMusic.play();
        if (gameEngine.isTouchDown(0) && passedTime > 0.5f)
        {
            Log.d("Examgame", "trying to get game screen");
            gameEngine.setScreen(new MainMenuScreen(gameEngine));
            return;
        }

        System.out.println("Final time: " + timeElapsed);
        gameEngine.drawBitmap(background, 0, 0);

        //  NEEDS TO DRAW THE FINAL TIME
        //gameEngine.drawText(, , 380, 20, Color.BLACK, 12);


    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void dispose()
    {
        menuMusic.dispose();
    }
}

