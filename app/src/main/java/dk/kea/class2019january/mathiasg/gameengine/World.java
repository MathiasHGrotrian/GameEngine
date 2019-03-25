package dk.kea.class2019january.mathiasg.gameengine;

import java.util.ArrayList;
import java.util.List;

public class World
{
    public static float MIN_X = 0;
    public static float MIN_Y = 36;
    public static float MAX_X = 319;
    public static float MAX_Y = 479;

    Ball ball = new Ball();
    Paddle paddle = new Paddle();
    List<Block> blocks = new ArrayList<>();

    public World()
    {
        generateBlocks();
    }

    public void update(float deltaTime, float accelX, boolean isTouch, int touchX)
    {
        ball.x = ball.x + ball.vx * deltaTime;
        ball.y = ball.y + ball.vy * deltaTime;


        if (ball.x < MIN_X)
        {
            ball.vx = -ball.vx;
            ball.x = MIN_X;
        }
        if (ball.x > MAX_X - Ball.WIDTH)
        {
            ball.vx = -ball.vx;
            ball.x = MAX_X - Ball.WIDTH;
        }


        if (ball.y < MIN_Y)
        {
            ball.vy = -ball.vy;
            ball.y = MIN_Y;
        }

        /*
        if (ball.y > MAX_Y - Ball.HEIGHT)
        {
            ball.vy = -ball.vy;
            ball.y = MAX_Y - Ball.HEIGHT;
        }
        */

        //  move paddle based on phone tilt
        paddle.x = paddle.x - accelX * 60 * deltaTime;

        //  move paddle based on touch, only for testing in emulator
        if (isTouch)
        {
            paddle.x = touchX - paddle.WIDTH / 2;
        }

        //  make sure the paddle stops at the end of the screen
        if (paddle.x < MIN_X) paddle.x = MIN_X;
        if (paddle.x + Paddle.WIDTH > MAX_X) paddle.x = MAX_X - Paddle.WIDTH;

        collideBallPaddle();
        collideBallBlocks(deltaTime);

    }   //  end of update() method


    private void collideBallPaddle()
    {
        if (ball.y > paddle.y + Paddle.HEIGHT) return;
        if ((ball.x >= paddle.x) && (ball.x + Ball.WIDTH < paddle.x + Paddle.WIDTH) && (ball.y + Ball.HEIGHT > paddle.y))
        {
            ball.vy =- ball.vy;
        }
    }

    private void collideBallBlocks(float delta)
    {
        Block block = null;

        for (int i = 0; i < blocks.size(); i++)
        {
            block = blocks.get(i);

            if(collideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT,
                    block.x, block.y, Block.WIDTH, Block.HEIGHT))
            {
                blocks.remove(i);
                float oldvx = ball.vx;
                float oldvy = ball.vy;
                reflectBall(ball, block);

                // Back the ball out 1% to avoid multiple interactions
                ball.x = ball.x - oldvx * delta * 1.01f;
                ball.y = ball.y - oldvy * delta * 1.01f;
            }
        }
    }

    private void reflectBall(Ball ball, Block block)
    {
        // Checks if balls hits the top left corner of the block
        if(collideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT,
                block.x, block.y, 1, 1))
        {
            if(ball.vx > 0) ball.vx = -ball.vx;
            if(ball.vy > 0) ball.vy = -ball.vy;
            return;
        }

        // Checks if ball hits the top right corner of the block
        if(collideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT,
                block.x + Block.WIDTH, block.y, 1, 1))
        {
            if(ball.vx < 0) ball.vx = -ball.vx;
            if(ball.vy > 0) ball.vy = -ball.vy;
            return;
        }

        // Checks the bottom left corner of the block
        if(collideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT,
                block.x, block.y + Block.HEIGHT, 1, 1))
        {
            if(ball.vx < 0) ball.vx = -ball.vx;
            if(ball.vy < 0) ball.vy = -ball.vy;
            return;
        }

        // Checks the bottom right corner of the block
        if(collideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT,
                block.x + Block.WIDTH, block.y + Block.HEIGHT, 1, 1))
        {
            if(ball.vx < 0) ball.vx = -ball.vx;
            if(ball.vy < 0) ball.vy = -ball.vy;
            return;
        }

        //Checks the top edge of the block
        if(collideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT,
                block.x, block.y, Block.WIDTH, 1))
        {
            if(ball.vy < 0) ball.vy = -ball.vy;
            return;
        }

        // Checks the bottom edge of the block
        if(collideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT,
                block.x, block.y + Block.HEIGHT, Block.WIDTH, 1))
        {
            if(ball.vy < 0) ball.vy = -ball.vy;
            return;
        }

        // Checks the left edge of the block
        if(collideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT,
                block.x, block.y, 1, Block.HEIGHT))
        {
            if(ball.vx > 0) ball.vx = -ball.vx;
            return;
        }

        // Checks the right edge of the block
        if(collideRects(ball.x, ball.y, Ball.WIDTH, Ball.HEIGHT,
                block.x + Block.WIDTH, block.y, 1, Block.HEIGHT))
        {
            if(ball.vx > 0) ball.vx = -ball.vx;
            return;
        }
    }

    private boolean collideRects(float x, float y, float width, float height,
                                 float x2, float y2, float width2, float height2)
    {
        if(x < x2 + width2 && x + width > x2 && y < y2 + height2 && y + height > y2)
        {
            return true;
        }

        return false;
    }

    private void generateBlocks()
    {
        blocks.clear();
        for (int y = 60, type = 0; y < 60 + 8 * Block.HEIGHT+4; y = y + ((int)Block.HEIGHT+4), type++)
        {
            for(int x = 30; x < 320 - Block.WIDTH; x = x + (int)Block.WIDTH+4)
            {
                blocks.add(new Block(x, y, type));
            }
        }
    }


}
