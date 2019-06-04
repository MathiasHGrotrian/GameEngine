package dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens;

import android.graphics.Bitmap;
import android.util.Log;

import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.FirstLevel.FirstLevel;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.SecondLevel.SecondLevel;
import dk.kea.class2019january.mathiasg.gameengine.GameEngine;
import dk.kea.class2019january.mathiasg.gameengine.Music;
import dk.kea.class2019january.mathiasg.gameengine.Screen;

public class MainMenuScreen extends Screen
{
    Bitmap background;
    Bitmap pressToPlay;
    float passedTime = 0;
    Music menuMusic;
    public MainMenuScreen(GameEngine gameEngine)
    {
        super(gameEngine);
        background = gameEngine.loadBitmap("ExamGame/Menu/mainMenu.png");
        pressToPlay = gameEngine.loadBitmap("ExamGame/Menu/pressToPlay.png");
        this.menuMusic = gameEngine.loadMusic("ExamGame/Sounds/menuMusic.wav");
        this.menuMusic.setLooping(true);
    }

    @Override
    public void update(float deltaTime)
    {
        menuMusic.play();
        if (gameEngine.isTouchDown(0) && passedTime > 0.5f)
        {
            Log.d("Examgame", "trying to get game screen");
            gameEngine.setScreen(new FirstLevel(gameEngine));
            return;
        }

        gameEngine.drawBitmap(background, 0, 0);

        passedTime += deltaTime;
        if((passedTime - (int)passedTime) > 0.5f)
        {
            gameEngine.drawBitmap(pressToPlay, 0,0);
        }

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
