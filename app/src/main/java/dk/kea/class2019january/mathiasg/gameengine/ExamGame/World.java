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

        //moves player left
        //20 is padding from edge of screen
        if(gameEngine.isTouchDown(0)
                && gameEngine.getTouchY(0) > 240
                && gameEngine.getTouchX(0) < 20 + movementButtonsLenght)
        {
            player.x = player.x - 2;
        }

        //moves player right
        if(gameEngine.isTouchDown(0)
                && gameEngine.getTouchY(0) > 240
                && gameEngine.getTouchX(0) > 480 - movementButtonsLenght - 20)
        {
            player.x = player.x + 2;
        }

        //player jumps
        if(gameEngine.isTouchDown(0)
                && gameEngine.getTouchY(0) > 240
                && gameEngine.getTouchX(0) > 480 - movementButtonsLenght - 40 - jumpButtonDimensions
                && gameEngine.getTouchX(0) < 480 - movementButtonsLenght - 40)
        {
            player.jump(deltaTime);
        }

    }
}
