package dk.kea.class2019january.mathiasg.gameengine.ExamGame;

import dk.kea.class2019january.mathiasg.gameengine.GameEngine;

public class DirectionHandler
{
    public boolean isMovingRight(GameEngine gameEngine)
    {
        //moves player right
        //20 is padding from edge of screen
        if (gameEngine.isTouchDown(0)
                && gameEngine.getTouchY(0) > 240
                && gameEngine.getTouchX(0) > 480 - 75 - 20)
        {
            return true;
        }

        return false;

    }

    public boolean isMovingLeft(GameEngine gameEngine)
    {
        //moves player left
        //20 is padding from edge of screen
        if (gameEngine.isTouchDown(0)
                && gameEngine.getTouchY(0) > 240
                && gameEngine.getTouchX(0) < 20 + 75)
        {
            return true;
        }

        return false;
    }
}
