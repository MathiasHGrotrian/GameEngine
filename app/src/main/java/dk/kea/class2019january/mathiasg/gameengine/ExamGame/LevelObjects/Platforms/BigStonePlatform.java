package dk.kea.class2019january.mathiasg.gameengine.ExamGame.LevelObjects.Platforms;

import dk.kea.class2019january.mathiasg.gameengine.ExamGame.LevelObjects.LevelObject;

public class BigStonePlatform extends LevelObject
{
    public static final int WIDTH = 80;
    public static final int HEIGHT = 32;

    public BigStonePlatform(int x, int y)
    {
        super(x, y, WIDTH, HEIGHT);
    }
}
