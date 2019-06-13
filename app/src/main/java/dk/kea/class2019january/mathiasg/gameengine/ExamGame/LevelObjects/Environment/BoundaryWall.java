package dk.kea.class2019january.mathiasg.gameengine.ExamGame.LevelObjects.Environment;

import dk.kea.class2019january.mathiasg.gameengine.ExamGame.LevelObjects.LevelObject;

public class BoundaryWall extends LevelObject
{
    public static final int WIDTH = 10;
    public static final int HEIGHT = 320;

    public BoundaryWall(int x, int y)
    {
      super(x, y, WIDTH, HEIGHT);
    }
}
