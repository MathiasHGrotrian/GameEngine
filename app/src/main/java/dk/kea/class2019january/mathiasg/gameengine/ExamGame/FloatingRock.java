package dk.kea.class2019january.mathiasg.gameengine.ExamGame;

public class FloatingRock
{
    public static final int WIDTH = 99;
    public static final int HEIGHT = 76;
    public int x = 0;
    public int y = 0;

    public FloatingRock()
    {
        this.x = 15;
        this.y = 160 - this.HEIGHT / 2;
    }
}