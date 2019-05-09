package dk.kea.class2019january.mathiasg.gameengine.ExamGame;

import android.util.Log;

import dk.kea.class2019january.mathiasg.gameengine.GameEngine;

public class World
{
    Player player = new Player();
    GameEngine gameEngine;
    int movementButtonsLenght = 75;
    int getMovementButtonsHeight = 61;
    int jumpButtonDimensions = 80;

    public World(GameEngine gameEngine)
    {
        this.gameEngine = gameEngine;
    }

    public void update(float deltaTime)
    {

        player.y = player.y + 3;

        if(collideRects(player.x, player.y, Player.WIDTH, Player.HEIGHT,
                0, 220, 500, 100))
        {
            player.y = 220;
        }

        //moves player left
        //20 is padding from edge of screen
        if (gameEngine.isTouchDown(0)
                && gameEngine.getTouchY(0) > 240
                && gameEngine.getTouchX(0) < 20 + movementButtonsLenght)
        {
            player.x = player.x - 2;
        }

        //moves player right
        if (gameEngine.isTouchDown(0)
                && gameEngine.getTouchY(0) > 240
                && gameEngine.getTouchX(0) > 480 - movementButtonsLenght - 20)
        {
            player.x = player.x + 2;
        }

        //player jumps
        if (gameEngine.isTouchDown(0)
                && gameEngine.getTouchY(0) > 240
                && gameEngine.getTouchX(0) > 480 - movementButtonsLenght - 40 - jumpButtonDimensions
                && gameEngine.getTouchX(0) < 480 - movementButtonsLenght - 40)
        {
            player.jump(deltaTime);
        }



    }

    private boolean collideRects(float x, float y, float width, float height,
                                 float x2, float y2, float width2, float height2)
    {
        if(x < x2 + width2 && x + width > x2 && y < y2 + height2 && y + height > y2)
        {
            return true;
        }

        return false;
    }
}
