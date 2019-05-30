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
    int levelX = 0;
    int levelY = 0;
    DirectionHandler directionHandler = new DirectionHandler();
    Door objDoor = new Door(2555, 200);
    List<LevelObject> levelObjects = buildBoundaries();
    Ground ground = new Ground(0, 243);
    List<LevelObject> platforms = buildPlatforms();
    List<Orc> orcs = populateLevel();
    List<Coin> coins = placeCoins();
    Typeface font;
    Sound bounceSound;
    Sound coinSound;
    Sound deathSound;
    Sound damageSound;

    public FirstLevel(GameEngine gameEngine)
    {
        super(gameEngine);
        Log.d("Examgame", "Starting the game");

        this.orc = gameEngine.loadBitmap("ExamGame/orcRight.png");
        this.coin = gameEngine.loadBitmap("ExamGame/coin.png");
        this.firstLevel = gameEngine.loadBitmap("ExamGame/Levels/firstLevel.png");
        this.door = gameEngine.loadBitmap("ExamGame/door.png");
        this.health = gameEngine.loadBitmap("ExamGame/3hearts.png");
        this.font = gameEngine.loadFont("ExamGame/font.ttf");
        this.bounceSound = gameEngine.loadSound("ExamGame/Sounds/bounce.wav");
        this.coinSound = gameEngine.loadSound("ExamGame/Sounds/coin.ogg");
        this.deathSound = gameEngine.loadSound("ExamGame/Sounds/death.ogg");
        this.damageSound = gameEngine.loadSound("ExamGame/Sounds/damage.wav");


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
            //  orcs
            levelX -= 3;
            for(Orc orc: orcs)
            {
                orc.x -= 3;
            }

            //  objects
            for(LevelObject levelObject: levelObjects)
            {
                levelObject.x -= 3;
            }

            //  platforms
            for(LevelObject platform: platforms)
            {
                platform.x -= 3;
            }

            //  coins
            for(Coin objCoin: coins)
            {
                objCoin.x -= 3;
            }

            //  door
            objDoor.x -= 3;

        }
        if(directionHandler.isMovingLeft(gameEngine))
        {
            //  orcs
            levelX += 3;
            for(Orc orc: orcs)
            {
                orc.x += 3;
            }

            //  objects
            for(LevelObject levelObject: levelObjects)
            {
                levelObject.x += 3;
            }

            //  platforms
            for(LevelObject platform: platforms)
            {
                platform.x += 3;
            }

            //  coins
            for(Coin objCoin: coins)
            {
                objCoin.x += 3;
            }

            //  door
            objDoor.x += 3;
        }

        gameEngine.drawBitmap(firstLevel, levelX, levelY);

        //  orcs
        for(Orc objOrc: orcs)
        {
            gameEngine.drawBitmap(loadOrc(playerRenderer.world.player.x, objOrc.x), objOrc.x, objOrc.y);
        }
        for(int i = 0; i < orcs.size(); i++)
        {
            collideOrc(playerRenderer.world.player, orcs.get(i));
        }

        for(int i = 0; i < orcs.size(); i++)
        {
            collideFireball(playerRenderer.world.fireball, orcs.get(i), playerRenderer.world.player, orcs);
        }

        //  coins
        for(Coin objCoin: coins)
        {
            gameEngine.drawBitmap(coin, objCoin.x, objCoin.y);
        }
        for(int i = 0; i < coins.size(); i++)
        {
            collideCoin(playerRenderer.world.player, coins.get(i));
        }

        for (LevelObject levelObject: levelObjects)
        {
            collideObjectsSides(playerRenderer.world.player, levelObject, levelObjects);
        }

        for(LevelObject platform: platforms)
        {
            collidePlatform(playerRenderer.world.player, platform);
        }


        if(openDoor())
        {
            Log.d("door", "update: opening door");
            gameEngine.drawBitmap(door, objDoor.x,objDoor.y);
            if(collideDoor(playerRenderer.world.player, objDoor) && (gameEngine.isTouchDown(0)
                && gameEngine.getTouchY(0) > 240
                && gameEngine.getTouchX(0) > 480 - 75 - 40 - 80
                && gameEngine.getTouchX(0) < 480 - 75 - 40))
            {
                gameEngine.setScreen(new MainMenuScreen(gameEngine));
            }
        }

        gameEngine.drawBitmap(loadHealth(playerRenderer.world.player.health), 10, 10);

        String showText = "Coins: " + playerRenderer.world.player.coinsCollected;

        gameEngine.drawText(font,showText, 400, 20, Color.BLACK, 12);

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
            damageSound.play(1);
            moveObjects(player, player.orcKnockBack);
            player.health -= 1;
            if(player.health <= 0)
            {
                deathSound.play(1);
                gameEngine.setScreen(new MainMenuScreen(gameEngine));
            }

        }
    }

    private void collideObjectsSides(Player player, LevelObject levelObject, List<LevelObject> levelObjects)
    {
        if(collideSides(player, levelObject))
        {
            Log.d("FirstLevel", "Player collided with object");
            bounceSound.play(1);
            moveObjects(player, player.wallKnockBack);
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
            //Log.d("FirstLevel.collideGround()", "Player collided with ground");
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

    private void collideFireball(Fireball fireball, Orc orc, Player player, List<Orc> orcs)
    {
        if(collideRects(fireball.x, player.y + 11, Fireball.WIDTH, Fireball.HEIGHT,
                    orc.x, orc.y, Orc.WIDTH, Orc.HEIGHT))
        {
            Log.d("FirstLevel.collideFireball()", "Fireball collided with orc");
            orcs.remove(orc);
        }
    }

    private void collideCoin(Player player, Coin coin)
    {
        if(collideRects(player.x, player.y, Player.WIDTH, Player.HEIGHT,
                        coin.x, coin.y, Coin.WIDTH, Coin.HEIGHT))
        {
            coinSound.play(1);
            coins.remove(coin);
            player.coinsCollected += 1;
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

    private void moveObjects(Player player, int knockback)
    {
        if(player.direction == Player.Direction.RIGHT)
        {
            levelX += knockback;

            //  orc
            for(Orc objOrc: orcs)
            {
                objOrc.x += knockback;
            }

            //  objects
            for (LevelObject object : levelObjects)
            {
                object.x += knockback;
            }

            //  platforms
            for(LevelObject platform: platforms)
            {
                platform.x += knockback;
            }

            //  coins
            for(Coin objCoin: coins)
            {
                objCoin.x += knockback;
            }

            //  door
            objDoor.x += knockback;
        }
        else
        {
            levelX -= knockback;

            //  orc
            for(Orc objOrc: orcs)
            {
                objOrc.x -= knockback;
            }

            //  objects
            for(LevelObject levelObject: levelObjects)
            {
                levelObject.x -= knockback;
            }

            //  platforms
            for(LevelObject platform: platforms)
            {
                platform.x -= knockback;
            }

            //  coins
            for(Coin objCoin: coins)
            {
                objCoin.x -= knockback;
            }

            //  door
            objDoor.x -= knockback;
        }
    }

    private boolean collideDoor(Player player, Door door)
    {
        return collideRects(player.x, player.y, Player.WIDTH, Player.HEIGHT,
                            door.x, door.y, Door.WIDTH, Door.HEIGHT);
    }

    private Bitmap loadHealth(int health)
    {
        if(health == 3)
        {
            return gameEngine.loadBitmap("ExamGame/3hearts.png");
        }
        if(health == 2)
        {
            return gameEngine.loadBitmap("ExamGame/2hearts.png");
        }

        return gameEngine.loadBitmap("ExamGame/heart.png");

    }

    private Bitmap loadOrc(int playerX, int orcX)
    {
        if(playerX <= orcX)
        {
            return gameEngine.loadBitmap("ExamGame/orcLeft.png");
        }

        return gameEngine.loadBitmap("ExamGame/orcRight.png");
    }

    private boolean openDoor()
    {
        return orcs.isEmpty() && coins.isEmpty();
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

    }

}
