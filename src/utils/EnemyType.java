package utils;

public enum EnemyType {
    FACELESS("Безликий", 60, 10, 15, 10, 5, 15, 13, 100),
    OWL("Филин", 75, 10, 19, 5, 10, 5, 25, 100),
    ORC("Орк", 80, 14, 16, 35, 10, 0, 8, 100),
    BOSS_LESHY("БОСС: ЛЕШИЙ", 250, 20, 25, 25, 5, 5, 8, 250),

    CANNIBAL_LOCKSMITH("Слесарь людоед", 80, 14, 15, 40, 2, 10, 8, 100),
    BARE_ELECTRICIAN("Оголенный электрик", 70, 12, 18, 20, 10, 3, 15, 100),
    CONVICT("Зэк", 55, 14, 19, 5, 15, 0, 15, 100),
    BOSS_BRIGADIER("БОСС: БРИГАДИР", 150, 30, 50, 35, 5, 0, 10, 150),

    HORSEHEAD("Конеголовый", 95, 10, 18, 20, 10, 0, 15, 100),
    FALLEN_ANGEL("Падший ангел", 80, 15, 20, 15, 15, 5, 10, 100),
    WANDERER("Блудший", 50, 10, 12, 10, 20, 15, 20, 100),
    BOSS_SOUL_COLLECTOR("БОСС: СБОРЩИК ДУШ", 300, 15, 35, 25, 5, 15, 13, 300),

    ROTTING_PLUMBER("Гниющий сантехник", 50, 10, 17, 10, 10, 2, 10, 100),
    DIVER("Водолаз", 85, 12, 14, 45, 2, 0, 8, 100),
    RATMAN("Крысолюд", 70, 10, 20, 10, 10, 10, 15, 100),
    BOSS_PIRANHA_HANDED_URKA("БОСС: ПИРАНЬЕРУКИЙ УРКА", 200, 30, 40, 15, 30, 5, 15, 200);
    private final String name;
    private final int hp, minDmg, maxDmg, armor, crit, vamp, dodge, maxHp;
    EnemyType(String name, int hp, int minDmg, int maxDmg, int armor,
              int crit, int vamp, int dodge, int maxHp) {
        this.name = name;
        this.hp = hp;
        this.minDmg = minDmg;
        this.maxDmg = maxDmg;
        this.armor = armor;
        this.crit = crit;
        this.vamp = vamp;
        this.dodge = dodge;
        this.maxHp = maxHp;
    }
    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMinDmg() { return minDmg; }
    public int getMaxDmg() { return maxDmg; }
    public int getArmor() { return armor; }
    public int getCritChance() { return crit; }
    public int getVampirism() { return vamp; }
    public int getDodge() { return dodge; }
    public int getMaxHp() { return maxHp; }
}
