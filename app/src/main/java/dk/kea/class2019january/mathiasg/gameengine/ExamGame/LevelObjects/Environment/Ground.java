package dk.kea.class2019january.mathiasg.gameengine.ExamGame.LevelObjects.Environment;

import dk.kea.class2019january.mathiasg.gameengine.ExamGame.LevelObjects.LevelObject;

public class Ground extends LevelObject
{
    public static final int WIDTH = 2800;
    public static final int HEIGHT = 10;

    public Ground(int x, int y)
    {
        super(x, y, WIDTH, HEIGHT);
    }
}
