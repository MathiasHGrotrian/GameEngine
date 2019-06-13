package dk.kea.class2019january.mathiasg.gameengine.ExamGame;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Characters.Fireball;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Characters.Orc;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Characters.Player;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Directions.Direction;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Directions.DirectionHandler;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Directions.VerticalDirection;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.LevelObjects.Environment.Coin;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.LevelObjects.Environment.Door;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.LevelObjects.Environment.Ground;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.LevelObjects.LevelObject;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.EndScreen;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.MainMenuScreen;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.Levels.SecondLevel;
import dk.kea.class2019january.mathiasg.gameengine.GameEngine;
import dk.kea.class2019january.mathiasg.gameengine.Sound;

public class World
{
    //player object to determine where on screen to render
    public Player player = new Player();

    GameEngine gameEngine;

    //dimensions for buttons
    int movementButtonsLenght = 75;
    int actionButtonDimensions = 80;

    //sounds to be used in world
    Sound fireballSound;
    Sound jumpSound;
    Sound bounceSound;
    Sound coinSound;
    Sound deathSound;
    Sound damageSound;
    Sound orcDeathSound;
    Sound enterDoorSound;

    //lists of world objects
    public List<Orc> orcs;
    public List<Coin> coins;
    public List<LevelObject> levelObjects;
    public List<LevelObject> platforms;

    //door object to determine where door should be drawn
    Door objDoor;

    //variable to determine where the levels x position is
    public int levelX = 0;

    //ground object for player to walk on
    Ground ground = new Ground(0, 243);

    //handler for handling when player changes direction
    DirectionHandler directionHandler = new DirectionHandler();

    // fireball object
    public Fireball fireball = new Fireball(player.x, player.y + 11);

    //bitmaps to draw
    Bitmap orcRight;
    Bitmap orcLeft;
    Bitmap coin;
    Bitmap door;
    Bitmap health;
    Bitmap fireballLeft;
    Bitmap fireballRight;

    //control bitmaps
    Bitmap rightArrow;
    Bitmap leftArrow;
    Bitmap jumpButton;
    Bitmap fireBallButton;

    //font for showing text on screen
    Typeface font;

    //amount of coins player has to collect
    public int coinsToCollect;

    //variable for determining which level player is on
    public int level;

    public int levelvx = 3;


    public World(GameEngine gameEngine, int doorX, int doorY)
    {
        this.gameEngine = gameEngine;

        //bitmaps
        this.coin = gameEngine.loadBitmap("ExamGame/LevelObjects/coin.png");
        this.door = gameEngine.loadBitmap("ExamGame/LevelObjects/door.png");
        this.health = gameEngine.loadBitmap("ExamGame/Health/3hearts.png");
        this.orcRight = gameEngine.loadBitmap("ExamGame/Orc/orcRight.png");
        this.orcLeft = gameEngine.loadBitmap("ExamGame/Orc/orcLeft.png");
        this.fireballRight = gameEngine.loadBitmap("ExamGame/Fireball/rightfireball.png");
        this.fireballLeft = gameEngine.loadBitmap("ExamGame/Fireball/leftfireball.png");

        //control bitmaps
        this.rightArrow = gameEngine.loadBitmap("ExamGame/Controls/rightArrow.png");
        this.leftArrow = gameEngine.loadBitmap("ExamGame/Controls/leftArrow.png");
        this.jumpButton = gameEngine.loadBitmap("ExamGame/Controls/jumpButton.png");
        this.fireBallButton = gameEngine.loadBitmap("ExamGame/Controls/fireball.png");

        //sounds
        this.fireballSound = gameEngine.loadSound("ExamGame/Sounds/fireball.wav");
        this.jumpSound = gameEngine.loadSound("ExamGame/Sounds/jump.wav");
        this.bounceSound = gameEngine.loadSound("ExamGame/Sounds/bounce.wav");
        this.coinSound = gameEngine.loadSound("ExamGame/Sounds/coin.ogg");
        this.deathSound = gameEngine.loadSound("ExamGame/Sounds/death.ogg");
        this.damageSound = gameEngine.loadSound("ExamGame/Sounds/damage.wav");
        this.orcDeathSound = gameEngine.loadSound("ExamGame/Sounds/orcDeath.wav");
        this.enterDoorSound = gameEngine.loadSound("ExamGame/Sounds/enterDoor.wav");

        //lists
        this.orcs = new ArrayList<>();
        this.coins = new ArrayList<>();
        this.levelObjects = new ArrayList<>();
        this.platforms = new ArrayList<>();

        //misc
        this.objDoor = new Door(doorX, doorY);
        this.fireball.direction = Direction.RIGHT;
        this.font = gameEngine.loadFont("ExamGame/font.ttf");
        this.coinsToCollect = coins.size();
        System.out.println("New World");
    }

