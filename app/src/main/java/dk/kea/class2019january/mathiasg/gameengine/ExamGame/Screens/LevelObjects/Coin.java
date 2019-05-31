package dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.LevelObjects;

public class Coin
{
    public static final int WIDTH = 14;
    public static final int HEIGHT = 16;

    //determines spawn location
    public int x;
    public int y;

    public Coin(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
}
