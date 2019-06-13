package dk.kea.class2019january.mathiasg.gameengine.ExamGame;

import android.graphics.Bitmap;

import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Characters.Player;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Directions.Direction;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Directions.VerticalDirection;
import dk.kea.class2019january.mathiasg.gameengine.GameEngine;

public class PlayerRenderer
{
    GameEngine gameEngine;
    public World world;
    public Player player;

    //bitmaps
    Bitmap playerRight;
    Bitmap playerLeft;
    Bitmap playerJumpRight;
    Bitmap playerJumpLeft;
    Bitmap playerFallRight;
    Bitmap playerFallLeft;
    Bitmap playerRunRight1;
    Bitmap playerRunRight2;
    Bitmap playerRunLeft1;
    Bitmap playerRunLeft2;

    //time used for animating player bitmap
    float passedTime = 0;

    public PlayerRenderer(GameEngine gameEngine, World world)
    {
        this.gameEngine = gameEngine;
        this.world = world;
        this.player = world.player;

        this.playerRight = gameEngine.loadBitmap("ExamGame/Player/playerRight.png");
        this.playerLeft = gameEngine.loadBitmap("ExamGame/Player/playerLeft.png");
        this.playerJumpRight = gameEngine.loadBitmap("ExamGame/Player/playerJumpRight.png");
        this.playerJumpLeft = gameEngine.loadBitmap("ExamGame/Player/playerJumpLeft.png");
        this.playerFallRight = gameEngine.loadBitmap("ExamGame/Player/playerFallRight.png");
        this.playerFallLeft = gameEngine.loadBitmap("ExamGame/Player/playerFallLeft.png");
        this.playerRunRight1 = gameEngine.loadBitmap("ExamGame/Player/playerRunRight1.png");
        this.playerRunRight2 = gameEngine.loadBitmap("ExamGame/Player/playerRunRight2.png");
        this.playerRunLeft1 = gameEngine.loadBitmap("ExamGame/Player/playerRunLeft1.png");
        this.playerRunLeft2 = gameEngine.loadBitmap("ExamGame/Player/playerRunLeft2.png");
    }

    //renders player in current level
    public void render(float deltaTime)
    {
        gameEngine.drawBitmap(loadPlayerSprite(deltaTime), player.x, player.y);
    }


    private Bitmap loadPlayerSprite(float deltaTime)
    {
        if(player.isIdle
                && player.direction == Direction.RIGHT
                && player.verticalDirection == VerticalDirection.STILL)
        {
            return playerRight;
        }
        if(player.isIdle
                && player.direction == Direction.LEFT
                && player.verticalDirection == VerticalDirection.STILL)
        {
            return playerLeft;
        }
        if(player.verticalDirection == VerticalDirection.UP
                && player.direction == Direction.RIGHT)
        {
            return playerJumpRight;
        }
        if(player.verticalDirection == VerticalDirection.UP
                && player.direction == Direction.LEFT)
        {
            return playerJumpLeft;
        }
        if(player.verticalDirection == VerticalDirection.DOWN
                && player.direction == Direction.RIGHT)
        {
            return playerFallRight;
        }
        if(player.verticalDirection == VerticalDirection.DOWN
                && player.direction == Direction.LEFT)
        {
            return playerFallLeft;
        }
        if(player.direction == Direction.RIGHT)
        {
            passedTime += deltaTime;
            if((passedTime - (int)passedTime) > 0.5f)
            {
                return playerRunRight1;
            }
            else
            {
                return playerRunRight2;
            }
        }
        if(player.direction == Direction.LEFT)
        {
            passedTime += deltaTime;
            if((passedTime - (int)passedTime) > 0.5f)
            {
                return playerRunLeft1;
            }
            else
            {
                return playerRunLeft2;
            }
        }
        return this.playerRight;
    }
}
