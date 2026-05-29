package logic;


import go.Main;

public class Enemy extends Entity {
    public Enemy(String name, int hp, int minDamage, int maxDamage, int armor, int critChance, int vampirism, int dodge, int maxHp) { // чертеж врага
        super(name, hp, minDamage, maxDamage, armor, critChance, vampirism, dodge, maxHp);
    }
    public Enemy(Enemy other) { // создаем клон врага
        super(other.name, other.hp, other.minDamage, other.maxDamage, other.armor, other.critChance, other.vampirism, other.dodge, other.maxHp);
    }
    @Override
    public String getAttackPhrase() {
        int choice = Main.rand.nextInt(5);
        if(choice == 0) {return " злобно хрипит и идет в атаку...!"; }
        else if(choice == 1) {return " совершает атаку...!"; }
        else if(choice == 2) {return " наносит подлый удар...!"; }
        else if(choice == 3) {return " бьет вас руками...!"; }
        else {return " кидает в вас обломки...!"; }
    }
}
