package dk.kea.class2019january.mathiasg.gameengine.Carscroller;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.List;

import dk.kea.class2019january.mathiasg.gameengine.GameEngine;
import dk.kea.class2019january.mathiasg.gameengine.Screen;
import dk.kea.class2019january.mathiasg.gameengine.Sound;
import dk.kea.class2019january.mathiasg.gameengine.TouchEvent;

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
    int backGroundSpeed = 750;

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
        }, backGroundSpeed);

        this.worldRenderer = new WorldRenderer(gameEngine, world);
    }

    @Override
    public void update(float deltaTime)
    {
        if(world.gameOver)
        {
            state = State.GameOver;
        }

        if(state == State.Paused && gameEngine.getTouchEvents().size() > 0)
        {
            Log.d("FirstLevel", "Starting the game again");
            state = State.Running;
            resume();
        }

        if(state == State.GameOver)
        {
            Log.d("FirstLevel", "Game Over");
            List<TouchEvent> events = gameEngine.getTouchEvents();
            for(int i = 0; i < events.size(); i++)
            {
                if(events.get(i).type == TouchEvent.TouchEventType.Up)
                {
                    gameEngine.setScreen(new MainMenuScreen(gameEngine));

                    return;
                }
            }
        }

        if(state == State.Running && gameEngine.getTouchY(0) < 40
                && gameEngine.getTouchX(0) > 320 - 40)
        {
            Log.d("FirstLevel", "Pausing the game");
            state = State.Paused;
            pause();
        }

        if(state == State.Running)
        {
            backgroundX = backgroundX + backGroundSpeed * deltaTime;

            if(backgroundX > 2700 - 480)
            {
                backgroundX = 0;
            }

            // Update the game objects
            world.update(deltaTime, gameEngine.getAccelerometer()[1]);
        }

        // Draws the background regardless of state
        gameEngine.drawBitmap(background, 0, 0, (int)backgroundX, 0, 480, 320);
        // Draws the game objects regardless of state
        worldRenderer.render();

        if(state == State.Paused)
        {
            gameEngine.drawBitmap(resume, 240 - resume.getWidth() / 2,160 - resume.getHeight() / 2);
        }

        if(state == State.GameOver)
        {
            gameEngine.drawBitmap(gameover, 240 - gameover.getWidth() / 2,160 - gameover.getHeight() / 2);
        }
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
