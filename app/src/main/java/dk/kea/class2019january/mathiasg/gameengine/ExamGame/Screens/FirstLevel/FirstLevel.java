package dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.FirstLevel;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dk.kea.class2019january.mathiasg.gameengine.ExamGame.DirectionHandler;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.LevelObjects.BigHill;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.LevelObjects.BigMossyPlatform;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.LevelObjects.BigStonePlatform;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.LevelObjects.BoundaryWall;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.LevelObjects.Door;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Orc;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Player;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.LevelObjects.Ground;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.LevelObjects.LevelObject;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.LevelObjects.LongStonePlatform;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.LevelObjects.MossyPlatform;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.LevelObjects.StonePlatform;
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
    List<LevelObject> levelObjects = buildBoundaries();
    Ground ground = new Ground(0, 243);
    List<LevelObject> platforms = buildPlatforms();



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
        playerRenderer.world.player.y += 1;

        collideGround(playerRenderer.world.player, ground);
        for(LevelObject platform : platforms)
        {
            collidePlatform(playerRenderer.world.player, platform);
        }

        if(directionHandler.isMovingRight(gameEngine))
        {
            levelX -= 10;
            objOrc.x -= 10;
            for(LevelObject levelObject: levelObjects)
            {
                levelObject.x -= 10;
            }
            for(LevelObject platform: platforms)
            {
                platform.x -= 10;
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
            for(LevelObject platform: platforms)
            {
                platform.x += 10;
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
                for(LevelObject platform: platforms)
                {
                    platform.x += 10;
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
                for(LevelObject platform: platforms)
                {
                    platform.x -= 10;
                }
            }

        }
    }
    private void collideObjectsSides(Player player, LevelObject levelObject, List<LevelObject> levelObjects)
    {
        if(collideSides(player, levelObject))
        {
            Log.d("FirstLevel", "Player collided with object");
            if(player.direction == Player.Direction.RIGHT)
            {
                levelX += player.knockBack;
                for (LevelObject object : levelObjects)
                {
                    object.x += player.knockBack;
                }
                for(LevelObject platform: platforms)
                {
                    platform.x += 10;
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
                for(LevelObject platform: platforms)
                {
                    platform.x -= 10;
                }
                objOrc.x -= player.knockBack;
            }
        }
    }

    private boolean collideSides(Player player, LevelObject levelObject)
    {
        return (player.x < levelObject.x + levelObject.width
                && player.x + Player.WIDTH > levelObject.x);
    }

    private void collideGround(Player player, LevelObject levelObject)
    {
        if(player.y + Player.HEIGHT > levelObject.y
                && player.x > levelObject.x
                && player.x < levelObject.x + levelObject.width)
        {
            Log.d("FirstLevel.collideGround()", "Player collided with ground");
            player.y = levelObject.y - Player.HEIGHT;
            player.verticalDirection = Player.VerticalDirection.STILL;
        }
    }

    private void collidePlatform(Player player, LevelObject levelObject)
    {
        if(player.y + Player.HEIGHT == levelObject.y
                && player.x > levelObject.x
                && player.x < levelObject.x + levelObject.width)
        {
            Log.d("FirstLevel.collideGround()", "Player collided with platform");
            player.y = levelObject.y - Player.HEIGHT - 1;
            player.verticalDirection = Player.VerticalDirection.STILL;
        }

    }


    private List<LevelObject> buildBoundaries()
    {
        List<LevelObject> boundaries = new ArrayList<>();

        BoundaryWall boundaryWallLeft = new BoundaryWall(218, 0);
        boundaries.add(boundaryWallLeft);
        BoundaryWall boundaryWallRight = new BoundaryWall(2618, 0);
        boundaries.add(boundaryWallRight);


        return boundaries;
    }

    private List<LevelObject> buildPlatforms()
    {
        List<LevelObject> platforms = new ArrayList<>();

        BigStonePlatform upperBigStone = new BigStonePlatform(646, 99);
        BigStonePlatform lowerBigStone = new BigStonePlatform(595, 137);
        StonePlatform stonePlatform = new StonePlatform(549, 172);
        MossyPlatform firstMossy = new MossyPlatform(1061, 156);
        MossyPlatform secondMossy = new MossyPlatform(1624, 117);
        MossyPlatform thirdMossy = new MossyPlatform(1703, 81);
        MossyPlatform fourthMossy = new MossyPlatform(1775, 115);
        MossyPlatform fifthMossy = new MossyPlatform(1839, 78);
        BigHill bigHill = new BigHill(1115, 90);
        BigMossyPlatform firstBigMossy = new BigMossyPlatform(1374, 90);
        BigMossyPlatform secondBigMossy = new BigMossyPlatform(1504, 90);
        LongStonePlatform longStonePlatform = new LongStonePlatform(1933, 78);
        platforms.add(upperBigStone);
        platforms.add(lowerBigStone);
        platforms.add(stonePlatform);
        platforms.add(firstMossy);
        platforms.add(secondMossy);
        platforms.add(thirdMossy);
        platforms.add(fourthMossy);
        platforms.add(fifthMossy);
        platforms.add(bigHill);
        platforms.add(firstBigMossy);
        platforms.add(secondBigMossy);
        platforms.add(longStonePlatform);


        return platforms;
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
