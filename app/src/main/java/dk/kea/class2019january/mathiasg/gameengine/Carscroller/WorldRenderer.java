package dk.kea.class2019january.mathiasg.gameengine.Carscroller;

import android.graphics.Bitmap;

import dk.kea.class2019january.mathiasg.gameengine.GameEngine;

public class WorldRenderer
{
    GameEngine gameEngine;
    World world;
    Bitmap carImage;
    Bitmap monsterImage;

    public WorldRenderer(GameEngine gameEngine, World world)
    {
        this.gameEngine = gameEngine;
        this.world = world;
        this.carImage = gameEngine.loadBitmap("carscroller/xbluecar.png");
        this.monsterImage = gameEngine.loadBitmap("carscroller/xyellowmonster2.png");
    }

    public void render()
    {
        gameEngine.drawBitmap(monsterImage, world.car.x, world.car.y);

        for(Monster monster : world.monsterList)
        {
            gameEngine.drawBitmap(monsterImage, monster.x, monster.y);
        }
    }
}
