package dk.kea.class2019january.mathiasg.gameengine.Breakout;

public class Block
{
    public static float WIDTH = 40;
    public static float HEIGHT = 18;
    public float x;
    public float y;
    public int type;


    public Block(float x, float y, int type)
    {
        this.x = x;
        this.y = y;
        this.type = type;
    }
}
