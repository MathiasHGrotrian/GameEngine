package dk.kea.class2019january.mathiasg.gameengine.ExamGame;

public class Orc
{
    public static final int WIDTH = 14;
    public static final int HEIGHT = 20;

    //determines spawn location
    public int x;
    public int y;
    public Direction direction = Direction.LEFT;
    int initialPosition;

    public Orc(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.initialPosition = x;
    }

    public int moveOrc()
    {
        return 1;
    }
}
