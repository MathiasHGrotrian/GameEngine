package dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.LevelObjects;

public class Ground extends LevelObject
{
    public static final int HEIGHT = 10;
    public static final int WIDTH = 2800;

    public Ground(int x, int y)
    {
        super(x, y, HEIGHT, WIDTH);
    }
}
