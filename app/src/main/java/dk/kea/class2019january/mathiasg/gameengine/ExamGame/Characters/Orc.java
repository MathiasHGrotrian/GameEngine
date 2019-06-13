package dk.kea.class2019january.mathiasg.gameengine.ExamGame.Characters;

import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Directions.Direction;

public class Orc
{
    public static final int WIDTH = 14;
    public static final int HEIGHT = 20;

    //determines spawn location
    public int x;
    public int y;
    public Direction direction = Direction.LEFT;

    public Orc(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
}
