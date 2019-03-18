package dk.kea.class2019january.mathiasg.gameengine;

public class Breakout extends GameEngine
{

    @Override
    public Screen createStartScreen()
    {
        return new MainMenuScreen(this);
    }
}
