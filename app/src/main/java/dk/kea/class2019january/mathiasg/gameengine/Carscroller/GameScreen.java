package dk.kea.class2019january.mathiasg.gameengine.Carscroller;

import android.graphics.Bitmap;
import android.util.Log;

import dk.kea.class2019january.mathiasg.gameengine.GameEngine;
import dk.kea.class2019january.mathiasg.gameengine.Screen;
import dk.kea.class2019january.mathiasg.gameengine.Sound;

public class GameScreen extends Screen
{
    enum State
    {
        Paused,
        Running,
        GameOver
    }

    Bitmap background = null;
    float backgroundX = 0;
    Bitmap resume = null;
    Bitmap gameover = null;
    Sound bounce = null;
    Sound crash = null;
    Sound gameOverSound = null;


    World world = null;
    WorldRenderer worldRenderer = null;
    State state = State.Running;

    public GameScreen(GameEngine gameEngine)
    {
        super(gameEngine);
        Log.d("Carscroller", "Starting the game");

        this.background = gameEngine.loadBitmap("carscroller/xcarbackground.png");
        this.resume = gameEngine.loadBitmap("carscroller/resume.png");
        this.gameover = gameEngine.loadBitmap("carscroller/gameover.png");
        this.bounce = gameEngine.loadSound("carscroller/bounce.wav");
        this.crash = gameEngine.loadSound("carscroller/blocksplosion.wav");
        this.gameOverSound = gameEngine.loadSound("carscroller/gameover.wav");

        this.world = new World(gameEngine, new CollisionListener()
        {
            @Override
            public void collisionRoadside()
            {
                bounce.play(1);
            }

            @Override
            public void collisionMonster()
            {
                crash.play(1);
            }

            @Override
            public void gameOver()
            {
                gameOverSound.play(1);
            }
        });

        this.worldRenderer = new WorldRenderer(gameEngine, world);
    }

    @Override
    public void update(float deltaTime)
    {
        if(state == State.Running)
        {
            backgroundX = backgroundX + 100 * deltaTime;

            if(backgroundX > 2700 - 480)
            {
                backgroundX = 0;
            }
        }

        gameEngine.drawBitmap(background, 0, 0, (int)backgroundX, 0, 480, 320);
        world.update(deltaTime, gameEngine.getAccelerometer()[1]);
        worldRenderer.render();
    }

    @Override
    public void pause()
    {
        if(state == State.Running) state = State.Paused;
        gameEngine.music.pause();

    }

    @Override
    public void resume()
    {
        gameEngine.music.play();
    }

    @Override
    public void dispose()
    {

    }
}
