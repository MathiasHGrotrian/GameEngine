package dk.kea.class2019january.mathiasg.gameengine.ExamGame;

import android.graphics.Bitmap;

import dk.kea.class2019january.mathiasg.gameengine.GameEngine;

public class World
{
    Player player = new Player();
    GameEngine gameEngine;
    int movementButtonsLenght = 75;
    int movementButtonsHeight = 61;
    int actionButtonDimensions = 80;
    int fireballSpawnX;
    int fireballSpawnY;

    //fireball sprites
    Bitmap leftFireball;
    Bitmap rightFireball;

    // fireball object
    Fireball fireball = new Fireball();

    public World(GameEngine gameEngine)
    {
        this.gameEngine = gameEngine;
        this.leftFireball = gameEngine.loadBitmap("ExamGame/leftfireball.png");
        this.rightFireball = gameEngine.loadBitmap("ExamGame/rightfireball.png");
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
            player.direction = Player.Direction.LEFT;
            player.x = player.x - 2;
        }

        //moves player right
        if (gameEngine.isTouchDown(0)
                && gameEngine.getTouchY(0) > 240
                && gameEngine.getTouchX(0) > 480 - movementButtonsLenght - 20)
        {
            player.direction = Player.Direction.RIGHT;
            player.x = player.x + 2;
        }

        //player shoots fireball
        if (gameEngine.isTouchDown(0)
                && gameEngine.getTouchY(0) > 240
                && gameEngine.getTouchX(0) > movementButtonsLenght + 40
                && gameEngine.getTouchX(0) < movementButtonsLenght + 40 + actionButtonDimensions)
        {

            player.isShootingFireball = true;

            //gets the location for projectile spawn on screen
            fireballSpawnX = player.x;
            fireballSpawnY = player.y;

        }


        if(player.isShootingFireball)
        {
            if(player.direction == Player.Direction.RIGHT)
            {
                gameEngine.drawBitmap(rightFireball, (int)(fireballSpawnX + fireball.x), fireballSpawnY);
                fireball.x += 3;

                if(fireball.x > fireballSpawnX + 200)
                {
                    player.isShootingFireball = false;
                    fireball.x = player.x;
                }
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
