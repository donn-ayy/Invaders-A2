package invaders.levels;

import invaders.engine.GameEngine;
/**
 * Returns an instance of Hard level Game Engine based on Singleton Pattern.
 */
public class HardLevel implements GameLevel{
    private static GameEngine instance;

    private HardLevel(){}

    public static GameEngine getInstance(){
        if (instance == null){
            instance = new GameEngine("src/main/resources/config_hard.json");
        }
        return instance;
    }

    @Override
    public GameEngine getConfig() {
        return instance;
    }
}
