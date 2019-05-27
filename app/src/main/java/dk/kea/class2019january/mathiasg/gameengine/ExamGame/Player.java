package dk.kea.class2019january.mathiasg.gameengine.ExamGame;

import android.util.Log;

public class Player
{
    public static final int WIDTH = 17;
    public static final int HEIGHT = 30;

    //determines spawn location
    public int x;
    public int y;

    public int knockBack = 11;
    int playervyUp = 100;
    int playervyDown = 150;
    boolean isShootingFireball = false;
    boolean isJumping;
    boolean isFalling;

    // enums used to determine which player sprite to load
    public enum Direction
    {
        RIGHT,
        LEFT
    }
    public enum VerticalDirection
    {
        UP,
        DOWN,
        STILL
    }

    // set default directions
    public Direction direction = Direction.RIGHT;
    public VerticalDirection verticalDirection = VerticalDirection.STILL;

    public Player()
    {
        this.x = 256;
        this.y = 206;
    }

    // moves player character up
    public void jump(float deltaTime)
    {
        this.verticalDirection = VerticalDirection.UP;

        this.y -= this.playervyUp * deltaTime;
    }

    // moves player character down
    public void fall(float deltaTime)
    {
        this.verticalDirection = VerticalDirection.DOWN;
        this.y += playervyDown * deltaTime;
    }
}
