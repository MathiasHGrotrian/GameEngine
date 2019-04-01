package dk.kea.class2019january.mathiasg.gameengine.Breakout;

public interface CollisionListener
{
    public void collisionWall();

    public void collisionPaddle();

    public void collisionBlock();
}
