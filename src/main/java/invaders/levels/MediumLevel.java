package invaders.levels;

public class MediumLevel implements GameLevel{
    private static MediumLevel instance;

    private MediumLevel(){}

    public static MediumLevel getInstance(){
        if (instance == null){
            instance = new MediumLevel();
        }
        return instance;
    }

    @Override
    public String getConfig() {
        return "src/main/resources/config_medium.json";
    }
}
