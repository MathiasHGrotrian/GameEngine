package dk.kea.class2019january.mathiasg.gameengine;

public class TestGame extends GameEngine
{
    @Override
    public Screen createStartScreen()
    {
        return new TestScreen(this);
    }
}
