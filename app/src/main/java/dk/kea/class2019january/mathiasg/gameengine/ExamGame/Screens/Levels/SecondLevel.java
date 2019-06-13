package dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.Levels;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Characters.Fireball;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Characters.Player;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.LevelObjects.Environment.Coin;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.LevelObjects.Platforms.BigHill;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.LevelObjects.Platforms.BigMossyPlatform;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.LevelObjects.Environment.BoundaryWall;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Characters.Orc;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.LevelObjects.LevelObject;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.LevelObjects.Platforms.MossyPlatform;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.LevelObjects.Platforms.StonePlatform;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.World;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.PlayerRenderer;
import dk.kea.class2019january.mathiasg.gameengine.GameEngine;
import dk.kea.class2019january.mathiasg.gameengine.Music;
import dk.kea.class2019january.mathiasg.gameengine.Screen;

public class SecondLevel extends Screen implements Level
{
    Bitmap secondLevel;
    World world;
    PlayerRenderer playerRenderer;
    Player player;
    Fireball fireball;
    int levelY = 0;
    Music backgroundMusic;

    public SecondLevel(GameEngine gameEngine)
    {
        super(gameEngine);
        Log.d("Examgame", "Starting the game");

        this.secondLevel = gameEngine.loadBitmap("ExamGame/Levels/secondLevel.png");
        this.backgroundMusic = gameEngine.loadMusic("ExamGame/Sounds/music2.wav");
        this.backgroundMusic.setLooping(true);
        this.world = new World(gameEngine, 2555, 200);
        //build level
        this.world.orcs = populateLevel();
        this.world.coins = placeCoins();
        this.world.coinsToCollect = world.coins.size();
        this.world.levelObjects = buildBoundaries();
        this.world.platforms = buildPlatforms();
        //render player
        this.playerRenderer = new PlayerRenderer(gameEngine, world);
        this.player = world.player;
        this.fireball = world.fireball;
        this.world.level = 2;
    }

    @Override
    public void update(float deltaTime)
    {
        backgroundMusic.play();
        gameEngine.drawBitmap(secondLevel, world.levelX, levelY);
        world.gameUpdate(deltaTime);
        playerRenderer.render(deltaTime);
    }

    public List<LevelObject> buildBoundaries()
    {
        List<LevelObject> boundaries = new ArrayList<>();

        BoundaryWall boundaryWallLeft = new BoundaryWall(218, 0);
        boundaries.add(boundaryWallLeft);
        BoundaryWall boundaryWallRight = new BoundaryWall(2618, 0);
        boundaries.add(boundaryWallRight);

        return boundaries;
    }

    public List<Orc> populateLevel()
    {
        List<Orc> orcs = new ArrayList<>();

        Orc orc1 = new Orc(392,55);
        orcs.add(orc1);

        Orc orc2 = new Orc(505,55);
        orcs.add(orc2);

        Orc orc3 = new Orc(600,220);
        orcs.add(orc3);

        Orc orc4 = new Orc(967,97);
        orcs.add(orc4);

        Orc orc5 = new Orc(1117,149);
        orcs.add(orc5);

        Orc orc6 = new Orc(1280,168);
        orcs.add(orc6);

        Orc orc7 = new Orc(1330,220);
        orcs.add(orc7);

        Orc orc8 = new Orc(1794,83);
        orcs.add(orc8);

        Orc orc9 = new Orc(2170,220);
        orcs.add(orc9);

        Orc orc10 = new Orc(2281,220);
        orcs.add(orc10);

        return orcs;
    }

    public List<Coin> placeCoins()
    {
        List<Coin> coins = new ArrayList<>();

        Coin coin1 = new Coin(623, 48);
        coins.add(coin1);

        Coin coin2 = new Coin(760, 20);
        coins.add(coin2);

        Coin coin3 = new Coin(882, 97);
        coins.add(coin3);

        Coin coin4 = new Coin(1046, 97);
        coins.add(coin4);

        Coin coin5 = new Coin(1385, 160);
        coins.add(coin5);

        Coin coin6 = new Coin(1535, 140);
        coins.add(coin6);

        Coin coin7 = new Coin(1665, 80);
        coins.add(coin7);

        Coin coin8 = new Coin(1855, 40);
        coins.add(coin8);

        return coins;
    }

    public List<LevelObject> buildPlatforms()
    {
        List<LevelObject> platforms = new ArrayList<>();

        MossyPlatform mossyPlatform1 = new MossyPlatform(311, 173);
        platforms.add(mossyPlatform1);

        MossyPlatform mossyPlatform2 = new MossyPlatform(311, 106);
        platforms.add(mossyPlatform2);

        BigHill bigHill1 = new BigHill(368, 75);
        platforms.add(bigHill1);

        MossyPlatform mossyPlatform3 = new MossyPlatform(606, 73);
        platforms.add(mossyPlatform3);

        BigMossyPlatform bigMossyPlatform1 = new BigMossyPlatform(718, 44);
        platforms.add(bigMossyPlatform1);

        StonePlatform stonePlatform1 = new StonePlatform(866, 117);
        platforms.add(stonePlatform1);

        StonePlatform stonePlatform2 = new StonePlatform(951, 117);
        platforms.add(stonePlatform2);

        StonePlatform stonePlatform3 = new StonePlatform(1029, 117);
        platforms.add(stonePlatform3);

        StonePlatform stonePlatform4 = new StonePlatform(1100, 169);
        platforms.add(stonePlatform4);

        BigHill bigHill2 = new BigHill(1249, 188);
        platforms.add(bigHill2);

        StonePlatform stonePlatform5 = new StonePlatform(1519, 164);
        platforms.add(stonePlatform5);

        BigMossyPlatform bigMossyPlatform2 = new BigMossyPlatform(1622, 108);
        platforms.add(bigMossyPlatform2);

        StonePlatform stonePlatform6 = new StonePlatform(1779, 103);
        platforms.add(stonePlatform6);

        StonePlatform stonePlatform7 = new StonePlatform(1841, 63);
        platforms.add(stonePlatform7);

        return platforms;
    }

    @Override
    public void pause(){}

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
