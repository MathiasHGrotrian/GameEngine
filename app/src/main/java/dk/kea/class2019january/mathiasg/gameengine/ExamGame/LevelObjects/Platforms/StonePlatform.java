package dk.kea.class2019january.mathiasg.gameengine.ExamGame.LevelObjects.Platforms;

import dk.kea.class2019january.mathiasg.gameengine.ExamGame.LevelObjects.LevelObject;

public class StonePlatform extends LevelObject
{
    public static final int WIDTH = 48;
    public static final int HEIGHT = 32;

    public StonePlatform(int x, int y)
    {
        super(x, y, WIDTH, HEIGHT);
    }

}
