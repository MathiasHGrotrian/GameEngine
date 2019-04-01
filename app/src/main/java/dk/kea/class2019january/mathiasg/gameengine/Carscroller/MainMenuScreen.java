package dk.kea.class2019january.mathiasg.gameengine.Carscroller;

import android.graphics.Bitmap;

import dk.kea.class2019january.mathiasg.gameengine.GameEngine;
import dk.kea.class2019january.mathiasg.gameengine.Screen;

public class MainMenuScreen extends Screen
{
    Bitmap background;
    Bitmap startGame;
    float passedTime = 0;
    long starttime;

    public MainMenuScreen(GameEngine gameEngine)
    {
        super(gameEngine);
        background = gameEngine.loadBitmap("carscroller/xcarbackground.png");
        startGame = gameEngine.loadBitmap("carscroller/xstartgame.png");
        starttime = System.nanoTime();
    }

    @Override
    public void update(float deltaTime)
    {
        if (gameEngine.isTouchDown(0) && (passedTime) > 0.5f)
        {
            gameEngine.setScreen(new GameScreen(gameEngine));
            return;
        }
        gameEngine.drawBitmap(background, 0, 0);

        passedTime = passedTime + deltaTime;
        if (    (passedTime - (int) passedTime) > 0.5f  )
        {
            gameEngine.drawBitmap(startGame,240 - startGame.getWidth()/2, 160);
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
