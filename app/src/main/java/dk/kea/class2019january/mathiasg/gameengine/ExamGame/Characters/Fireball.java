package dk.kea.class2019january.mathiasg.gameengine.ExamGame.Characters;

import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Directions.Direction;

public class Fireball
{
    public static float WIDTH = 8;
    public static float HEIGHT = 7;

    public int x;
    public int y;
    public Direction direction = Direction.RIGHT;
    public int initialX;

    // v = velocity aka speed
    public float vx = 200;

    public Fireball(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

}
