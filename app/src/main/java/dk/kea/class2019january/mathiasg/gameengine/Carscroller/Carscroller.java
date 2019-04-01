package dk.kea.class2019january.mathiasg.gameengine.Carscroller;

import dk.kea.class2019january.mathiasg.gameengine.GameEngine;
import dk.kea.class2019january.mathiasg.gameengine.Screen;

public class Carscroller extends GameEngine
{
    @Override
    public Screen createStartScreen()
    {
        music = this.loadMusic("carscroller/music.ogg");

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
