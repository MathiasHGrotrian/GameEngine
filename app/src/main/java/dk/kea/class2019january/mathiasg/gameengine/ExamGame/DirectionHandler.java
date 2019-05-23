package dk.kea.class2019january.mathiasg.gameengine.ExamGame;

import dk.kea.class2019january.mathiasg.gameengine.GameEngine;

public class DirectionHandler
{
    private int movementButtonsLenght = 75;
    private int actionButtonDimensions = 80;

    public boolean isMovingRight(GameEngine gameEngine)
    {
        //moves player right
        //20 is padding from edge of screen
        return  (gameEngine.isTouchDown(0)
                && gameEngine.getTouchY(0) > 240
                && gameEngine.getTouchX(0) > 480 - 75 - 20);

    }

    public boolean isMovingLeft(GameEngine gameEngine)
    {
        //moves player left
        //20 is padding from edge of screen
        return (gameEngine.isTouchDown(0)
                && gameEngine.getTouchY(0) > 240
                && gameEngine.getTouchX(0) < 20 + movementButtonsLenght);

    }
}
