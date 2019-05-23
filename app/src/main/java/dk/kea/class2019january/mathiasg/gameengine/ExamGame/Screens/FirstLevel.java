package dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens;

import android.graphics.Bitmap;
import android.util.Log;

import dk.kea.class2019january.mathiasg.gameengine.ExamGame.DirectionHandler;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Door;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Orc;
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
    int levelX = 0;
    int levelY = 0;
    DirectionHandler directionHandler = new DirectionHandler();
    Door objDoor;
    Orc objOrc = new Orc(300,210);

    public FirstLevel(GameEngine gameEngine)
    {
        super(gameEngine);
        Log.d("Examgame", "Starting the game");

        //level objects
        /*
        this.background = gameEngine.loadBitmap("ExamGame/sky.png");
        this.ground = gameEngine.loadBitmap("ExamGame/ground.png");
        this.door = gameEngine.loadBitmap("ExamGame/door.png");
        */
        this.orc = gameEngine.loadBitmap("ExamGame/orc.png");


        this.firstLevel = gameEngine.loadBitmap("ExamGame/Levels/firstLevel.png");


        this.world = new World(gameEngine);
        this.playerRenderer = new PlayerRenderer(gameEngine, world);
        //this.objDoor = new Door(300, 195);

    }

    @Override
    public void update(float deltaTime)
    {
        if(directionHandler.isMovingRight(gameEngine))
        {
            levelX -= 2;
            objOrc.x -= 2;
        }
        if(directionHandler.isMovingLeft(gameEngine))
        {
            levelX += 2;
            objOrc.x += 2;
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
        gameEngine.drawBitmap(firstLevel, levelX, levelY);

        gameEngine.drawBitmap(orc, objOrc.x, 210);

        collideOrc(playerRenderer.world.player, objOrc);

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

    private void collideOrc(Player player, Orc orc)
    {
        if(collideRects(player.x, player.y, Player.WIDTH, Player.HEIGHT,
                orc.x, orc.y, Orc.WIDTH, Orc.HEIGHT))
        {
            Log.d("FirstLevel", "Player collided with Orc");
            levelX += 30;
            objOrc.x += 30;
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
