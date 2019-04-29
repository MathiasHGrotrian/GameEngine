package dk.kea.class2019january.mathiasg.gameengine.ExamGame;

public class Player
{
    public static final int WIDTH = 17;
    public static final int HEIGHT = 30;
    public int x = 0;
    public int y = 0;

    public Player()
    {
        this.x = 15;
        this.y = 160 - this.HEIGHT / 2;
    }
}
