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
    Music menuMusic;
    Typeface font;
    long timeElapsed;

    public EndScreen(GameEngine gameEngine)
    {
        super(gameEngine);
        background = gameEngine.loadBitmap("ExamGame/Menu/endScreen.png");
        this.menuMusic = gameEngine.loadMusic("ExamGame/Sounds/menuMusic.wav");
        this.menuMusic.setLooping(true);
        this.font = this.gameEngine.loadFont("ExamGame/enchanted_land.otf");
        gameEngine.secondSnapshot = System.currentTimeMillis();
        this.timeElapsed = gameEngine.secondSnapshot - gameEngine.firstSnapshot;
        timeElapsed = TimeUnit.MILLISECONDS.toSeconds(timeElapsed);
    }

    @Override
    public void update(float deltaTime)
    {
        menuMusic.play();
        if (gameEngine.isTouchDown(0))
        {
            Log.d("Examgame", "trying to get game screen");
            gameEngine.setScreen(new MainMenuScreen(gameEngine));
            return;
        }

        gameEngine.drawBitmap(background, 0, 0);

        gameEngine.drawText(font, timeElapsed + " seconds", 161, 201, Color.BLACK, 40);
        gameEngine.drawText(font, timeElapsed + " seconds", 160, 200, Color.YELLOW, 40);

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

