package dk.kea.class2019january.mathiasg.gameengine.ExamGame.Characters;

import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Directions.Direction;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Directions.VerticalDirection;

public class Player
{
    //bitmap measurements
    public static final int WIDTH = 22;
    public static final int HEIGHT = 34;

    //determines spawn location
    public int x;
    public int y;
    public int health;
    public int coinsCollected;
    public int jumpStartPoint;

    //knockback from collisions
    public int wallKnockBack = 11;
    public int orcKnockBack = 30;

    //used for shooting fireball
    public boolean isShootingFireball = false;

    //determines which bitmap to load
    public boolean isIdle = true;

    // set default directions
    public Direction direction = Direction.RIGHT;
    public VerticalDirection verticalDirection = VerticalDirection.STILL;

    public Player()
    {
        this.x = 256;
        this.y = 180;
        this.health = 3;
        this.coinsCollected = 0;
    }
}
