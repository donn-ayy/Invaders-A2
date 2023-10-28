package invaders.levels;

import invaders.engine.GameEngine;

public class MediumLevel implements GameLevel{
    private static GameEngine instance;

    private MediumLevel(){}

    public static GameEngine getInstance(){
        if (instance == null){
            instance = new GameEngine("src/main/resources/config_medium.json");
        }
        return instance;
    }

    @Override
    public GameEngine getConfig() {
        return instance;
    }
}
