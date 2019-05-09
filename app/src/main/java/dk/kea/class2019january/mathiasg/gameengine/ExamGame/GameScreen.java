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

    World world = null;
    WorldRenderer worldRenderer = null;


    public GameScreen(GameEngine gameEngine)
    {
        super(gameEngine);
        Log.d("Examgame", "Starting the game");


        this.background = gameEngine.loadBitmap("ExamGame/sky.png");
        this.floatingRock = gameEngine.loadBitmap("ExamGame/floatingrock.png");
        this.ground = gameEngine.loadBitmap("ExamGame/ground.png");

        this.world = new World(gameEngine);
        this.worldRenderer = new WorldRenderer(gameEngine, world);

    }

    @Override
    public void update(float deltaTime)
    {

        //what is srcX srcY?
        gameEngine.drawBitmap(background, 0, 0, 0, 0, 480, 320);
        gameEngine.drawBitmap(ground, -100, 235);


        world.update(deltaTime);
        worldRenderer.render();

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
