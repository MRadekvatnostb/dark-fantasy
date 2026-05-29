package utils;

import logic.Enemy;

public class EnemyFactory {
    public static Enemy createEnemy(EnemyType type) {
        return new Enemy(
                type.getName(),
                type.getHp(),
                type.getMinDmg(),
                type.getMaxDmg(),
                type.getArmor(),
                type.getCritChance(),
                type.getVampirism(),
                type.getDodge(),
                type.getMaxHp()
        );
    }
    public static Enemy createBoss(EnemyType type) {
        return createEnemy(type);
    }
}
