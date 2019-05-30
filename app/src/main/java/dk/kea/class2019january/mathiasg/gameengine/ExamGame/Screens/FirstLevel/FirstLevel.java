package dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.FirstLevel;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Coin;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.DirectionHandler;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Fireball;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.LevelObjects.Platforms.BigHill;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.LevelObjects.Platforms.BigMossyPlatform;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.LevelObjects.Platforms.BigStonePlatform;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.LevelObjects.BoundaryWall;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.LevelObjects.Door;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Orc;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Player;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.LevelObjects.Ground;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.LevelObjects.LevelObject;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.LevelObjects.Platforms.LongStonePlatform;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.LevelObjects.Platforms.MossyPlatform;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.LevelObjects.Platforms.StonePlatform;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.MainMenuScreen;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.World;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.PlayerRenderer;
import dk.kea.class2019january.mathiasg.gameengine.GameEngine;
import dk.kea.class2019january.mathiasg.gameengine.Music;
import dk.kea.class2019january.mathiasg.gameengine.Screen;
import dk.kea.class2019january.mathiasg.gameengine.Sound;

public class FirstLevel extends Screen
{
    enum State
    {
        Paused,
        Running,
        GameOver
    }

    Bitmap orc;
    Bitmap coin;
    Bitmap firstLevel;
    Bitmap door;
    Bitmap health;

    World world = null;
    PlayerRenderer playerRenderer;
    int levelY = 0;
    DirectionHandler directionHandler = new DirectionHandler();
    List<LevelObject> levelObjects = buildBoundaries();
    Ground ground = new Ground(0, 243);
    List<LevelObject> platforms = buildPlatforms();
    Typeface font;

    Music backgroundMusic;

    public FirstLevel(GameEngine gameEngine)
    {
        super(gameEngine);
        Log.d("Examgame", "Starting the game");

        this.orc = gameEngine.loadBitmap("ExamGame/Orc/orcRight.png");
        this.coin = gameEngine.loadBitmap("ExamGame/LevelObjects/coin.png");
        this.firstLevel = gameEngine.loadBitmap("ExamGame/Levels/firstLevel.png");
        this.door = gameEngine.loadBitmap("ExamGame/LevelObjects/door.png");
        this.health = gameEngine.loadBitmap("ExamGame/Health/3hearts.png");
        this.font = gameEngine.loadFont("ExamGame/font.ttf");
        this.backgroundMusic = gameEngine.loadMusic("ExamGame/Sounds/music.wav");
        this.backgroundMusic.setLooping(true);
        this.world = new World(gameEngine, 2555, 200);
        this.world.orcs = populateLevel();
        this.world.coins = placeCoins();

        this.world.levelObjects = buildBoundaries();
        this.world.platforms = buildPlatforms();

        this.playerRenderer = new PlayerRenderer(gameEngine, world);


    }

