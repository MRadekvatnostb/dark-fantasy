package logic;

import utils.EffectType;
import utils.GameItem;
import utils.ListItem;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Player extends Entity {
    private int id;
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    private final Map<String, Integer> inventory = new LinkedHashMap<>(); // список предметов в инвентаре
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
        int sum = 0;
        for(Map.Entry<String, Integer> entry : inventory.entrySet()) {
            String itemName = entry.getKey();
            GameItem item = ListItem.createItemByName(itemName); // создаем предмет по имени
            if (item == null)  continue;
            if (item.getDuration() != 0) continue;
            sum += switch (statType) {
                    case DMG -> item.getDmgBuff();
                    case STUN -> item.getStunBuff();
                    case RES -> item.getResurrectionBuff();
                    case DUBATK -> item.getDoubleAttackBuff();
                    case MONEY -> item.getMoneyBuff();
                    case ARMOR -> item.getArmorBuff();
                    case DODGE -> item.getDodgeBuff();
                    case CRIT -> item.getCritChanceBuff();
                    case VAMP -> item.getStillHpBuff();
                    case HP -> item.getHealBuff();
                    default -> 0;
            };
        }
        return sum;
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
     public void addItem(GameItem item) {
        String name = item.getName();
        inventory.merge(name,1,Integer::sum);
         System.out.println("Добавлено: " + name);
}
public boolean useItem(String itemName) {
         Integer count = inventory.get(itemName);
         if (count == null || count<=0){
             System.out.println("Такого предмета нет! ");
             return false;
         }
         inventory.put(itemName, count-1);
         if (inventory.get(itemName) <= 0) {
             inventory.remove(itemName);

         }
    System.out.println("Использовано: " + itemName);
         return true;
}
    public Map<String, Integer> getInventory() {
        return Collections.unmodifiableMap(inventory);
    }
}
