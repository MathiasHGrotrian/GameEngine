package dk.kea.class2019january.mathiasg.gameengine.ExamGame.Directions;

import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Characters.Player;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Directions.Direction;
import dk.kea.class2019january.mathiasg.gameengine.GameEngine;

public class DirectionHandler
{
    private int movementButtonsLenght = 75;

    public boolean isMovingRight(GameEngine gameEngine, Player player)
    {
        //moves player right
        //20 is padding from edge of screen
        if((gameEngine.isTouchDown(0)
                && gameEngine.getTouchY(0) > 240
                && gameEngine.getTouchX(0) > 480 - movementButtonsLenght - 20))
        {
            player.direction = Direction.RIGHT;
            return true;
        }
        return false;

    }

    public boolean isMovingLeft(GameEngine gameEngine, Player player)
    {
        //moves player left
        //20 is padding from edge of screen
        if((gameEngine.isTouchDown(0)
                && gameEngine.getTouchY(0) > 240
                && gameEngine.getTouchX(0) < 20 + movementButtonsLenght))
        {
            player.direction = Direction.LEFT;
            return true;
        }
        return false;

    }
}
