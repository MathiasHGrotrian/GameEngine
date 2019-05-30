package dk.kea.class2019january.mathiasg.gameengine.ExamGame;

import android.graphics.Bitmap;

import dk.kea.class2019january.mathiasg.gameengine.GameEngine;
import dk.kea.class2019january.mathiasg.gameengine.Sound;

public class World
{
    public Player player = new Player();
    GameEngine gameEngine;
    int movementButtonsLenght = 75;
    int actionButtonDimensions = 80;
    int jumpStartPoint;

    //fireball sprites
    Bitmap fireballBitmap;
    Sound fireballSound;
    Sound jumpSound;


    // fireball object
    public Fireball fireball = new Fireball(player.x, player.y + 11);


    public World(GameEngine gameEngine)
    {
        this.gameEngine = gameEngine;
        this.fireballBitmap = gameEngine.loadBitmap("ExamGame/Fireball/leftfireball.png");
        this.fireballSound = gameEngine.loadSound("ExamGame/Sounds/fireball.wav");
        this.jumpSound = gameEngine.loadSound("ExamGame/Sounds/jump.wav");

    }

    public void update(float deltaTime)
    {
        movePlayerLeft();
        movePlayerRight();
        shootFireball(deltaTime);

        jump(deltaTime);


    }

    private void movePlayerLeft()
    {
        //moves player left
        //20 is padding from edge of screen
        if (gameEngine.isTouchDown(0)
                && gameEngine.getTouchY(0) > 240
                && gameEngine.getTouchX(0) < 20 + movementButtonsLenght)
        {
            player.direction = Player.Direction.LEFT;
        }
    }

    private void movePlayerRight()
    {
        //moves player right
        //20 is padding from edge of screen
        if (gameEngine.isTouchDown(0)
                && gameEngine.getTouchY(0) > 240
                && gameEngine.getTouchX(0) > 480 - movementButtonsLenght - 20)
        {
            player.direction = Player.Direction.RIGHT;
        }
    }


    private void shootFireball(float deltaTime)
    {
        //player shoots fireball
        if (gameEngine.isTouchDown(0)
                && gameEngine.getTouchY(0) > 240
                && gameEngine.getTouchX(0) > movementButtonsLenght + 40
                && gameEngine.getTouchX(0) < movementButtonsLenght + 40 + actionButtonDimensions)
        {
            fireballSound.play(1);
            player.isShootingFireball = true;
            fireball.x = player.x;
        }

        if(player.isShootingFireball)
        {
            if(player.direction == Player.Direction.RIGHT)
            {
                fireball.x += fireball.vx * deltaTime;
                gameEngine.drawBitmap(loadFireball(player), fireball.x, player.y + 11);

                if(fireball.x > player.x + 100)
                {
                    player.isShootingFireball = false;
                    fireball.x = player.x;
                }

            }
            if(player.direction == Player.Direction.LEFT)
            {
                fireball.x -= fireball.vx * deltaTime;
                gameEngine.drawBitmap(loadFireball(player), fireball.x, player.y + 11);

                if(fireball.x < player.x - 100)
                {
                    player.isShootingFireball = false;
                    fireball.x = player.x;
                }

            }
        }

    }

    public void jump(float deltaTime)
    {

        //player jumps
        if(player.verticalDirection == Player.VerticalDirection.STILL)
        {
            if (gameEngine.isTouchDown(0)
                    && gameEngine.getTouchY(0) > 240
                    && gameEngine.getTouchX(0) > 480 - movementButtonsLenght - 40 - actionButtonDimensions
                    && gameEngine.getTouchX(0) < 480 - movementButtonsLenght - 40)
            {
                jumpSound.play(1);
                jumpStartPoint = player.y;
                player.verticalDirection = Player.VerticalDirection.UP;
                //player.y -= 5;
            }
        }

        if (player.verticalDirection == Player.VerticalDirection.UP)
        {
            player.y -= 5;
        }
        if(player.y < jumpStartPoint - 90)
        {
            player.verticalDirection = Player.VerticalDirection.DOWN;
        }
    }

    private Bitmap loadFireball(Player player)
    {
        Bitmap fireball;
        if(player.direction == Player.Direction.RIGHT)
        {
            fireball = gameEngine.loadBitmap("ExamGame/Fireball/rightfireball.png");
        }
        else
        {
            fireball = gameEngine.loadBitmap("ExamGame/Fireball/leftfireball.png");
        }

        return fireball;
    }
}
