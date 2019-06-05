package dk.kea.class2019january.mathiasg.gameengine.ExamGame.LevelObjects.Platforms;

import dk.kea.class2019january.mathiasg.gameengine.ExamGame.LevelObjects.LevelObject;

public class MossyPlatform extends LevelObject
{
    public static int WIDTH = 48;
    public static int HEIGHT = 35;

    public MossyPlatform(int x, int y)
    {
        super(x, y, WIDTH, HEIGHT);
    }
}
