package dk.kea.class2019january.mathiasg.gameengine.ExamGame;

public class Fireball
{
    public static float WIDTH = 8;
    public static float HEIGHT = 7;

    //is this what fucks up the fireball location?
    public int x;
    public int y;
    public int startY = 0;
    public Direction direction = Direction.RIGHT;

    // v = velocity aka speed
    public float vx = 200;

    public Fireball(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

}
