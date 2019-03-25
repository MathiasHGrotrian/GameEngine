package dk.kea.class2019january.mathiasg.gameengine;

public class Breakout extends GameEngine
{

    @Override
    public Screen createStartScreen()
    {
        music = this.loadMusic("music.ogg");

        return new MainMenuScreen(this);
    }

    public void onResume()
    {
        super.onResume();

        music.play();
    }

    public void onPause()
    {
        super.onPause();

        music.pause();
    }
}


