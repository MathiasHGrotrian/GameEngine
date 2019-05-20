package dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens;

import android.graphics.Bitmap;
import android.util.Log;

import dk.kea.class2019january.mathiasg.gameengine.ExamGame.DirectionHandler;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Door;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Player;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.World;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.PlayerRenderer;
import dk.kea.class2019january.mathiasg.gameengine.GameEngine;
import dk.kea.class2019january.mathiasg.gameengine.Screen;

public class FirstLevel extends Screen
{
    enum State
    {
        Paused,
        Running,
        GameOver
    }

    Bitmap background;
    Bitmap ground;
    Bitmap door;
    Bitmap orc;

    World world = null;
    PlayerRenderer playerRenderer;
    int levelX = 0;
    DirectionHandler directionHandler = new DirectionHandler();
    Door objDoor;

    public FirstLevel(GameEngine gameEngine)
    {
        super(gameEngine);
        Log.d("Examgame", "Starting the game");

        //level objects
        this.background = gameEngine.loadBitmap("ExamGame/sky.png");
        this.ground = gameEngine.loadBitmap("ExamGame/ground.png");
        this.door = gameEngine.loadBitmap("ExamGame/door.png");
        this.orc = gameEngine.loadBitmap("ExamGame/orc.png");

        this.world = new World(gameEngine);
        this.playerRenderer = new PlayerRenderer(gameEngine, world);
        this.objDoor = new Door(300, 195);

    }

    @Override
    public void update(float deltaTime)
    {

        if(directionHandler.isMovingRight(gameEngine))
        {
            levelX = levelX + 2;
        }
        if(directionHandler.isMovingLeft(gameEngine))
        {
            levelX = levelX - 2;
        }
        //what is srcX srcY?
        gameEngine.drawBitmap(background, 100 - levelX, 0, 0, 0, 480, 320);
        gameEngine.drawBitmap(ground, -100 - levelX, 235);
        gameEngine.drawBitmap(door, 300 - levelX, objDoor.y);
        gameEngine.drawBitmap(orc, 200 - levelX, 215);



        world.collideDoor(300 - levelX, objDoor.y);

        world.update(deltaTime);
        playerRenderer.render();



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
