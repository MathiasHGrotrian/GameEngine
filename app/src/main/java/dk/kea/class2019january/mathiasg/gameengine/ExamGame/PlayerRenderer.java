package dk.kea.class2019january.mathiasg.gameengine.ExamGame;

import android.graphics.Bitmap;

import dk.kea.class2019january.mathiasg.gameengine.GameEngine;

public class PlayerRenderer
{
    GameEngine gameEngine;
    public World world;
    public Bitmap playerImage;
    Bitmap rightArrow;
    Bitmap leftArrow;
    Bitmap jumpButton;
    Bitmap fireBallButton;
    float passedTime = 0;

    public PlayerRenderer(GameEngine gameEngine, World world)
    {
        this.gameEngine = gameEngine;
        this.world = world;
        this.rightArrow = gameEngine.loadBitmap("ExamGame/Controls/rightArrow.png");
        this.leftArrow = gameEngine.loadBitmap("ExamGame/Controls/leftArrow.png");
        this.jumpButton = gameEngine.loadBitmap("ExamGame/Controls/jumpButton.png");
        this.fireBallButton = gameEngine.loadBitmap("ExamGame/Controls/fireball.png");

    }

    public void render(float deltaTime)
    {
        gameEngine.drawBitmap(loadPlayerSprite(deltaTime), world.player.x, world.player.y);

        gameEngine.drawBitmap(leftArrow, 20, 240);
        gameEngine.drawBitmap(rightArrow, 380, 240);
        gameEngine.drawBitmap(jumpButton, 280, 230);
        gameEngine.drawBitmap(fireBallButton, leftArrow.getWidth() + 40, 230);

    }


    private Bitmap loadPlayerSprite(float deltaTime)
    {
        if(world.player.isIdle && world.player.direction == Player.Direction.RIGHT)
        {
            return gameEngine.loadBitmap("ExamGame/Player/playerRight.png");
        }
        if(world.player.isIdle && world.player.direction == Player.Direction.LEFT)
        {
            return gameEngine.loadBitmap("ExamGame/Player/playerLeft.png");
        }
        if(world.player.verticalDirection == Player.VerticalDirection.UP
                && world.player.direction == Player.Direction.RIGHT)
        {
            return gameEngine.loadBitmap("ExamGame/Player/playerJumpRight.png");
        }
        if(world.player.verticalDirection == Player.VerticalDirection.UP
                && world.player.direction == Player.Direction.LEFT)
        {
            return gameEngine.loadBitmap("ExamGame/Player/playerJumpLeft.png");
        }
        if(world.player.verticalDirection == Player.VerticalDirection.DOWN
                && world.player.direction == Player.Direction.RIGHT)
        {
            return gameEngine.loadBitmap("ExamGame/Player/playerFallRight.png");
        }
        if(world.player.verticalDirection == Player.VerticalDirection.DOWN
                && world.player.direction == Player.Direction.LEFT)
        {
            return gameEngine.loadBitmap("ExamGame/Player/playerFallLeft.png");
        }
        if(world.player.direction == Player.Direction.RIGHT)
        {
            passedTime += deltaTime;
            if((passedTime - (int)passedTime) > 0.5f)
            {
                return gameEngine.loadBitmap("ExamGame/Player/playerRunRight1.png");
            }
            else
            {
                return gameEngine.loadBitmap("ExamGame/Player/playerRunRight2.png");
            }

            //this.playerImage = gameEngine.loadBitmap("ExamGame/Player/playerRight.png");
        }
        if(world.player.direction == Player.Direction.LEFT)
        {
            passedTime += deltaTime;
            if((passedTime - (int)passedTime) > 0.5f)
            {
                return gameEngine.loadBitmap("ExamGame/Player/playerRunLeft1.png");
            }
            else
            {
                return gameEngine.loadBitmap("ExamGame/Player/playerRunLeft2.png");
            }
        }

        return this.playerImage;
    }
}
