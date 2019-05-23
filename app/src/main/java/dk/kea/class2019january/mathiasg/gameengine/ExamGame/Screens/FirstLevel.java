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

    Bitmap firstLevel;

    World world = null;
    PlayerRenderer playerRenderer;
    int levelX = -82;
    int levelY = -444;
    DirectionHandler directionHandler = new DirectionHandler();
    Door objDoor;

    public FirstLevel(GameEngine gameEngine)
    {
        super(gameEngine);
        Log.d("Examgame", "Starting the game");

        //level objects
        /*
        this.background = gameEngine.loadBitmap("ExamGame/sky.png");
        this.ground = gameEngine.loadBitmap("ExamGame/ground.png");
        this.door = gameEngine.loadBitmap("ExamGame/door.png");
        this.orc = gameEngine.loadBitmap("ExamGame/orc.png");
        */

        this.firstLevel = gameEngine.loadBitmap("ExamGame/FirstLevel.png");


        this.world = new World(gameEngine);
        this.playerRenderer = new PlayerRenderer(gameEngine, world);
        //this.objDoor = new Door(300, 195);

    }

    @Override
    public void update(float deltaTime)
    {
        if(directionHandler.isMovingRight(gameEngine))
        {
            levelX += 2;
        }
        if(directionHandler.isMovingLeft(gameEngine))
        {
            levelX -= 2;
        }
        if(directionHandler.isJumping(gameEngine, playerRenderer.world.player))
        {
            levelY -= 10;
            if(levelY < 20)
            {
                levelY += 10;
            }
        }
        /*
        if(directionHandler.isJumping(gameEngine, playerRenderer.world.player))
        {
            playerRenderer.world.player.verticalDirection = Player.VerticalDirection.UP;
            levelY -= -10;
            playerRenderer.world.player.verticalDirection = Player.VerticalDirection.DOWN;

        }
        if(directionHandler.isFalling(playerRenderer.world.player))
        {
            levelY += 10;
            playerRenderer.world.player.verticalDirection = Player.VerticalDirection.STILL;
        }
        */
        //what is srcX srcY?
        /*
        gameEngine.drawBitmap(background, 100 - levelX, 0, 0, 0, 480, 320);
        gameEngine.drawBitmap(ground, -100 - levelX, 235);
        gameEngine.drawBitmap(door, 300 - levelX, objDoor.y);
        gameEngine.drawBitmap(orc, 200 - levelX, 215);
        */

        //gameEngine.drawBitmap(firstLevel,levelX, levelY);
        gameEngine.drawBitmap(firstLevel,playerRenderer.world.player.x, playerRenderer.world.player.y);

        //world.collideDoor(300 - levelX, objDoor.y);

        world.update(deltaTime);
        playerRenderer.render();

    }

    //  Collisions
    private boolean collideRects(float x, float y, float width, float height,
                                 float x2, float y2, float width2, float height2)
    {
        return (x < x2 + width2 && x + width > x2 && y < y2 + height2 && y + height > y2);

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
