package invaders;

public enum Points {
    DEFAULT(1),
    SLOW_PROJECTILE(1),
    FAST_PROJECTILE(2),
    SLOW_ALIEN(3),
    FAST_ALIEN(4);
    private final int points;
    Points(int points){
        this.points = points;
    }
    public int getPoints(){
        return points;
    }
}
