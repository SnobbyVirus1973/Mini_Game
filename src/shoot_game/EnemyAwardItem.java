package shoot_game;

public interface EnemyAwardItem {
    int MISSILE = 0;
    int SCORE = 1;
    int DOUBLE_SCORE = 2;
    int SHOOT_LEVEL_UP = 3;
    int LIFE = 4;

    default Items creatItem() {
        if (!(this instanceof Enemy)) {
            return null;
        }
        Enemy obj = (Enemy) this;
        Items item = new Items(obj.x + obj.width / 2, obj.y + obj.height / 2, (int) (Math.random() * 5));
        return item;
    }
}
