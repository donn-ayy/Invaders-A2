package invaders.levels;

import invaders.engine.GameEngine;

/**
 * Returns an instance of Easy level Game Engine based on Singleton Pattern.
 */
public class EasyLevel implements GameLevel{
    private static GameEngine instance;

    private EasyLevel(){}

    public static GameEngine getInstance(){
        if (instance == null){
            instance = new GameEngine("src/main/resources/config_easy.json");
        }
        return instance;
    }

    @Override
    public GameEngine getConfig() {
        return instance;
    }
}
