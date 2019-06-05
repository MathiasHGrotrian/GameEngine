package dk.kea.class2019january.mathiasg.gameengine.ExamGame;

import java.util.List;

import dk.kea.class2019january.mathiasg.gameengine.ExamGame.LevelObjects.Coin;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.LevelObjects.LevelObject;

public interface Level
{
    List<Coin> placeCoins();
    List<Orc> populateLevel();
    List<LevelObject> buildBoundaries();
    List<LevelObject> buildPlatforms();
}
