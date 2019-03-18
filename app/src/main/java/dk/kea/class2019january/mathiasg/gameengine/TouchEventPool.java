package dk.kea.class2019january.mathiasg.gameengine;

public class TouchEventPool extends Pool<TouchEvent>
{
    @Override
    protected TouchEvent newItem()
    {
        return new TouchEvent();
    }
}