    //update method to be called in levels update method
    public void gameUpdate(float deltaTime)
    {
        //gravity
        player.y += 1;

        collideGround(player, ground);
        movePlayer();
        drawWorldObjects();
        collideWorldObjects();
        drawDoor();
        drawUI();
        shootFireball(deltaTime);
        jump();
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
                fireball.y = player.y + 11;
                fireball.x = player.x;
                fireball.initialX = player.x;
                fireball.direction = player.direction;
            }
        }

        if(player.isShootingFireball)
        {
            if(fireball.direction == Direction.RIGHT)
            {
                fireball.x += fireball.vx * deltaTime;
                gameEngine.drawBitmap(fireballRight, fireball.x, fireball.y);

                if(fireball.x > fireball.initialX + 100)
                {
                    player.isShootingFireball = false;
                    fireball.x = player.x;
                    fireball.y = 0;
                }

            }

            if(fireball.direction == Direction.LEFT)
            {
                fireball.x -= fireball.vx * deltaTime;
                gameEngine.drawBitmap(fireballLeft, fireball.x, fireball.y);

                if(fireball.x < fireball.initialX - 100)
                {
                    player.isShootingFireball = false;
                    fireball.x = player.x;
                    fireball.y = 0;
                }
            }
        }
    }

    private void jump()
    {
        //player jumps
        if(player.verticalDirection == VerticalDirection.STILL)
        {
            if(gameEngine.isTouchDown(0)
                    && gameEngine.getTouchY(0) > 240
                    && gameEngine.getTouchX(0) > 480 - movementButtonsLenght - 40 - actionButtonDimensions
                    && gameEngine.getTouchX(0) < 480 - movementButtonsLenght - 40)
            {
                jumpSound.play(1);
                player.jumpStartPoint = player.y;
                player.verticalDirection = VerticalDirection.UP;
                player.isIdle = false;
            }
        }

        if(player.verticalDirection == VerticalDirection.UP)
        {
            player.y -= 5;
        }
        if(player.y < player.jumpStartPoint - 90)
        {
            player.verticalDirection = VerticalDirection.DOWN;
        }
    }

    private void moveObjects(Direction direction, int knockback)
    {
        Log.d("moveObjects()", "Objecst should be moved");
        if(direction == Direction.RIGHT)
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

    private boolean collideDoor(Player player, Door door)
    {
        return collideRects(player.x, player.y, Player.WIDTH, Player.HEIGHT,
                door.x, door.y, Door.WIDTH, Door.HEIGHT);
    }

    private Bitmap loadHealth(int health)
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

    private Bitmap loadOrc(int playerX, int orcX)
    {
        if(playerX <= orcX)
        {
            return orcLeft;
        }

        return orcRight;
    }

    private boolean openDoor()
    {
        return orcs.isEmpty() && coins.isEmpty();
    }

    //  Collisions
    private boolean collideRects(float x, float y, float width, float height,
                                 float x2, float y2, float width2, float height2)
    {
        return (x < x2 + width2 && x + width > x2 && y < y2 + height2 && y + height > y2);

    }

    private void collideOrc(Player player, Orc orc)
    {
        if(collideRects(player.x, player.y, Player.WIDTH, Player.HEIGHT,
                orc.x, orc.y, Orc.WIDTH, Orc.HEIGHT))
        {
            Log.d("FirstLevel", "Player collided with Orc");
            damageSound.play(1);
            moveObjects(player.direction, player.orcKnockBack);
            player.health -= 1;
            if(player.health <= 0)
            {
                deathSound.play(1);
                gameEngine.setScreen(new MainMenuScreen(gameEngine));
            }

        }
    }

    private void collideObjectsSides(Player player, LevelObject levelObject, List<LevelObject> levelObjects)
    {
        if(collideSides(player, levelObject))
        {
            Log.d("FirstLevel", "Player collided with object");
            bounceSound.play(1);
            moveObjects(player.direction, player.wallKnockBack);
        }
    }

    private boolean collideSides(Player player, LevelObject levelObject)
    {
        return (player.x < levelObject.x + levelObject.width
                && player.x + Player.WIDTH > levelObject.x);
    }

    private void collideGround(Player player, LevelObject levelObject)
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

    private void collidePlatform(Player player, LevelObject levelObject)
    {
        if(player.y + Player.HEIGHT == levelObject.y
                && player.x > levelObject.x
                && player.x < levelObject.x + levelObject.width)
        {
            //Log.d("FirstLevel.collideGround()", "Player collided with platform");
            player.y = levelObject.y - Player.HEIGHT - 1;
            player.verticalDirection = VerticalDirection.STILL;
        }
    }

    private void collideFireball(Fireball fireball, Orc orc, Player player, List<Orc> orcs)
    {
        if(collideRects(fireball.x, fireball.y + 11, Fireball.WIDTH, Fireball.HEIGHT,
                orc.x, orc.y, Orc.WIDTH, Orc.HEIGHT))
        {
            orcDeathSound.play(1);
            Log.d("FirstLevel.collideFireball()", "Fireball collided with orc");
            orcs.remove(orc);
        }
    }

    private void collideCoin(Player player, Coin coin)
    {
        if(collideRects(player.x, player.y, Player.WIDTH, Player.HEIGHT,
                coin.x, coin.y, Coin.WIDTH, Coin.HEIGHT))
        {
            coinSound.play(1);
            coins.remove(coin);
            player.coinsCollected += 1;
        }
    }

    private void movePlayer()
    {
        if(directionHandler.isMovingRight(gameEngine, player))
        {
            player.isIdle = false;
            //  orcs
            levelX -= levelvx;
            for(Orc orc: orcs)
            {
                orc.x -= levelvx;
            }

            //  objects
            for(LevelObject levelObject: levelObjects)
            {
                levelObject.x -= levelvx;
            }

            //  platforms
            for(LevelObject platform: platforms)
            {
                platform.x -= levelvx;
            }

            //  coins
            for(Coin objCoin: coins)
            {
                objCoin.x -= levelvx;
            }

            //  door
            objDoor.x -= levelvx;

            //  fireball
            if(player.isShootingFireball)
            {
                fireball.x -= levelvx;
            }

        }
        else if(directionHandler.isMovingLeft(gameEngine, player))
        {
            player.isIdle = false;
            //  orcs
            levelX += levelvx;
            for(Orc orc: orcs)
            {
                orc.x += levelvx;
            }

            //  objects
            for(LevelObject levelObject: levelObjects)
            {
                levelObject.x += levelvx;
            }

            //  platforms
            for(LevelObject platform: platforms)
            {
                platform.x += levelvx;
            }

            //  coins
            for(Coin objCoin: coins)
            {
                objCoin.x += levelvx;
            }

            //  door
            objDoor.x += levelvx;

            // fireball
            if(player.isShootingFireball)
            {
                fireball.x += levelvx;
            }
        }
        else
        {
            player.isIdle = true;
        }
    }

    //draws the UI
    private void drawUI()
    {
        String showText = "Coins: " + player.coinsCollected + " / " + coinsToCollect;

        gameEngine.drawText(font, showText, 380, 20, Color.BLACK, 12);
        gameEngine.drawBitmap(coin, 360, 7);
        gameEngine.drawBitmap(loadHealth(player.health), 10, 10);
        gameEngine.drawBitmap(leftArrow, 20, 240);
        gameEngine.drawBitmap(rightArrow, 380, 240);
        gameEngine.drawBitmap(jumpButton, 280, 230);
        gameEngine.drawBitmap(fireBallButton, leftArrow.getWidth() + 40, 230);
    }

    //draws the door when conditions are met
    private void drawDoor()
    {
        if(openDoor())
        {
            Log.d("Firstlevel.update()", "Update: Opening door");
            gameEngine.drawBitmap(door, objDoor.x, objDoor.y);
            if(collideDoor(player, objDoor) && (gameEngine.isTouchDown(0)
                    && gameEngine.getTouchY(0) > 240
                    && gameEngine.getTouchX(0) > 480 - 75 - 40 - 80
                    && gameEngine.getTouchX(0) < 480 - 75 - 40))
            {
                enterDoorSound.play(1);
                if(level == 1)
                {
                    gameEngine.setScreen(new SecondLevel(gameEngine));
                }
                else
                {
                    gameEngine.setScreen(new EndScreen(gameEngine));
                }

            }
        }
    }

    //collides player with every object in the level
    private void collideWorldObjects()
    {
        for(LevelObject platform : platforms)
        {
            collidePlatform(player, platform);
        }

        for(int i = 0; i < orcs.size(); i++)
        {
            collideOrc(player, orcs.get(i));
        }

        for(int i = 0; i < orcs.size(); i++)
        {
            collideFireball(fireball, orcs.get(i), player, orcs);
        }


        for(int i = 0; i < coins.size(); i++)
        {
            collideCoin(player, coins.get(i));
        }

        for (LevelObject levelObject: levelObjects)
        {
            collideObjectsSides(player, levelObject, levelObjects);
        }

        for(LevelObject platform: platforms)
        {
            collidePlatform(player, platform);
        }
    }

    //draws objects like coins and orcs
    private void drawWorldObjects()
    {
        //  orcs
        for(Orc objOrc: orcs)
        {
            gameEngine.drawBitmap(loadOrc(player.x, objOrc.x), objOrc.x, objOrc.y);
        }
        //  coins
        for(Coin objCoin: coins)
        {
            gameEngine.drawBitmap(coin, objCoin.x, objCoin.y);
        }
    }
}
