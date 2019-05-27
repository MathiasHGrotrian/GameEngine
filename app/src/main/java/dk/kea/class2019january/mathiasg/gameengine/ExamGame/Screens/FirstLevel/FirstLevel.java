package dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.FirstLevel;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dk.kea.class2019january.mathiasg.gameengine.ExamGame.DirectionHandler;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.LevelObjects.BoundaryWall;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.LevelObjects.Door;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Orc;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Player;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.LevelObjects.Ground;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.LevelObjects.LevelObject;
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

    Bitmap orc;

    Bitmap firstLevel;

    World world = null;
    PlayerRenderer playerRenderer;
    int levelX = 0;
    int levelY = 0;
    DirectionHandler directionHandler = new DirectionHandler();
    Door objDoor;
    Orc objOrc = new Orc(300,210);
    List<LevelObject> levelObjects = buildLevel();
    Ground ground = new Ground(0, 250);

    public FirstLevel(GameEngine gameEngine)
    {
        super(gameEngine);
        Log.d("Examgame", "Starting the game");

        this.orc = gameEngine.loadBitmap("ExamGame/orc.png");

        this.firstLevel = gameEngine.loadBitmap("ExamGame/Levels/firstLevel.png");


        this.world = new World(gameEngine);
        this.playerRenderer = new PlayerRenderer(gameEngine, world);


    }

    @Override
    public void update(float deltaTime)
    {

        collideGround(playerRenderer.world.player, ground);
        if(directionHandler.isMovingRight(gameEngine))
        {
            levelX -= 10;
            objOrc.x -= 10;
            for(LevelObject levelObject: levelObjects)
            {
                levelObject.x -= 10;
            }

        }
        if(directionHandler.isMovingLeft(gameEngine))
        {
            levelX += 10;
            objOrc.x += 10;
            for(LevelObject levelObject: levelObjects)
            {
                levelObject.x += 10;
            }
        }

        gameEngine.drawBitmap(firstLevel, levelX, levelY);
        gameEngine.drawBitmap(orc, objOrc.x, 210);
        collideOrc(playerRenderer.world.player, objOrc);

        for (LevelObject levelObject: levelObjects)
        {
            collideObjectsSides(playerRenderer.world.player, levelObject, levelObjects);
        }

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
            if(player.direction == Player.Direction.RIGHT)
            {
                levelX += player.knockBack;
                objOrc.x += player.knockBack;

                for(LevelObject levelObject: levelObjects)
                {
                    levelObject.x += player.knockBack;
                }
            }
            else
            {
                levelX -= player.knockBack;
                objOrc.x -= player.knockBack;

                for(LevelObject levelObject: levelObjects)
                {
                    levelObject.x -= player.knockBack;
                }
            }

        }
    }
    private void collideObjectsSides(Player player, LevelObject levelObject, List<LevelObject> levelObjects)
    {
        if(collideRects(player.x, player.y, Player.WIDTH, Player.HEIGHT,
                levelObject.x, levelObject.y, levelObject.width, levelObject.height))
        {
            Log.d("FirstLevel", "Player collided with object");
            if(player.direction == Player.Direction.RIGHT)
            {
                levelX += player.knockBack;
                for (LevelObject object : levelObjects)
                {
                    object.x += player.knockBack;
                }
                objOrc.x += player.knockBack;
            }
            else
            {
                levelX -= player.knockBack;
                for (LevelObject object : levelObjects)
                {
                    object.x -= player.knockBack;
                }
                objOrc.x -= player.knockBack;
            }
        }
    }

    private void collideGround(Player player, LevelObject levelObject)
    {
        if(player.y + Player.HEIGHT >= levelObject.y
                /*&& player.x > levelObject.x
                && player.x < levelObject.x + levelObject.width*/)
        {
            Log.d("FirstLevel.collideGround()", "Player collided with ground");
            player.y = 235 - Player.HEIGHT;
            player.verticalDirection = Player.VerticalDirection.STILL;
        }
    }


    private List<LevelObject> buildLevel()
    {
        List<LevelObject> levelObjects = new ArrayList<>();

        BoundaryWall boundaryWallLeft = new BoundaryWall(218, 0);
        levelObjects.add(boundaryWallLeft);
        BoundaryWall boundaryWallRight = new BoundaryWall(2618, 0);
        levelObjects.add(boundaryWallRight);

        return levelObjects;
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
