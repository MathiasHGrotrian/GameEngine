package dk.kea.class2019january.mathiasg.gameengine.ExamGame;

import android.graphics.Bitmap;
import android.util.Log;

import dk.kea.class2019january.mathiasg.gameengine.GameEngine;
import dk.kea.class2019january.mathiasg.gameengine.Screen;

public class GameScreen extends Screen
{
    enum State
    {
        Paused,
        Running,
        GameOver
    }

    Bitmap background;
    Bitmap floatingRock;
    Bitmap ground;
    Bitmap player;

    public GameScreen(GameEngine gameEngine)
    {
        super(gameEngine);
        Log.d("Examgame", "Starting the game");

        this.background = gameEngine.loadBitmap("ExamGame/sky.png");
        this.floatingRock = gameEngine.loadBitmap("ExamGame/floatingrock.png");
        this.ground = gameEngine.loadBitmap("ExamGame/ground.png");
        this.player = gameEngine.loadBitmap("ExamGame/player.png");

    }

    @Override
    public void update(float deltaTime)
    {   //what is srcX srcY?
        gameEngine.drawBitmap(background, 0, 0, 0, 0, 480, 320);

        //gameEngine.drawBitmap(floatingRock, 200, 130);

        //gameEngine.drawBitmap(player, 50, (300 - ground.getHeight()));

        gameEngine.drawBitmap(ground, 0, 220);
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
