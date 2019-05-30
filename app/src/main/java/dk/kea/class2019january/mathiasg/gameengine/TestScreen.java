package dk.kea.class2019january.mathiasg.gameengine;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import java.util.Random;

public class TestScreen extends Screen
{
    int x = 0;
    int y = 240;
    Bitmap bitmap;
    Random random = new Random();
    int color = 0;
    Sound sound;
    Music backgroundMusic;
    boolean isPlaying = false;

    public TestScreen(GameEngine gameEngine)
    {
        super(gameEngine);
        this.bitmap = gameEngine.loadBitmap("breakout/bob.png");
        sound = gameEngine.loadSound("ExamGame/blocksplosion.wav");
        backgroundMusic = gameEngine.loadMusic("ExamGame/music.ogg");
        isPlaying = true;

    }

    @Override
    public void update(float deltaTime)
    {

        Log.d("testscreen", "fps: " + gameEngine.getFramesPerSecond());

        x = x + 10;
        if (x > 320 + bitmap.getWidth())
        {
            x = 0 - bitmap.getWidth();
        }



        if(gameEngine.isTouchDown(0))
        {
            x = gameEngine.getTouchX(0);
            y = gameEngine.getTouchY(0);
            sound.play(1);

            if(backgroundMusic.isPlaying())
            {
                backgroundMusic.pause();
                isPlaying = false;
            }
            else
            {
                backgroundMusic.play();
                isPlaying = true;
            }

        }


        /*
        float x = gameEngine.getAccelerometer()[0];
        float y = -1 * gameEngine.getAccelerometer()[1];
        x = gameEngine.getFrameBufferWidth() / 10 + (x * gameEngine.getFrameBufferWidth()/2);
        y = gameEngine.getFrameBufferHeight()/ 10 + (y * gameEngine.getFrameBufferHeight()/2);
        */


        color = random.nextInt();

        gameEngine.clearFrameBuffer(Color.BLUE);
        gameEngine.drawBitmap(bitmap, (int) x, (int) y);
        //gameEngine.drawBitmap(bitmap, 200, 300, 64, 64, 64, 64);
    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void dispose()
    {

    }
}