    @Override
    public void update(float deltaTime)
    {
        backgroundMusic.play();
        playerRenderer.world.player.y += 1;

        world.collideGround(playerRenderer.world.player, ground);
        for(LevelObject platform : platforms)
        {
            world.collidePlatform(playerRenderer.world.player, platform);
        }

        if(directionHandler.isMovingRight(gameEngine))
        {
            playerRenderer.world.player.isIdle = false;
            //  orcs
            world.levelX -= 3;
            for(Orc orc: world.orcs)
            {
                orc.x -= 3;
            }

            //  objects
            for(LevelObject levelObject: world.levelObjects)
            {
                levelObject.x -= 3;
            }

            //  platforms
            for(LevelObject platform: world.platforms)
            {
                platform.x -= 3;
            }

            //  coins
            for(Coin objCoin: world.coins)
            {
                objCoin.x -= 3;
            }

            //  door
            world.objDoor.x -= 3;

            //  fireball
            if(playerRenderer.world.player.isShootingFireball)
            {
                playerRenderer.world.fireball.x -= 3;
            }

        }
        else if(directionHandler.isMovingLeft(gameEngine))
        {
            playerRenderer.world.player.isIdle = false;
            //  orcs
            world.levelX += 3;
            for(Orc orc: world.orcs)
            {
                orc.x += 3;
            }

            //  objects
            for(LevelObject levelObject: world.levelObjects)
            {
                levelObject.x += 3;
            }

            //  platforms
            for(LevelObject platform: world.platforms)
            {
                platform.x += 3;
            }

            //  coins
            for(Coin objCoin: world.coins)
            {
                objCoin.x += 3;
            }

            //  door
            world.objDoor.x += 3;

            // fireball
            if(playerRenderer.world.player.isShootingFireball)
            {
                playerRenderer.world.fireball.x += 3;
            }
        }
        else
        {
            playerRenderer.world.player.isIdle = true;
        }

        gameEngine.drawBitmap(firstLevel, world.levelX, levelY);

        //  orcs
        for(Orc objOrc: world.orcs)
        {
            gameEngine.drawBitmap(world.loadOrc(playerRenderer.world.player.x, objOrc.x), objOrc.x, objOrc.y);
        }
        for(int i = 0; i < world.orcs.size(); i++)
        {
            world.collideOrc(playerRenderer.world.player, world.orcs.get(i));
        }

        for(int i = 0; i < world.orcs.size(); i++)
        {
            world.collideFireball(playerRenderer.world.fireball, world.orcs.get(i), playerRenderer.world.player, world.orcs);
        }

        //  coins
        for(Coin objCoin: world.coins)
        {
            gameEngine.drawBitmap(coin, objCoin.x, objCoin.y);
        }
        for(int i = 0; i < world.coins.size(); i++)
        {
            world.collideCoin(playerRenderer.world.player, world.coins.get(i));
        }

        for (LevelObject levelObject: world.levelObjects)
        {
            world.collideObjectsSides(playerRenderer.world.player, levelObject, levelObjects);
        }

        for(LevelObject platform: world.platforms)
        {
            world.collidePlatform(playerRenderer.world.player, platform);
        }


        if(world.openDoor())
        {
            Log.d("door", "update: opening door");
            gameEngine.drawBitmap(door, world.objDoor.x,world.objDoor.y);
            if(world.collideDoor(playerRenderer.world.player, world.objDoor) && (gameEngine.isTouchDown(0)
                && gameEngine.getTouchY(0) > 240
                && gameEngine.getTouchX(0) > 480 - 75 - 40 - 80
                && gameEngine.getTouchX(0) < 480 - 75 - 40))
            {
                gameEngine.setScreen(new MainMenuScreen(gameEngine));
            }
        }

        gameEngine.drawBitmap(world.loadHealth(playerRenderer.world.player.health), 10, 10);

        String showText = "Coins: " + playerRenderer.world.player.coinsCollected;

        gameEngine.drawText(font,showText, 400, 20, Color.BLACK, 12);
        gameEngine.drawBitmap(coin, 380, 7);

        world.update(deltaTime);
        playerRenderer.render(deltaTime);

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

    private List<Orc> populateLevel()
    {
        List<Orc> orcs = new ArrayList<>();

        Orc orc1 = new Orc(700,220);
        orcs.add(orc1);

        Orc orc2 = new Orc(950,200);
        orcs.add(orc2);

        Orc orc3 = new Orc(980,200);
        orcs.add(orc3);

        Orc orc4 = new Orc(1195,69);
        orcs.add(orc4);

        Orc orc5 = new Orc(1545,70);
        orcs.add(orc5);

        Orc orc6 = new Orc(1720,61);
        orcs.add(orc6);

        Orc orc7 = new Orc(1855,58);
        orcs.add(orc7);

        return orcs;

    }

    private List<Coin> placeCoins()
    {
        List<Coin> coins = new ArrayList<>();

        Coin coin1 = new Coin(564, 144);
        coins.add(coin1);

        Coin coin2 = new Coin(620, 110);
        coins.add(coin2);

        Coin coin3 = new Coin(680, 70);
        coins.add(coin3);

        Coin coin4 = new Coin(1075, 135);
        coins.add(coin4);

        Coin coin5 = new Coin(1415, 60);
        coins.add(coin5);

        Coin coin6 = new Coin(1980, 55);
        coins.add(coin6);

        Coin coin7 = new Coin(2010, 55);
        coins.add(coin7);

        Coin coin8 = new Coin(2040, 55);
        coins.add(coin8);

        return coins;

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
        gameEngine.music.play();
    }

    @Override
    public void dispose()
    {
        backgroundMusic.dispose();
    }

}
