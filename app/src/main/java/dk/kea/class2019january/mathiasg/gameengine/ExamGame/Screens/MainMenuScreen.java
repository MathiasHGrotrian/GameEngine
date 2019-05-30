package dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens;

import android.graphics.Bitmap;
import android.util.Log;

import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.FirstLevel.FirstLevel;
import dk.kea.class2019january.mathiasg.gameengine.GameEngine;
import dk.kea.class2019january.mathiasg.gameengine.Screen;

public class MainMenuScreen extends Screen
{
    Bitmap background;
    Bitmap pressToPlay;
    float passedTime = 0;

    public MainMenuScreen(GameEngine gameEngine)
    {
        super(gameEngine);
        background = gameEngine.loadBitmap("ExamGame/Menu/mainMenu.png");
        pressToPlay = gameEngine.loadBitmap("ExamGame/Menu/pressToPlay.png");

    }

    @Override
    public void update(float deltaTime)
    {
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

    }
}
