package dk.kea.class2019january.mathiasg.gameengine.ExamGame;

import android.graphics.Bitmap;
import android.util.Log;

import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.MainMenuScreen;
import dk.kea.class2019january.mathiasg.gameengine.GameEngine;

public class World
{
    public Player player = new Player();
    GameEngine gameEngine;
    int movementButtonsLenght = 75;
    int movementButtonsHeight = 61;
    int actionButtonDimensions = 80;
    int fireballSpawnX;
    int fireballSpawnY;
    int jumpStartPoint;

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
        //player.y = player.y + 3;

        //collideGround();

        movePlayerLeft(deltaTime);
        movePlayerRight(deltaTime);
        shootFireball(deltaTime);
        jump(deltaTime);

    }

    private boolean collideRects(float x, float y, float width, float height,
                                 float x2, float y2, float width2, float height2)
    {
        return (x < x2 + width2 && x + width > x2 && y < y2 + height2 && y + height > y2);

    }

    private void collideGround()
    {
        if(collideRects(player.x, player.y, Player.WIDTH, Player.HEIGHT,
                -100, 235, 485, 10))
        {
            //height makes it adjust to feet
            player.y = 235 - Player.HEIGHT;
            player.verticalDirection = Player.VerticalDirection.STILL;
        }
    }

    public void collideDoor(int doorX, int doorY)
    {
        if(collideRects(player.x, player.y, Player.WIDTH, Player.HEIGHT,
                doorX, doorY, Door.WIDTH, Door.HEIGHT) && player.verticalDirection == Player.VerticalDirection.UP)
        {
            Log.d("World", "touching door");
            //needs to discard old screen as well?
            gameEngine.setScreen(new MainMenuScreen(gameEngine));

        }
    }

    private void movePlayerLeft(float deltaTime)
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

    private void movePlayerRight(float deltaTime)
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
            player.isShootingFireball = true;
            fireball.x = player.x;
        }

        if(player.isShootingFireball)
        {
            if(player.direction == Player.Direction.RIGHT)
            {
                fireball.x += fireball.vx * deltaTime;
                gameEngine.drawBitmap(rightFireball, fireball.x, player.y - 11);

                if(fireball.x > player.x + 100)
                {
                    player.isShootingFireball = false;
                    fireball.x = player.x;
                }

            }
            if(player.direction == Player.Direction.LEFT)
            {
                fireball.x -= fireball.vx * deltaTime;
                gameEngine.drawBitmap(rightFireball, fireball.x, player.y - 11);

                if(fireball.x < player.x - 100)
                {
                    player.isShootingFireball = false;
                    fireball.x = player.x;
                }

            }
        }

    }

    private void jump(float deltaTime)
    {
        //player jumps
        if (gameEngine.isTouchDown(0)
                && gameEngine.getTouchY(0) > 240
                && gameEngine.getTouchX(0) > 480 - movementButtonsLenght - 40 - actionButtonDimensions
                && gameEngine.getTouchX(0) < 480 - movementButtonsLenght - 40)
        {
            player.verticalDirection = Player.VerticalDirection.UP;
            jumpStartPoint = player.y;
        }

        if(player.verticalDirection == Player.VerticalDirection.UP)
        {
            player.jump(deltaTime);
            if(player.y < jumpStartPoint - 90)
            {
                player.fall(deltaTime);
            }
        }
        if(player.verticalDirection == Player.VerticalDirection.DOWN)
        {
            player.fall(deltaTime);
        }
    }


    /*
        if(collideRects(player.x, player.y, Player.WIDTH, Player.HEIGHT,
                -100, 235, 485, 10))
        {
            //height makes it adjust to feet
            player.y = 235 - Player.HEIGHT;
            player.verticalDirection = Player.VerticalDirection.STILL;
        }



        //moves player left
        //20 is padding from edge of screen
        if (gameEngine.isTouchDown(0)
                && gameEngine.getTouchY(0) > 240
                && gameEngine.getTouchX(0) < 20 + movementButtonsLenght)
        {
            player.direction = Player.Direction.LEFT;
            player.x -= player.playervx * deltaTime;
        }

        //moves player right
        //20 is padding from edge of screen
        if (gameEngine.isTouchDown(0)
                && gameEngine.getTouchY(0) > 240
                && gameEngine.getTouchX(0) > 480 - movementButtonsLenght - 20)
        {
            player.direction = Player.Direction.RIGHT;
            player.x += player.playervx * deltaTime;
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
                fireball.x += fireball.vx * deltaTime;

                if(fireball.x > fireballSpawnX + 200)
                {
                    player.isShootingFireball = false;
                    fireball.x = player.x;
                }
            }

            if(player.direction == Player.Direction.LEFT)
            {
                gameEngine.drawBitmap(leftFireball, (int)(fireballSpawnX + fireball.x), fireballSpawnY);
                fireball.x -= fireball.vx * deltaTime;

                if(fireball.x < fireballSpawnX - 200)
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
            player.verticalDirection = Player.VerticalDirection.UP;
            jumpStartPoint = player.y;
        }

        if(player.verticalDirection == Player.VerticalDirection.UP)
        {
            player.jump(deltaTime);
            if(player.y < jumpStartPoint - 90)
            {
                player.fall(deltaTime);
            }
        }
        if(player.verticalDirection == Player.VerticalDirection.DOWN)
        {
            player.fall(deltaTime);
        }
        */


}
