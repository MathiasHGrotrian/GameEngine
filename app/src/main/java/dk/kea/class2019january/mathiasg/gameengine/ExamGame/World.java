package dk.kea.class2019january.mathiasg.gameengine.ExamGame;

import dk.kea.class2019january.mathiasg.gameengine.GameEngine;

public class World
{
    Player player = new Player();
    GameEngine gameEngine;
    int movementButtonsLenght = 75;
    int getMovementButtonsHeight = 61;
    int actionButtonDimensions = 80;
    private PlayerDirection playerDirection = PlayerDirection.RIGHT;

    public World(GameEngine gameEngine)
    {
        this.gameEngine = gameEngine;
    }

    public void update(float deltaTime)
    {
        //gravity
        player.y = player.y + 3;

        if(collideRects(player.x, player.y, Player.WIDTH, Player.HEIGHT,
                -100, 235, 485, 10))
        {
            //height makes it adjust to feet
            player.y = 235 - Player.HEIGHT;
        }

        //moves player left
        //20 is padding from edge of screen
        if (gameEngine.isTouchDown(0)
                && gameEngine.getTouchY(0) > 240
                && gameEngine.getTouchX(0) < 20 + movementButtonsLenght)
        {
            playerDirection = PlayerDirection.LEFT;
            player.x = player.x - 2;
        }

        //moves player right
        if (gameEngine.isTouchDown(0)
                && gameEngine.getTouchY(0) > 240
                && gameEngine.getTouchX(0) > 480 - movementButtonsLenght - 20)
        {
            playerDirection = PlayerDirection.RIGHT;
            player.x = player.x + 2;
        }

        //player shoots fireball
        if (gameEngine.isTouchDown(0)
                && gameEngine.getTouchY(0) > 240
                && gameEngine.getTouchX(0) > movementButtonsLenght + 40
                && gameEngine.getTouchX(0) < movementButtonsLenght + 40 + actionButtonDimensions)
        {
            if (playerDirection == PlayerDirection.RIGHT)
            {
                //Shoot fireball right
                player.x = player.x + 30;
            }
            if (playerDirection == PlayerDirection.LEFT)
            {
                //Shoot fireball right
                player.x = player.x - 30;
            }

        }

        //player jumps
        if (gameEngine.isTouchDown(0)
                && gameEngine.getTouchY(0) > 240
                && gameEngine.getTouchX(0) > 480 - movementButtonsLenght - 40 - actionButtonDimensions
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
