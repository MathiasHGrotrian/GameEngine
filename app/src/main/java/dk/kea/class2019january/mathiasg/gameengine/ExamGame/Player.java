package dk.kea.class2019january.mathiasg.gameengine.ExamGame;

public class Player
{
    public static final int WIDTH = 22;
    public static final int HEIGHT = 34;

    //determines spawn location
    public int x;
    public int y;
    public int health;
    public int coinsCollected;

    public int wallKnockBack = 11;
    public int orcKnockBack = 30;

    public boolean isShootingFireball = false;

    public int movementSpeed = 2;

    public boolean isIdle = true;

    // set default directions
    public Direction direction = Direction.RIGHT;
    public VerticalDirection verticalDirection = VerticalDirection.STILL;

    public Player()
    {
        this.x = 256;
        this.y = 100;
        this.health = 3;
        this.coinsCollected = 0;
    }

    // moves player character up
    /*
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
    */
}
