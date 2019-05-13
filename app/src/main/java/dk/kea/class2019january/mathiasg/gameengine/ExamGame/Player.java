package dk.kea.class2019january.mathiasg.gameengine.ExamGame;

public class Player
{
    public static final int WIDTH = 17;
    public static final int HEIGHT = 30;

    //determines spawn location
    public int x;
    public int y;
    int playervx = 2;
    int playervy = 10;
    boolean isShootingFireball = false;
    boolean isJumping;

    public enum Direction
    {
        RIGHT,
        LEFT
    }

    Direction direction = Direction.RIGHT;

    public Player()
    {
        this.x = 15;
        this.y = 320 - 140;
    }

    public void jump(float deltaTime)
    {
        this.y = this.y - playervy;

    }
}
