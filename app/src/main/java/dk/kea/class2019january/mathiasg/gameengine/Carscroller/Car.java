package dk.kea.class2019january.mathiasg.gameengine.Carscroller;

public class Car
{
    public static final int WIDTH = 64;
    public static final int HEIGHT = 32;
    public int x = 0;
    public int y = 0;

    public Car()
    {
        this.x = 15;
        this.y = 160 - this.HEIGHT / 2;
    }
}
