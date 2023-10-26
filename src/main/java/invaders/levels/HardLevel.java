package invaders.levels;

public class HardLevel implements GameLevel{
    private static HardLevel instance;

    private HardLevel(){}

    public static HardLevel getInstance(){
        if (instance == null){
            instance = new HardLevel();
        }
        return instance;
    }

    @Override
    public String getConfig() {
        return "src/main/resources/config_hard.json";
    }
}
