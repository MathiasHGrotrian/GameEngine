package dk.kea.class2019january.mathiasg.gameengine.ExamGame;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dk.kea.class2019january.mathiasg.gameengine.ExamGame.LevelObjects.Coin;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.LevelObjects.Door;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.LevelObjects.LevelObject;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.MainMenuScreen;
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
    Sound bounceSound;
    Sound coinSound;
    Sound deathSound;
    Sound damageSound;
    Sound orcDeathSound;
    public Sound doorAppearsSound;
    public Sound enterDoorSound;
    public List<Orc> orcs;
    public List<Coin> coins;
    public List<LevelObject> levelObjects;
    public List<LevelObject> platforms;
    public Door objDoor;
    public int levelX = 0;

    // fireball object
    public Fireball fireball = new Fireball(player.x, player.y + 11);


    public World(GameEngine gameEngine, int doorX, int doorY)
    {
        this.gameEngine = gameEngine;
        this.fireballBitmap = gameEngine.loadBitmap("ExamGame/Fireball/leftfireball.png");
        this.fireballSound = gameEngine.loadSound("ExamGame/Sounds/fireball.wav");
        this.jumpSound = gameEngine.loadSound("ExamGame/Sounds/jump.wav");
        this.bounceSound = gameEngine.loadSound("ExamGame/Sounds/bounce.wav");
        this.coinSound = gameEngine.loadSound("ExamGame/Sounds/coin.ogg");
        this.deathSound = gameEngine.loadSound("ExamGame/Sounds/death.ogg");
        this.damageSound = gameEngine.loadSound("ExamGame/Sounds/damage.wav");
        this.orcDeathSound = gameEngine.loadSound("ExamGame/Sounds/orcDeath.wav");
        this.doorAppearsSound = gameEngine.loadSound("ExamGame/Sounds/doorAppears.wav");
        this.enterDoorSound = gameEngine.loadSound("ExamGame/Sounds/enterDoor.wav");
        this.orcs = new ArrayList<>();
        this.coins = new ArrayList<>();
        this.levelObjects = new ArrayList<>();
        this.platforms = new ArrayList<>();
        this.objDoor = new Door(doorX, doorY);
        this.fireball.direction = Direction.RIGHT;
    }

    public void update(float deltaTime)
    {
        shootFireball(deltaTime);
        jump();
        //moveOrcs(orcs.get(0), deltaTime, orcs.get(0).initialPosition);
    }




    private void shootFireball(float deltaTime)
    {
        if(!player.isShootingFireball)
        {
            //player shoots fireball
            if (gameEngine.isTouchDown(0)
                    && gameEngine.getTouchY(0) > 240
                    && gameEngine.getTouchX(0) > movementButtonsLenght + 40
                    && gameEngine.getTouchX(0) < movementButtonsLenght + 40 + actionButtonDimensions)
            {
                fireballSound.play(1);
                player.isShootingFireball = true;
                fireball.y = player.y;
                fireball.x = player.x;
                fireball.direction = player.direction;
            }
        }

        if(player.isShootingFireball)
        {
            if(fireball.direction == Direction.RIGHT)
            {
                fireball.x += fireball.vx * deltaTime;
                gameEngine.drawBitmap(loadFireball(player), fireball.x, fireball.y + 11);

                if(fireball.x > player.x + 100)
                {
                    player.isShootingFireball = false;
                    fireball.x = player.x;
                    fireball.y = 0;
                }

            }
            if(fireball.direction == Direction.LEFT)
            {
                fireball.x -= fireball.vx * deltaTime;
                gameEngine.drawBitmap(loadFireball(player), fireball.x, fireball.y + 11);

                if(fireball.x < player.x - 100)
                {
                    player.isShootingFireball = false;
                    fireball.x = player.x;
                    fireball.y = 0;
                }
            }
        }
    }

    public void jump()
    {

        //player jumps
        if(player.verticalDirection == VerticalDirection.STILL)
        {
            if (gameEngine.isTouchDown(0)
                    && gameEngine.getTouchY(0) > 240
                    && gameEngine.getTouchX(0) > 480 - movementButtonsLenght - 40 - actionButtonDimensions
                    && gameEngine.getTouchX(0) < 480 - movementButtonsLenght - 40)
            {
                jumpSound.play(1);
                jumpStartPoint = player.y;
                player.verticalDirection = VerticalDirection.UP;
                player.isIdle = false;
                //player.y -= 5;
            }
        }

        if (player.verticalDirection == VerticalDirection.UP)
        {
            player.y -= 5;
        }
        if(player.y < jumpStartPoint - 90)
        {
            player.verticalDirection = VerticalDirection.DOWN;
        }
    }

    private Bitmap loadFireball(Player player)
    {
        if(player.direction == Direction.RIGHT)
        {
            return gameEngine.loadBitmap("ExamGame/Fireball/rightfireball.png");
        }

        return gameEngine.loadBitmap("ExamGame/Fireball/leftfireball.png");
    }


    public void moveObjects(Player player, int knockback)
    {
        Log.d("moveObjects()", "Objecst should be moved");
        if(player.direction == Direction.RIGHT)
        {
            levelX += knockback;

            //  orc
            for(Orc objOrc: orcs)
            {
                objOrc.x += knockback;
            }

            //  objects
            for (LevelObject object : levelObjects)
            {
                object.x += knockback;
            }

            //  platforms
            for(LevelObject platform: platforms)
            {
                platform.x += knockback;
            }

            //  coins
            for(Coin objCoin: coins)
            {
                objCoin.x += knockback;
            }

            //  door
            objDoor.x += knockback;
        }
        else
        {
            levelX -= knockback;

            //  orc
            for(Orc objOrc: orcs)
            {
                objOrc.x -= knockback;
            }

            //  objects
            for(LevelObject levelObject: levelObjects)
            {
                levelObject.x -= knockback;
            }

            //  platforms
            for(LevelObject platform: platforms)
            {
                platform.x -= knockback;
            }

            //  coins
            for(Coin objCoin: coins)
            {
                objCoin.x -= knockback;
            }

            //  door
            objDoor.x -= knockback;
        }
    }

    public boolean collideDoor(Player player, Door door)
    {
        return collideRects(player.x, player.y, Player.WIDTH, Player.HEIGHT,
                door.x, door.y, Door.WIDTH, Door.HEIGHT);
    }

    public Bitmap loadHealth(int health)
    {
        if(health == 3)
        {
            return gameEngine.loadBitmap("ExamGame/Health/3hearts.png");
        }
        if(health == 2)
        {
            return gameEngine.loadBitmap("ExamGame/Health/2hearts.png");
        }

        return gameEngine.loadBitmap("ExamGame/Health/heart.png");

    }

    public Bitmap loadOrc(int playerX, int orcX)
    {
        if(playerX <= orcX)
        {
            return gameEngine.loadBitmap("ExamGame/Orc/orcLeft.png");
        }

        return gameEngine.loadBitmap("ExamGame/Orc/orcRight.png");
    }

    public boolean openDoor()
    {
        return orcs.isEmpty() && coins.isEmpty();
    }

    //  Collisions
    private boolean collideRects(float x, float y, float width, float height,
                                 float x2, float y2, float width2, float height2)
    {
        return (x < x2 + width2 && x + width > x2 && y < y2 + height2 && y + height > y2);

    }

    public void collideOrc(Player player, Orc orc)
    {
        if(collideRects(player.x, player.y, Player.WIDTH, Player.HEIGHT,
                orc.x, orc.y, Orc.WIDTH, Orc.HEIGHT))
        {
            Log.d("FirstLevel", "Player collided with Orc");
            damageSound.play(1);
            moveObjects(player, player.orcKnockBack);
            player.health -= 1;
            if(player.health <= 0)
            {
                deathSound.play(1);
                gameEngine.setScreen(new MainMenuScreen(gameEngine));
            }

        }
    }

    public void collideObjectsSides(Player player, LevelObject levelObject, List<LevelObject> levelObjects)
    {
        if(collideSides(player, levelObject))
        {
            Log.d("FirstLevel", "Player collided with object");
            bounceSound.play(1);
            moveObjects(player, player.wallKnockBack);
        }
    }

    public boolean collideSides(Player player, LevelObject levelObject)
    {
        return (player.x < levelObject.x + levelObject.width
                && player.x + Player.WIDTH > levelObject.x);
    }

    public void collideGround(Player player, LevelObject levelObject)
    {
        if(player.y + Player.HEIGHT > levelObject.y
                && player.x > levelObject.x
                && player.x < levelObject.x + levelObject.width)
        {
            //Log.d("FirstLevel.collideGround()", "Player collided with ground");
            player.y = levelObject.y - Player.HEIGHT;
            player.verticalDirection = VerticalDirection.STILL;
            player.isIdle = true;
        }
    }

    public void collidePlatform(Player player, LevelObject levelObject)
    {
        if(player.y + Player.HEIGHT == levelObject.y
                && player.x > levelObject.x
                && player.x < levelObject.x + levelObject.width)
        {
            Log.d("FirstLevel.collideGround()", "Player collided with platform");
            player.y = levelObject.y - Player.HEIGHT - 1;
            player.verticalDirection = VerticalDirection.STILL;
        }
    }

    public void collideFireball(Fireball fireball, Orc orc, Player player, List<Orc> orcs)
    {
        if(collideRects(fireball.x, fireball.y + 11, Fireball.WIDTH, Fireball.HEIGHT,
                orc.x, orc.y, Orc.WIDTH, Orc.HEIGHT))
        {
            orcDeathSound.play(1);
            Log.d("FirstLevel.collideFireball()", "Fireball collided with orc");
            orcs.remove(orc);
        }
    }

    public void collideCoin(Player player, Coin coin)
    {
        if(collideRects(player.x, player.y, Player.WIDTH, Player.HEIGHT,
                coin.x, coin.y, Coin.WIDTH, Coin.HEIGHT))
        {
            coinSound.play(1);
            coins.remove(coin);
            player.coinsCollected += 1;
        }
    }

    public void moveOrcs(Orc orc, float deltaTime, int initialPosition)
    {
        //Log.d("World.moveOrcs()", "Initial position is: " + initialPosition + "\n" +
                //"orc.x is :" + orc.x + "\n");
        if(orc.direction == Direction.RIGHT)
        {
            orc.x += 1;
            if(orc.x >= 700 + 5)
            {
                orc.direction = Direction.LEFT;
            }
        }
        if(orc.direction == Direction.LEFT)
        {
            orc.x -= 1;
            if(orc.x <= 700 - 5)
            {
                orc.direction = Direction.RIGHT;
            }
        }
    }
}
