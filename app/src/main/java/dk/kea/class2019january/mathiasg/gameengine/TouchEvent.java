package dk.kea.class2019january.mathiasg.gameengine;

public class TouchEvent
{
    public enum TouchEventType
    {
        Down,
        Up,
        Dragged
    }

    public TouchEventType type;  //the type of event
    public int x;      //coordinates
    public int y;
    public int pointer; //  pointer id from android system
}
