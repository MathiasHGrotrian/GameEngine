package dk.kea.class2019january.mathiasg.gameengine.ExamGame;

import android.graphics.Bitmap;

import dk.kea.class2019january.mathiasg.gameengine.GameEngine;

public class PlayerRenderer
{
    GameEngine gameEngine;
    public World world;
    Bitmap playerImage;
    Bitmap rightArrow;
    Bitmap leftArrow;
    Bitmap jumpButton;
    Bitmap fireBallButton;

    public PlayerRenderer(GameEngine gameEngine, World world)
    {
        this.gameEngine = gameEngine;
        this.world = world;
        this.rightArrow = gameEngine.loadBitmap("ExamGame/rightArrow.png");
        this.leftArrow = gameEngine.loadBitmap("ExamGame/leftArrow.png");
        this.jumpButton = gameEngine.loadBitmap("ExamGame/jumpButton.png");
        this.fireBallButton = gameEngine.loadBitmap("ExamGame/fireball.png");

    }

    public void render()
    {
        gameEngine.drawBitmap(loadPlayerSprite(), world.player.x, world.player.y);

        gameEngine.drawBitmap(leftArrow, 20, 240);
        gameEngine.drawBitmap(rightArrow, 380, 240);
        gameEngine.drawBitmap(jumpButton, 280, 230);
        gameEngine.drawBitmap(fireBallButton, leftArrow.getWidth() + 40, 230);

    }


    private Bitmap loadPlayerSprite()
    {
        if(world.player.direction == Player.Direction.RIGHT)
        {
            this.playerImage = gameEngine.loadBitmap("ExamGame/playerRight.png");
        }
        if(world.player.direction == Player.Direction.LEFT)
        {
            this.playerImage = gameEngine.loadBitmap("ExamGame/playerLeft.png");
        }
        if(world.player.verticalDirection == Player.VerticalDirection.UP
                && world.player.direction == Player.Direction.RIGHT)
        {
            this.playerImage = gameEngine.loadBitmap("ExamGame/playerJumpRight.png");
        }
        if(world.player.verticalDirection == Player.VerticalDirection.UP
                && world.player.direction == Player.Direction.LEFT)
        {
            this.playerImage = gameEngine.loadBitmap("ExamGame/playerJumpLeft.png");
        }
        if(world.player.verticalDirection == Player.VerticalDirection.DOWN
                && world.player.direction == Player.Direction.RIGHT)
        {
            this.playerImage = gameEngine.loadBitmap("ExamGame/playerFallRight.png");
        }
        if(world.player.verticalDirection == Player.VerticalDirection.DOWN
                && world.player.direction == Player.Direction.LEFT)
        {
            this.playerImage = gameEngine.loadBitmap("ExamGame/playerFallLeft.png");
        }

        return this.playerImage;
    }
}
