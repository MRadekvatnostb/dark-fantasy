package logic;

import utils.EffectType;
import utils.GameItem;

import java.util.ArrayList;
public abstract class Player extends Entity {
    private int id;
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    private ArrayList<GameItem> inventory = new ArrayList<>(); // список предметов в инвентаре
    protected int energy = 0; // энергия
    protected final int MAX_ENERGY = 100; // максимальная энергия
    public Player(String name, int hp, int minDamage, int maxDamage, int armor, int critChance, int vampirism, int dodge, int maxHp) {
        super(name, hp, minDamage, maxDamage, armor, critChance, vampirism, dodge, maxHp);
    }
    public void gainEnergy(int amount) { // добавление энергии
        this.energy = Math.min(this.energy + amount, MAX_ENERGY); // выбираем минимальное число
    }

    public int getEnergy() {return energy;} // возвращаем энергию
    public void setEnergy(int energy) {this.energy = energy; } // тут можно изменять энергию
    private int getInventoryBonus(EffectType statType) {
        return getInventory().stream()
                .filter(item -> item.getDuration() == 0)
                .mapToInt(item -> {
                    switch (statType) {
                        case DMG: return item.getDmgBuff();
                        case STUN: return item.getStunBuff();
                        case RES: return item.getResurrectionBuff();
                        case DUBATK: return item.getDoubleAttackBuff();
                        case MONEY: return item.getMoneyBuff();
                        case ARMOR: return item.getArmorBuff();
                        case DODGE: return item.getDodgeBuff();
                        case CRIT: return item.getCritChanceBuff();
                        case VAMP: return item.getStillHpBuff();
                        case HP: return item.getHealBuff();
                        default: return 0;
                    }
                }).sum();
    }
    @Override
    public int getMinDamage() {
        // Базовый урон из logic.Entity + бонусы от эффектов + бонусы от инвентаря
        return super.getMinDamage() + getInventoryBonus(EffectType.DMG);
    }

    @Override
    public int getMaxDamage() {
        return super.getMaxDamage() + getInventoryBonus(EffectType.DMG);
    }

    @Override
    public int getArmor() {
        return super.getArmor() + getInventoryBonus(EffectType.ARMOR);
    }
    @Override
    public int getDodge() {
        // Базовый урон из logic.Entity + бонусы от эффектов + бонусы от инвентаря
        return super.getDodge() + getInventoryBonus(EffectType.DODGE);
    }

    @Override
    public int getStun() {
        return super.getStun() + getInventoryBonus(EffectType.STUN);
    }

    @Override
    public int getResurrection() {
        // Базовый урон из logic.Entity + бонусы от эффектов + бонусы от инвентаря
        return super.getResurrection() + getInventoryBonus(EffectType.RES);
    }

    @Override
    public int getDoubleAttack() {
        return super.getDoubleAttack() + getInventoryBonus(EffectType.DUBATK);
    }

    @Override
    public int getMoney() {
        return super.getMoney() + getInventoryBonus(EffectType.MONEY);
    }
    @Override
    public int getMaxHp() {
        // Базовое HP героя + стакающиеся бонусы от шмоток
        return this.maxHp + getInventoryBonus(EffectType.HP);
    }

    public abstract void useUltimate(Entity target); // использование ульты
    public abstract void superAttack(Entity target); // супер атака
    public void addItem(GameItem gameItem) { // добавляем предмет
        this.inventory.add(gameItem);
        System.out.println("В инвентарь добавлено: " + gameItem.getName());
    }
    public ArrayList<GameItem> getInventory() { // делаем инвентарь публичным
        return inventory;
    }
}
