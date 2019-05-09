package dk.kea.class2019january.mathiasg.gameengine.ExamGame;

import android.graphics.Bitmap;

import dk.kea.class2019january.mathiasg.gameengine.GameEngine;

public class WorldRenderer
{
    GameEngine gameEngine;
    World world;
    Bitmap playerImage;
    //Bitmap dpadImage;
    Bitmap rightArrow;
    Bitmap leftArrow;
    Bitmap jumpButton;
    Bitmap fireBall;

    public WorldRenderer(GameEngine gameEngine, World world)
    {
        this.gameEngine = gameEngine;
        this.world = world;
        this.playerImage = gameEngine.loadBitmap("ExamGame/player.png");
        //this.dpadImage = gameEngine.loadBitmap("ExamGame/dpad.png");
        this.rightArrow = gameEngine.loadBitmap("ExamGame/rightArrow.png");
        this.leftArrow = gameEngine.loadBitmap("ExamGame/leftArrow.png");
        this.jumpButton = gameEngine.loadBitmap("ExamGame/jumpButton.png");
        this.fireBall = gameEngine.loadBitmap("ExamGame/fireball.png");

    }

    public void render()
    {
        gameEngine.drawBitmap(playerImage, world.player.x, world.player.y);
        //gameEngine.drawBitmap(dpadImage, 0, 0);
        gameEngine.drawBitmap(leftArrow, 20, 240);
        gameEngine.drawBitmap(rightArrow, 380, 240);
        gameEngine.drawBitmap(jumpButton, 280, 230);
        gameEngine.drawBitmap(fireBall, leftArrow.getWidth() + 40, 230);
    }
}
