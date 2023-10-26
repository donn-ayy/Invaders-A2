package invaders.levels;

public class EasyLevel implements GameLevel{
    private static EasyLevel instance;

    private EasyLevel(){}

    public static EasyLevel getInstance(){
        if (instance == null){
            instance = new EasyLevel();
        }
        return instance;
    }

    @Override
    public String getConfig() {
        return "src/main/resources/config_easy.json";
    }
}
