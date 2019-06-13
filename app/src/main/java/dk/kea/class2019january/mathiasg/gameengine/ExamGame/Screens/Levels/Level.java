package dk.kea.class2019january.mathiasg.gameengine.ExamGame.Screens.Levels;

import java.util.List;

import dk.kea.class2019january.mathiasg.gameengine.ExamGame.Characters.Orc;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.LevelObjects.Environment.Coin;
import dk.kea.class2019january.mathiasg.gameengine.ExamGame.LevelObjects.LevelObject;

public interface Level
{
    List<Coin> placeCoins();
    List<Orc> populateLevel();
    List<LevelObject> buildBoundaries();
    List<LevelObject> buildPlatforms();
}
